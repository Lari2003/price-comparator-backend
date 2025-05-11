package com.pricecomparator.service;

import com.pricecomparator.model.Product;
import org.springframework.stereotype.Service;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.io.IOException; 

@Service
public class ProductServiceImpl implements ProductService {

    private static final String DATA_DIR = "src/main/resources/data/";

    private final CSVParserComponent parser;  // Inject CSVParserComponent

    // Constructor injection of CSVParserComponent
    public ProductServiceImpl(CSVParserComponent parser) {
        this.parser = parser;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(DATA_DIR))) {
            paths.filter(Files::isRegularFile)
                 .filter(path -> path.toString().endsWith(".csv"))
                 .filter(path -> !path.toString().contains("discount")) // Skip discount files
                 .forEach(path -> {
                     List<Product> productList = parseCsvFile(path);
                     if (productList != null && !productList.isEmpty()) {
                         products.addAll(productList);
                     }
                 });
        } catch (IOException e) {
            System.err.println("Error reading files from " + DATA_DIR + ": " + e.getMessage());
        }

        return products;
    }

    private List<Product> parseCsvFile(Path filePath) {
        List<Product> products = new ArrayList<>();

        try {
            // Use the CSVParserComponent to parse the file
            products = parser.parseProducts(filePath.toString());
        } catch (IOException e) {
            System.err.println("Error reading CSV file " + filePath + ": " + e.getMessage());
        }

        return products;
    }
}
