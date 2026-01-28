package org.example.mealplanner.controller;

import org.example.mealplanner.core.model.Ingredient;
import org.example.mealplanner.core.service.ShoppingListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping("/shopping-list")
    public String showShoppingList(Model model) throws SQLException {

        List<Ingredient> ingredients =
                shoppingListService.getShoppingList();

        model.addAttribute("ingredients", ingredients);

        return "shopping-list";
    }

    @GetMapping("/shopping-list/download")
    public ResponseEntity<byte[]> downloadShoppingList() throws SQLException {

        String content = shoppingListService.buildShoppingListText();

        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=shopping-list.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(bytes);
    }

}
