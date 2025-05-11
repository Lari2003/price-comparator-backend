package com.pricecomparator.service;

import org.springframework.stereotype.Component;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.pricecomparator.model.Product;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component 
public class CSVParserComponent {

    private static final char CSV_DELIMITER = ';';

    public List<Product> parseProducts(String filePath) throws IOException {
        List<Product> products = new ArrayList<>();
        CSVParser csvParser = new CSVParserBuilder().withSeparator(CSV_DELIMITER).build();

        // Derive store name from file path (this can be customized based on how store names are encoded in the path)
        String storeName = filePath.split("/")[filePath.split("/").length - 1].split("_")[0]; // Assuming store name is part of the file name

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(csvParser)
                .build()) {

            String[] line;
            boolean firstRow = true; // Variable to skip the header row

            while ((line = reader.readNext()) != null) {
                // Skip header row
                if (firstRow) {
                    firstRow = false;
                    continue;
                }

                // Check if row contains valid data for price and quantity
                if (line.length < 8 || line[6].equalsIgnoreCase("price") || line[4].equalsIgnoreCase("quantity")) {
                    System.err.println("Skipping invalid row in " + filePath);
                    continue; // Skip rows with invalid values
                }

                try {
                    String productId = line[0].trim();
                    String productName = line[1].trim();
                    String category = line[2].trim();
                    String brand = line[3].trim();
                    String unit = line[5].trim();
                    String currency = line[7].trim();

                    // Validate and parse price and quantity
                    double price = 0;
                    double quantity = 0;

                    if (!line[6].matches("[0-9]*\\.?[0-9]+")) { // Check if price is numeric
                        System.err.println("Invalid price value: " + line[6]);
                        continue; // Skip this row
                    } else {
                        price = Double.parseDouble(line[6].trim());
                    }

                    if (!line[4].matches("[0-9]*\\.?[0-9]+")) { // Check if quantity is numeric
                        System.err.println("Invalid quantity value: " + line[4]);
                        continue; // Skip this row
                    } else {
                        quantity = Double.parseDouble(line[4].trim());
                    }

                    // Create product object and add to list, now with storeName
                    Product product = new Product(productId, productName, category, brand, price, unit, (int) quantity, currency, storeName);
                    products.add(product);

                } catch (Exception e) {
                    System.err.println("Error parsing product in " + filePath + ": " + e.getMessage());
                }
            }
        } catch (CsvValidationException e) {
            System.err.println("CSV Validation error in " + filePath + ": " + e.getMessage());
        }

        return products;
    }
}
