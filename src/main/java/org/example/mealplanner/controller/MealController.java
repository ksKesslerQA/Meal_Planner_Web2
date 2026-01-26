package org.example.mealplanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MealController {

    @GetMapping("/meals/new")
    public String showAddMealForm() {
        return "add-meal";
    }
}
