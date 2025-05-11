package com.pricecomparator.service;

// Updated imports
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.pricecomparator.model.Product;
import com.pricecomparator.model.Discount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Renamed class
public class StoreCSVParser {

    public List<Product> parseCSV(String filePath, String storeName) throws IOException {
        List<Product> products = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
    
            while ((line = reader.readLine()) != null) {
                try {
                    // Split the line by the semicolon delimiter
                    String[] values = line.split(";");
    
                    // Skip invalid rows with insufficient data
                    if (values.length < 8) {
                        System.err.println("Skipping invalid row (incorrect column count) in " + filePath);
                        continue;
                    }
    
                    // Parse each field with error handling
                    String productId = values[0].trim();
                    String productName = values[1].trim();
                    String category = values[2].trim();
                    String brand = values[3].trim();
                    double price = 0;
                    double quantity = 0;
                    String unit = values[5].trim();
                    String currency = values[7].trim();
    
                    // Parse price with error handling
                    try {
                        price = Double.parseDouble(values[6].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid price value in row: " + line);
                        continue; // Skip this line
                    }
    
                    // Parse quantity with error handling
                    try {
                        quantity = Double.parseDouble(values[4].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid quantity value in row: " + line);
                        continue; // Skip this line
                    }
    
                    // Create the Product object, including storeName
                    Product product = new Product(
                        productId, productName, category, brand, price, unit, quantity, currency, storeName
                    );
    
                    // Add the product to the list
                    products.add(product);
                } catch (Exception e) {
                    System.err.println("Error parsing product in " + filePath + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + ": " + e.getMessage());
            throw e; // Re-throw the exception after logging it
        }
    
        return products;
    }

    // Parse discount CSV
    public List<Discount> parseDiscountCSV(String filePath) throws IOException {
        List<Discount> discounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");

                if (values.length == 9) {
                    // Parse the fields from CSV to Discount object
                    String productId = values[0];
                    String productName = values[1];
                    String brand = values[2];
                    int packageQuantity = Integer.parseInt(values[3]);
                    String packageUnit = values[4];
                    String productCategory = values[5];
                    Date fromDate = dateFormat.parse(values[6]);
                    Date toDate = dateFormat.parse(values[7]);
                    double discountPercent = Double.parseDouble(values[8]);

                    Discount discount = new Discount(productId, productName, brand, packageQuantity, packageUnit,
                            productCategory, fromDate, toDate, discountPercent);
                    discounts.add(discount);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace(); // This prints the stack trace
            throw new IOException("Error parsing the discount CSV file", e); // Throws an IOException with the cause
        }
        return discounts;
    }
}



