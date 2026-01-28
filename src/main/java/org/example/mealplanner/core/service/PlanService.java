package org.example.mealplanner.core.service;

import org.example.mealplanner.core.dao.MealDao;
import org.example.mealplanner.core.dao.PlanDao;
import org.example.mealplanner.core.model.DaysOfTheWeek;
import org.example.mealplanner.core.model.Meal;
import org.example.mealplanner.core.model.MealPlan;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PlanService {

    private final MealDao mealDao;
    private final PlanDao planDao;

    public PlanService(MealDao mealDao, PlanDao planDao) {
        this.mealDao = mealDao;
        this.planDao = planDao;
    }

    public void clearPlan() throws SQLException {
        planDao.clearPlan();
    }

    public List<Meal> getMealsByCategory(String category) throws SQLException {
        return mealDao.getAllMealsByCategory(category);
    }

    public void addPlanItem(
            DaysOfTheWeek day,
            String category,
            Meal meal
    ) throws SQLException {

        int mealId = mealDao.getMealIdByName(meal.getNameOfMeal());
        planDao.savePlanItem(
                day.name(),
                category,
                meal.getNameOfMeal(),
                mealId
        );
    }

    public List<MealPlan> getWeeklyPlan() throws SQLException {
        return planDao.getWeeklyPlan();
    }

}

