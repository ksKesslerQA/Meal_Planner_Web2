package org.example.mealplanner.core.model;

import org.example.mealplanner.core.util.IngredientParser;

import java.util.List;
import java.util.Scanner;

public class Meal {
    private String category;
    private String nameOfMeal;
    private List<Ingredient> ingredients;

    public Meal(String category, String nameOfMeal, List<Ingredient> ingredients) {
        this.category = category;
        this.nameOfMeal = nameOfMeal;
        this.ingredients = ingredients;
    }

    public String getCategory() {
        return category;
    }

    public String getNameOfMeal() {
        return nameOfMeal;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public static Meal addNewMeal(Scanner scan){

        String сategory;
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");

        while (true) {
            сategory = scan.nextLine();

            if (сategory.equals("breakfast") ||
                    сategory.equals("lunch") ||
                    сategory.equals("dinner")) {
                break;
            } else {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
        }

        String nameOfMeal;
        System.out.println("Input the meal's name:");

        while (true) {
            nameOfMeal = scan.nextLine();

            if (nameOfMeal.matches("[a-zA-Z ]+")) {
                break;
            } else {
                System.out.println("Wrong format. Use letters only!");
            }
        }

        System.out.println("Input ingredients (format: name amount unit, separated by commas):");
        System.out.println("Example: eggs 2 pcs, milk 200 ml, flour 100 g");

        String input = scan.nextLine();
        List<Ingredient> ingredients = IngredientParser.parse(input);
/*
        while (true) {
            String ingredientsInput = scan.nextLine();

            List<String> tempIngredients = Arrays.stream(ingredientsInput.split(","))
                    .map(String::trim)
                    .toList();

            boolean allValid = true;

            for (String ingredient : tempIngredients) {
                if (!ingredient.matches("[a-zA-Z ]+")) {
                    allValid = false;
                    break;
                }
            }

            if (allValid) {
                ingredients = tempIngredients;
                break;
            } else {
                System.out.println("Wrong format. Use letters only!");
            }
        }

 */

        System.out.println("The meal has been added!");

        return new Meal(сategory, nameOfMeal, ingredients);

    };

    public static void printMealInfo(Meal meal) {
        System.out.println("\nName: " + meal.getNameOfMeal());
        System.out.println("Ingredients:");
        meal.getIngredients().forEach(i -> System.out.println(i));
    }
}
