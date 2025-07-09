package main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Launch {
	public static void main(String[] args) {
		
		EntityManagerFactory eFactory = Persistence.createEntityManagerFactory("config");
		
		EntityManager eManager = eFactory.createEntityManager();
				
		System.out.println(eManager);
		
		System.out.println("Connection Created Successful...");
	}

}
