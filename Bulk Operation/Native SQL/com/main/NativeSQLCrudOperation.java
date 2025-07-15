package com.main;

import javax.persistence.*;
import java.util.List;

public class NativeSQLCrudOperation {
    private static EntityManagerFactory eFactory;
    private static EntityManager eManager;

    public static void main(String[] args) {
        try {
            eFactory = Persistence.createEntityManagerFactory("config");
            eManager = eFactory.createEntityManager();

            // insert(); // Insert multiple products
            // readAllColumns();   // Read All Columns based on Condition <Product>
            // read();   // Read All Specific Columns based on Condition <Object>
            // update(); // Update product price using native SQL
            delete(); // Delete product(s) by condition

        } catch (Exception e) {
            System.err.println("❌ An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (eManager != null && eManager.isOpen()) eManager.close();
            if (eFactory != null && eFactory.isOpen()) eFactory.close();
            System.out.println("✅ Application finished. Resources closed.");
        }
    }

    // 🔹 INSERT using Native SQL (bulk-like behavior with loop)
    private static void insert() {
        EntityTransaction transaction = eManager.getTransaction();
        transaction.begin();

        // Prepare SQL (column names must match table)
        String sql = "INSERT INTO Product_1 (Product_id, name, manufacturingDate, price) VALUES (?, ?, ?, ?)";
        Query nativeQuery = eManager.createNativeQuery(sql);

        // Example product data
        Object[][] productData = {
            {101, "Soup", "2022-11-22", 20},
            {102, "Shampoo", "2023-02-10", 55},
            {103, "Brush", "2023-04-18", 75},
            {104, "Pen", "2023-06-12", 25}
        };

        // Loop to insert multiple rows
        for (Object[] data : productData) {
            nativeQuery.setParameter(1, data[0]);
            nativeQuery.setParameter(2, data[1]);
            nativeQuery.setParameter(3, data[2]);
            nativeQuery.setParameter(4, data[3]);
            nativeQuery.executeUpdate(); // Executes the insert
        }

        transaction.commit();
        System.out.println("✅ Products inserted successfully.");
    }

    // 🔹 READ All Columns based on Condition <Product>
    public static void readAllColumns() {
        String query = "SELECT * FROM Product_1 WHERE price > ?";

        Query nativeQuery = eManager.createNativeQuery(query, Product.class); // map full result to Product entity
        nativeQuery.setParameter(1, 40);

        List<Product> productsList = nativeQuery.getResultList(); // Directly mapped to Product objects

        for (Product product : productsList) {
            System.out.println(product);
        }
    }
    
    // 🔹 READ All Specific Columns based on Condition <Object>
    public static void read() {
        String query = "SELECT Product_id, name, price FROM Product_1 WHERE price > ?";

        Query nativeQuery = eManager.createNativeQuery(query); // no Product.class since result is partial
        nativeQuery.setParameter(1, 40);

        List<Object[]> resultList = nativeQuery.getResultList();

        for (Object[] row : resultList) {
            System.out.println("ID: " + row[0] + ", Name: " + row[1] + ", Price: " + row[2]);
        }
    }

    // 🔹 UPDATE price using Native SQL
    private static void update() {
        EntityTransaction transaction = eManager.getTransaction();
        transaction.begin();

        String sql = "UPDATE Product_1 SET price = 100 WHERE Product_id IN (?, ?)";
        Query nativeQuery = eManager.createNativeQuery(sql);
        nativeQuery.setParameter(1, 102);
        nativeQuery.setParameter(2, 104);

        int rows = nativeQuery.executeUpdate();
        System.out.println("✅ Rows updated: " + rows);

        transaction.commit();
    }

    // 🔹 DELETE by condition using Native SQL
    private static void delete() {
        EntityTransaction transaction = eManager.getTransaction();
        transaction.begin();

        String sql = "DELETE FROM Product_1 WHERE price = ?";
        Query nativeQuery = eManager.createNativeQuery(sql);
        nativeQuery.setParameter(1, 100);

        int rows = nativeQuery.executeUpdate();
        System.out.println("✅ Rows deleted: " + rows);

        transaction.commit();
    }
}
