package org.example.mealplanner.core.service;

import org.example.mealplanner.core.dao.MealDao;
import org.example.mealplanner.core.model.Meal;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MealService {

    private final MealDao mealDao;

    public MealService(MealDao mealDao) {
        this.mealDao = mealDao;
    }

    public List<Meal> getAllMeals() {
        try {
            return mealDao.getAllMeals();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all meals", e);
        }
    }

    public List<Meal> getMealsByCategory(String category) {
        try {
            return mealDao.getAllMealsByCategory(category);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch meals by category: " + category, e);
        }
    }
}
