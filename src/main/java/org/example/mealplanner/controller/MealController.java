package org.example.mealplanner.controller;

import org.example.mealplanner.core.model.Ingredient;
import org.example.mealplanner.core.model.Meal;
import org.example.mealplanner.core.service.MealService;
import org.example.mealplanner.core.util.IngredientParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/meals/new")
    public String showAddMealForm() {
        return "add-meal";
    }

    @PostMapping("/meals")
    public String addMeal(
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam String ingredients
    ) {
        List<Ingredient> parsedIngredients =
                IngredientParser.parse(ingredients);

        Meal meal = new Meal(category, name, parsedIngredients);

        mealService.addMeal(meal);

        return "redirect:/";
    }


    @GetMapping("/meals")
    public String showMeals(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            Model model
    ) {
        List<Meal> meals;

        if (category == null || category.isBlank()) {
            meals = mealService.getAllMeals();
        } else {
            meals = mealService.getMealsByCategory(category);
        }

        if (search != null && !search.isBlank()) {
            String lowerSearch = search.toLowerCase();
            meals = meals.stream()
                    .filter(meal ->
                            meal.getNameOfMeal().toLowerCase().contains(lowerSearch))
                    .toList();
        }

        model.addAttribute("meals", meals);
        model.addAttribute("category", category);
        model.addAttribute("search", search);

        return "meals";
    }


}
