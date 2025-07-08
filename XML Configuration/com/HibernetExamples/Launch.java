package com.HibernetExamples;

// JPA and Hibernate imports
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Launch {

    public static void main(String[] args) {

        // Step 1: Load the persistence unit "config" from persistence.xml
        EntityManagerFactory eFactory = Persistence.createEntityManagerFactory("config");

        // Step 2: Create an EntityManager to interact with the database
        EntityManager eManager = eFactory.createEntityManager();

        // Step 3: Begin a transaction before performing any DB operations
        EntityTransaction transaction = eManager.getTransaction();
        transaction.begin();

        // Step 4: Create multiple Student objects (these will be inserted as DB rows)
        Student student1 = new Student(101, "Alice", "CS", "NY", 1111111111L);
        Student student2 = new Student(102, "Bob", "EE", "LA", 2222222222L);
        Student student3 = new Student(103, "Charlie", "ME", "Chi", 3333333333L);
        Student student4 = new Student(104, "Diana", "Phy", "Aus", 4444444444L);
        Student student5 = new Student(105, "Eve", "Chem", "Mia", 5555555555L);

        // Step 5: Persist (save) each student object into the database
        // Hibernate will convert these into SQL INSERT statements based on XML mapping
        eManager.persist(student1);
        eManager.persist(student2);
        eManager.persist(student3);
        eManager.persist(student4);
        eManager.persist(student5);

        // Confirmation message
        System.out.println("Data Inserted Successfully.");

        // Step 6: Commit the transaction (executes all SQL statements)
        transaction.commit();

        // Step 7: Close the EntityManager and EntityManagerFactory to release resources
        eManager.close();
        eFactory.close();
    }
}
