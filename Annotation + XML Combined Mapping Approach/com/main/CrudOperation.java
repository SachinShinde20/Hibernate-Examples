package com.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class CrudOperation {
    private static EntityManagerFactory eFactory;
    private static EntityManager eManager;

    public static void main(String[] args) {
        
        eFactory = Persistence.createEntityManagerFactory("config");

        eManager = eFactory.createEntityManager();

        // Uncomment only one operation at a time to avoid conflicts
        // insert();   // For Inserting data
        // read();     // For Reading data
        // update();   // For Updating data
        // delete();      // For Deleting data

        eManager.close();
        eFactory.close();
    }

    // INSERT operation
    public static void insert() {
        EntityTransaction transaction = eManager.getTransaction();
        transaction.begin();

        Employee e1 = new Employee(1, "John Doe", "New York", "Manager", 1234567890L);
        Employee e2 = new Employee(2, "Jane Smith", "Los Angeles", "Developer", 9876543210L);
        Employee e3 = new Employee(3, "Peter Jones", "Chicago", "Analyst", 5551112222L);
        Employee e4 = new Employee(4, "Mary Brown", "Houston", "HR Specialist", 4443332211L);
        Employee e5 = new Employee(5, "David Lee", "Miami", "Designer", 7778889900L);

        eManager.persist(e1);
        eManager.persist(e2);
        eManager.persist(e3);
        eManager.persist(e4);
        eManager.persist(e5);

        transaction.commit();
        System.out.println("✅ Data Inserted Successfully...");
    }

    // READ operation
    public static void read() {
        Employee emp = eManager.find(Employee.class, 2);
        
        if (emp != null) {
            System.out.println("✅ Data Retrieved: " + emp);
        } else {
            System.out.println("❌ Employee not found with ID 3");
        }
    }

    // UPDATE operation
    public static void update() {
        EntityTransaction transaction = eManager.getTransaction();
        transaction.begin();

        Employee employee = eManager.find(Employee.class, 3);
        if (employee != null) {
            employee.setPost("HR");        
            eManager.merge(employee);      
            System.out.println("✅ Data Updated Successfully...");
        } else {
            System.out.println("❌ Employee not found. Update failed.");
        }

        transaction.commit();
    }

    // DELETE operation
    public static void delete() {
        EntityTransaction transaction = eManager.getTransaction();
        transaction.begin();

        Employee employee = eManager.find(Employee.class, 3);
        if (employee != null) {
            eManager.remove(employee);  
            System.out.println("✅ Data Deleted Successfully...");
        } else {
            System.out.println("❌ Employee not found. Deletion failed.");
        }

        transaction.commit();
    }
}
