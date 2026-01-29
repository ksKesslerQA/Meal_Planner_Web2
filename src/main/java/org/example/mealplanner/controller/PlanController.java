package org.example.mealplanner.controller;

import org.example.mealplanner.core.model.DaysOfTheWeek;
import org.example.mealplanner.core.model.Meal;
import org.example.mealplanner.core.model.MealPlan;
import org.example.mealplanner.core.service.MealService;
import org.example.mealplanner.core.service.PlanService;
import org.example.mealplanner.web.plan.DayPlanForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.example.mealplanner.web.plan.WeeklyPlanForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import java.nio.charset.StandardCharsets;


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

    @GetMapping("/plan/download")
    public ResponseEntity<byte[]> downloadPlan() throws SQLException {

        StringBuilder sb = new StringBuilder();
        sb.append("Weekly Meal Plan\n\n");

        List<MealPlan> plans = planService.getWeeklyPlan();

        for (MealPlan plan : plans) {
            sb.append(plan.getDay()).append("\n");

            if (plan.getBreakfast() != null && !plan.getBreakfast().isBlank()) {
                sb.append("  Breakfast: ")
                        .append(plan.getBreakfast())
                        .append("\n");
            }

            if (plan.getLunch() != null && !plan.getLunch().isBlank()) {
                sb.append("  Lunch: ")
                        .append(plan.getLunch())
                        .append("\n");
            }

            if (plan.getDinner() != null && !plan.getDinner().isBlank()) {
                sb.append("  Dinner: ")
                        .append(plan.getDinner())
                        .append("\n");
            }

            sb.append("\n");
        }

        byte[] content = sb.toString().getBytes(StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=weekly-plan.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(content);
    }


    private String capitalize(String s) {
        if (s == null || s.isBlank()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }




}