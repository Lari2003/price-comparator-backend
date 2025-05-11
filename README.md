Great question — a clear and honest README can really improve the impression of your work, even if it's not 100% complete. Here's a clean, focused README you can adapt to what you currently have:

---

# Price Comparator - Market 

This is a backend-only Java application for a "Price Comparator" system that helps users compare grocery product prices across multiple supermarket chains like Lidl, Kaufland, and Profi. The data is processed from CSV files, and the system is designed to allow for extensions like tracking price history, showing discounts, and enabling custom alerts.

# Implemented Features

- CSV Parsing for product data from multiple supermarkets.
- Product Aggregation from all product CSVs (excluding discount files).
- Basic Service Layer to expose product data logic.
- Modular Design using Spring Boot and component-based architecture.
- Test Skeleton for 'BasketOptimizerServiceTest'(with JUnit + Spring Test).

# Project Structure
src/main/
├── java/
│   └── com/pricecomparator/
│       ├── model/              # Product model
│       ├── service/            # ProductService interface and implementation
│       └── parser/             # CSVParserComponent for reading product files
├── resources/
│   └── data/                   # Sample CSVs:lidl_*.csv, kaufland_*.csv, etc.
# CSV Format

Sample product CSV structure:


product_id;product_name;category;brand;quantity;unit;price;currency
001;Milk 1L;Dairy;Milbona;1;litru;5.49;RON


- The application processes all product files (e.g., `lidl_2025-05-01.csv`) and ignores discount files (e.g., `lidl_discount_2025-05-01.csv`).

# Work in Progress / Planned Features

* [ ] Best discounts computation.
* [ ] Dynamic price history tracking.
* [ ] Product substitutes and unit-price comparisons.
* [ ] Custom price alerts.
* [ ] Filtering and advanced queries (e.g., by brand, store, or category).
* [ ] Full integration testing and API endpoints.

# How to Run

1. Requirements: Java 17+, Maven.
2. Build & Run:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. Run Tests:

   ```bash
   mvn test
   ```

# Tech Stack

* Java 17
* Spring Boot
* OpenCSV
* JUnit 5
* Maven

# Notes

* Focus was placed on file structure, modularity, and service logic.
* Discount handling and price trend tracking were planned but not implemented due to time constraints.
* Code is written with extensibility in mind — adding new features like REST endpoints 



