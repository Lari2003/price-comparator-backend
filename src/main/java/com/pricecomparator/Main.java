package com.pricecomparator;

import com.pricecomparator.model.Product;
import com.pricecomparator.service.BasketOptimizerService;
import com.pricecomparator.model.ShoppingList;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Example basket with products you want to optimize
        List<Product> basket = Arrays.asList(
            new Product("P001", "lapte zuzu", "lactate", "Zuzu", 5.5, "l", 1, "RON"),
            new Product("P008", "brânză telemea", "lactate", "Pilos", 12.99, "kg", 1, "RON")
        );


        // Store CSV file paths
        String[] storeFilePaths = {
                "src/main/resources/lidl_2025-05-08.csv",
                "src/main/resources/profi_2025-05-08.csv",
                "src/main/resources/kaufland_2025-05-08.csv"
        };

        // Discount CSV file paths (you should add your discount CSV file paths here)
        String[] discountFilePaths = {
                "src/main/resources/lidl_discount_2025-05-08.csv",
                "src/main/resources/profi_discount_2025-05-08.csv",
                "src/main/resources/kaufland_discount_2025-05-08.csv"
        };

        // Create the optimizer service
        BasketOptimizerService optimizer = new BasketOptimizerService();

        // Call the optimizeBasket method with all necessary arguments
        ShoppingList shoppingList = optimizer.optimizeBasket(basket, storeFilePaths, discountFilePaths);

        // Print the optimized shopping list and total price
        System.out.println("Optimized Shopping List for " + shoppingList.getStoreName() + ": ");
        shoppingList.getProducts().forEach(product ->
                System.out.println(product.getProductName() + " - " + product.getPrice() + " RON")
        );
        System.out.println("Total Price: " + shoppingList.getTotalPrice() + " RON");
    }
}
