package org.example.mealplanner.core.service;

import org.example.mealplanner.core.dao.IngredientDao;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class IngredientService {

    private final IngredientDao ingredientDao;

    public IngredientService(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public List<String> getIngredientNames() {
        try {
            return ingredientDao.findAllIngredientNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
