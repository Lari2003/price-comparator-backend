package com.pricecomparator.service;

import com.pricecomparator.model.Product;
import com.pricecomparator.model.Discount;
import com.pricecomparator.model.ShoppingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BasketOptimizerService {
    private static final Logger logger = LoggerFactory.getLogger(BasketOptimizerService.class);
    private final StoreCSVParser csvParser = new StoreCSVParser();

    // Main method to optimize basket
    public ShoppingList optimizeBasket(List<Product> basket, String[] storeFilePaths, String[] discountFilePaths) {
        Map<String, List<Product>> storeProducts = loadStoreProducts(storeFilePaths);
        Map<String, List<Discount>> storeDiscounts = loadStoreDiscounts(discountFilePaths);

        // Optimize the basket with best prices and discounts
        List<Product> optimizedBasket = basket.stream()
            .map(product -> {
                Product bestPriceProduct = findBestPriceProduct(product, storeProducts);
                Product discountedProduct = createDiscountedCopy(bestPriceProduct, storeDiscounts);
                return discountedProduct;
            })
            .collect(Collectors.toList());

        // Determine the best store and calculate total cost
        String bestStore = determineBestStore(optimizedBasket, storeProducts);
        double totalCost = optimizedBasket.stream()
            .mapToDouble(Product::getPrice)
            .sum();

        // Return the optimized shopping list
        return new ShoppingList(bestStore, optimizedBasket, totalCost);
    }

    // Load products from the store CSV files
    private Map<String, List<Product>> loadStoreProducts(String[] storeFilePaths) {
        Map<String, List<Product>> storeProducts = new HashMap<>();
        for (String filePath : storeFilePaths) {
            try {
                List<Product> products = csvParser.parseCSV(filePath, filePath.split("/")[filePath.split("/").length - 1]);
                storeProducts.put(filePath, products);
            } catch (IOException e) {
                logger.error("Failed to load products from {}", filePath, e);
                throw new RuntimeException("Failed to load store data from: " + filePath, e);
            }
        }
        return storeProducts;
    }

    // Load discounts from the discount CSV files
    private Map<String, List<Discount>> loadStoreDiscounts(String[] discountFilePaths) {
        Map<String, List<Discount>> storeDiscounts = new HashMap<>();
        for (String filePath : discountFilePaths) {
            try {
                List<Discount> discounts = csvParser.parseDiscountCSV(filePath);
                storeDiscounts.put(filePath, discounts);
            } catch (IOException e) {
                logger.error("Failed to load discounts from {}", filePath, e);
                throw new RuntimeException("Failed to load discount data from: " + filePath, e);
            }
        }
        return storeDiscounts;
    }

    // Find the product with the best price across all stores
    private Product findBestPriceProduct(Product basketProduct, Map<String, List<Product>> storeProducts) {
        return storeProducts.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .filter(product -> product.getProductId().equals(basketProduct.getProductId()))
                .min(Comparator.comparingDouble(Product::getPrice))
                .orElseThrow(() -> new IllegalArgumentException(
                    "Product " + basketProduct.getProductId() + " not found in any store"));
    }

    // Create a new product copy and apply discounts
    private Product createDiscountedCopy(Product original, Map<String, List<Discount>> discounts) {
        Product copy = new Product(
            original.getProductId(),
            original.getProductName(),
            original.getCategory(),
            original.getBrand(),
            original.getPrice(),
            original.getUnit(),
            original.getQuantity(),
            original.getCurrency(),
            original.getStoreName() // Pass store name to the copy
        );
        applyDiscount(copy, discounts);
        return copy;
    }

    // Apply discount to a product
    private void applyDiscount(Product product, Map<String, List<Discount>> storeDiscounts) {
        storeDiscounts.values().stream()
            .flatMap(List::stream)
            .filter(discount -> discount.getProductId().equals(product.getProductId()))
            .filter(this::isDiscountActive)
            .findFirst()
            .ifPresent(discount -> {
                double discountedPrice = product.getPrice() * (1 - discount.getDiscountPercent() / 100);
                product.setPrice(discountedPrice);
                logger.info("Applied {}% discount to product {}", discount.getDiscountPercent(), product.getProductId());
            });
    }

    // Check if the discount is active
    private boolean isDiscountActive(Discount discount) {
        Date now = new Date();
        return now.after(discount.getFromDate()) && now.before(discount.getToDate());
    }

    // Determine the best store (could be more advanced)
    private String determineBestStore(List<Product> basket, Map<String, List<Product>> storeProducts) {
        return basket.isEmpty() ? "Best Store" : basket.get(0).getStoreName();
    }
}
