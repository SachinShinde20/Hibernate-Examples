package jpql_Approch;

import java.util.List;
import javax.persistence.*;

public class Main {
    static EntityManagerFactory eFactory;
    static EntityManager eManager;

    public static void main(String[] args) {

        eFactory = Persistence.createEntityManagerFactory("config");
        eManager = eFactory.createEntityManager();

        // insert();         // Insert operation
        // readAll();        // Read all full entities
        // read();           // Read selected fields (projection)
        // update();         // Update price by ID
        // delete();           // Delete by name
         getCount();      // Count all products

        System.out.println(eManager);
    }

    // 🔸 Insert – JPQL doesn't support INSERT statements, use persist()
    public static void insert() {
        EntityTransaction transaction = eManager.getTransaction();
        transaction.begin();

        Product product1 = new Product(101, "Mobile", "11-11-2022", 20000);
        Product product2 = new Product(102, "Wooden Desk", "21-02-2022", 7000);
        Product product3 = new Product(103, "Mechanical Pen", "01-08-2022", 20);

        eManager.persist(product1);
        eManager.persist(product2);

        eManager.flush(); // Forces pending inserts to DB (optional here)

        eManager.persist(product3);

        transaction.commit();
    }

    // 🔸 Read All – Full Entity objects
    public static void readAll() {
        String query = "SELECT p FROM Product p WHERE p.price > :min";

        Query jpQuery = eManager.createQuery(query);
        jpQuery.setParameter("min", 100); // Named parameter

        List<Product> resultList = jpQuery.getResultList();

        for (Product row : resultList) {
            System.out.print(row.getId());
            System.out.print("\t" + row.getName());
            System.out.print("\t" + row.getManufacturingDate());
            System.out.print("\t" + row.getPrice());
            System.out.println();
        }
    }

    // 🔸 Read – Projection (partial fields)
    public static void read() {
        String query = "SELECT p.id, p.name FROM Product p WHERE p.price > :min";

        Query jpQuery = eManager.createQuery(query);
        jpQuery.setParameter("min", 100); // Named parameter

        List<Object[]> resultList = jpQuery.getResultList();

        for (Object[] row : resultList) {
            System.out.println("ID: " + row[0] + "\tName: " + row[1]);
        }
    }

    // 🔸 Update – Using NamedQuery with parameters
    public static void update() {
        EntityTransaction transaction = eManager.getTransaction();
        transaction.begin();

        Query jQuery = eManager.createNamedQuery("Product.UpdatePriceById"); //-> Pass as a String
        jQuery.setParameter("price", 25);
        jQuery.setParameter("id", 103);

        int rowsUpdated = jQuery.executeUpdate();
        System.out.println("Update Successful");
        System.out.println("Rows updated: " + rowsUpdated);

        transaction.commit();
    }

    // 🔸 Delete – Using NamedQuery with parameter
    public static void delete() {
        EntityTransaction transaction = eManager.getTransaction();
        transaction.begin();

        Query jQuery = eManager.createNamedQuery("Product.deleteByName"); //-> Pass as a String
        jQuery.setParameter("name", "Wooden Desk");

        int rowsUpdated = jQuery.executeUpdate();
        System.out.println("Delete Successful");
        System.out.println("Rows deleted: " + rowsUpdated);

        transaction.commit();
    }

    // 🔸 Count – Using NamedQuery (returns single result)
    public static void getCount() {
        Query jQuery = eManager.createNamedQuery("Product.countProducts");

        Object count = jQuery.getSingleResult(); // returns Long or BigInteger

        int totalProducts = Integer.parseInt(count.toString());

        System.out.println("Total Products: " + totalProducts);
    }
}