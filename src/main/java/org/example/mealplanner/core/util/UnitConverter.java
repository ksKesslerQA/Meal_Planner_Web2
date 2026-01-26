package org.example.mealplanner.core.util;

import org.example.mealplanner.core.model.Ingredient;
import org.example.mealplanner.core.model.Unit;

public class UnitConverter {

    public static Ingredient normalize(Ingredient ingredient) {

        double amount = ingredient.getAmount();
        Unit unit = ingredient.getUnit();

        if (unit == Unit.MILLILITERS && amount >= 1000) {
            return new Ingredient(
                    ingredient.getName(),
                    amount / 1000.0,
                    Unit.LITERS
            );
        }

        if (unit == Unit.GRAMS && amount >= 1000) {
            return new Ingredient(
                    ingredient.getName(),
                    amount / 1000.0,
                    Unit.KILOGRAMS
            );
        }

        return ingredient;
    }
}
