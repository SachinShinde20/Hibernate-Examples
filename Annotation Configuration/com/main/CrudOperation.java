package com.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.hibernate.jpa.HibernatePersistenceProvider;

public class CrudOperation {

	private static EntityManagerFactory eFactory;
	private static EntityManager eManager;

	public static void main(String[] args) {

		// Load properties
		Properties properties = new Properties();
		try (InputStream iReader = CrudOperation.class.getClassLoader().getResourceAsStream("files/config.properties")) {
			if (iReader == null) {
				System.err.println("❌ Error: config.properties file not found in classpath.");
				return;
			}
			properties.load(iReader);
		} catch (IOException e) {
			System.err.println("❌ Error loading config.properties: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		// Convert to Map<String, String>
		Map<String, String> map = new HashMap<>();
		for (String key : properties.stringPropertyNames()) {
			map.put(key, properties.getProperty(key));
		}

		try {
			// Programmatic Hibernate + JPA setup without persistence.xml
			eFactory = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new PersistenceUnitInfoImp(properties), map);

			eManager = eFactory.createEntityManager();

			insert();
			read();

		} catch (PersistenceException e) {
			System.err.println("❌ Error initializing JPA/Hibernate: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (eManager != null && eManager.isOpen()) eManager.close();
			if (eFactory != null && eFactory.isOpen()) eFactory.close();
		}
	}

	public static void insert() {
		EntityTransaction transaction = null;
		try {
			transaction = eManager.getTransaction();
			transaction.begin();

			eManager.persist(new Employee(1, "John Doe", "New York", "Manager", 1234567890L));
			eManager.persist(new Employee(2, "Jane Smith", "Los Angeles", "Developer", 9876543210L));
			eManager.persist(new Employee(3, "Peter Jones", "Chicago", "Analyst", 5551112222L));

			transaction.commit();
			System.out.println("✅ Data Inserted Successfully...");
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) transaction.rollback();
			System.err.println("❌ Error inserting data: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void read() {
		final int searchId = 2;
		Employee emp = eManager.find(Employee.class, searchId);
		if (emp != null) {
			System.out.println("✅ Data Retrieved: " + emp);
		} else {
			System.out.println("❌ Employee not found with ID " + searchId);
		}
	}
}
