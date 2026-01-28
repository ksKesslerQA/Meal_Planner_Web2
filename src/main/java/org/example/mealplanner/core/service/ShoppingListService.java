package org.example.mealplanner.core.service;

import org.example.mealplanner.core.dao.PlanDao;
import org.example.mealplanner.core.model.Ingredient;
import org.example.mealplanner.core.util.UnitConverter;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private final PlanDao planDao;

    public ShoppingListService(PlanDao planDao) {
        this.planDao = planDao;
    }

    public List<Ingredient> getShoppingList() throws SQLException {
        return planDao.getPlannedIngredients()
                .stream()
                .map(UnitConverter::normalize)
                .collect(Collectors.toList());
    }

    public String buildShoppingListText() throws SQLException {

        StringBuilder sb = new StringBuilder();

        for (Ingredient ingredient : getShoppingList()) {
            sb.append(ingredient.getName())
                    .append(" ")
                    .append(ingredient.getAmount())
                    .append(" ")
                    .append(ingredient.getUnit().getLabel())
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }

}
