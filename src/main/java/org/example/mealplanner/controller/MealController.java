package org.example.mealplanner.controller;

import org.example.mealplanner.core.model.Ingredient;
import org.example.mealplanner.core.model.Meal;
import org.example.mealplanner.core.service.IngredientService;
import org.example.mealplanner.core.service.MealService;
import org.example.mealplanner.core.util.IngredientParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.example.mealplanner.core.model.Unit;
import org.springframework.web.bind.annotation.ResponseBody;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MealController {

    private final MealService mealService;
    private final IngredientService ingredientService;

    public MealController(
            MealService mealService,
            IngredientService ingredientService
    ) {
        this.mealService = mealService;
        this.ingredientService = ingredientService;
    }


    @GetMapping("/meals/new")
    public String showAddMealForm() {
        return "add-meal";
    }

    @PostMapping("/meals")
    public String addMeal(
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam(required = false) String recipe,
            @RequestParam List<String> ingredientName,
            @RequestParam List<Double> ingredientAmount,
            @RequestParam List<String> ingredientUnit
    ) throws SQLException {

        List<Ingredient> ingredients = new ArrayList<>();

        for (int i = 0; i < ingredientName.size(); i++) {
            ingredients.add(new Ingredient(
                    ingredientName.get(i),
                    ingredientAmount.get(i),
                    Unit.fromUserInput(ingredientUnit.get(i))
            ));
        }


        Meal meal = new Meal(category, name, ingredients, recipe == null || recipe.isBlank() ? null : recipe);

        mealService.saveMeal(meal);

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

    @GetMapping("/api/ingredients")
    @ResponseBody
    public List<String> ingredientSuggestions(
            @RequestParam String query
    ) {
        String q = query.toLowerCase();

        return ingredientService.getIngredientNames().stream()
                .filter(name -> name.toLowerCase().contains(q))
                .toList();
    }


}
