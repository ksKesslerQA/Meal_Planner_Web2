package org.example.mealplanner.controller;

import org.example.mealplanner.core.model.DaysOfTheWeek;
import org.example.mealplanner.core.model.Meal;
import org.example.mealplanner.core.service.MealService;
import org.example.mealplanner.core.service.PlanService;
import org.example.mealplanner.web.plan.DayPlanForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.example.mealplanner.web.plan.WeeklyPlanForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.SQLException;
import java.util.List;

@Controller
public class PlanController {

    private final MealService mealService;
    private final PlanService planService;

    public PlanController(MealService mealService, PlanService planService) {
        this.mealService = mealService;
        this.planService = planService;
    }

    @GetMapping("/plan")
    public String showPlanForm(Model model) {

        WeeklyPlanForm form = new WeeklyPlanForm();

        for (DaysOfTheWeek day : DaysOfTheWeek.values()) {
            form.getDays().put(day.name(), new DayPlanForm());
        }

        model.addAttribute("form", form);
        model.addAttribute("meals", mealService.getAllMeals());

        return "plan";
    }

    @PostMapping("/plan")
    public String savePlan(@ModelAttribute WeeklyPlanForm form) throws SQLException {

        planService.clearPlan();

        for (var entry : form.getDays().entrySet()) {
            DaysOfTheWeek day = DaysOfTheWeek.valueOf(entry.getKey());
            DayPlanForm d = entry.getValue();

            if (d.getBreakfast() != null && !d.getBreakfast().isBlank()) {
                planService.addPlanItem(day, "breakfast",
                        new Meal("breakfast", d.getBreakfast(), List.of()));
            }

            if (d.getLunch() != null && !d.getLunch().isBlank()) {
                planService.addPlanItem(day, "lunch",
                        new Meal("lunch", d.getLunch(), List.of()));
            }

            if (d.getDinner() != null && !d.getDinner().isBlank()) {
                planService.addPlanItem(day, "dinner",
                        new Meal("dinner", d.getDinner(), List.of()));
            }
        }

        return "redirect:/plan/view";
    }

    @GetMapping("/plan/view")
    public String viewPlan(Model model) throws SQLException {
        model.addAttribute("plans", planService.getWeeklyPlan());
        return "plan-view";
    }

}