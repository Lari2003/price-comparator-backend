package com.pricecomparator.service_test;

import com.pricecomparator.model.Product;
import com.pricecomparator.service.BasketOptimizerService;
import com.pricecomparator.model.ShoppingList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BasketOptimizerServiceTest {

    @Test
    public void testOptimizeBasket() {
        // Sample basket for testing
        List<Product> basket = Arrays.asList(
                new Product("P001", "lapte zuzu", "lactate", "Zuzu", 5.5, "l", 1.0, "RON", "ZuzuStore"),
                new Product("P008", "brânză telemea", "lactate", "Pilos", 12.99, "kg", 1.0, "RON", "PilosStore")
        );

        // Store CSV file paths
        String[] storeFilePaths = {
                "src/main/resources/lidl_2025-05-08.csv",
                "src/main/resources/profi_2025-05-08.csv",
                "src/main/resources/kaufland_2025-05-08.csv"
        };

        // Discount CSV file paths
        String[] discountFilePaths = {
                "src/main/resources/lidl_discount_2025-05-08.csv",
                "src/main/resources/profi_discount_2025-05-08.csv",
                "src/main/resources/kaufland_discount_2025-05-08.csv"
        };

        // Create the optimizer service
        BasketOptimizerService optimizer = new BasketOptimizerService();

        // Call the optimizeBasket method
        ShoppingList shoppingList = optimizer.optimizeBasket(basket, storeFilePaths, discountFilePaths);

        // Assertions
        assertNotNull(shoppingList); // Ensure the shopping list is not null
        assertEquals("ZuzuStore", shoppingList.getStoreName()); // Check store name
        assertTrue(shoppingList.getTotalPrice() > 0); // Ensure total price is greater than 0

        // Optionally, check if the products list is not empty
        assertFalse(shoppingList.getProducts().isEmpty(), "Optimized shopping list should not be empty");
    }
}
