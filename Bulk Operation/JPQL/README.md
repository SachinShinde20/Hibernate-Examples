# 🟨 JPQL (Java Persistence Query Language) – Full Notes

---

### 🔷 What is JPQL?

JPQL is an **object-oriented query language** used to perform operations (SELECT, UPDATE, DELETE) on **entities and their fields**, not directly on database tables.

> ✅ JPQL queries work on Java classes and fields, not database table names or column names.

---

### 🔷 JPQL vs Native SQL – Key Differences

| Feature        | JPQL                                         | Native SQL                         |
| -------------- | -------------------------------------------- | ---------------------------------- |
| Operates On    | Entity classes and fields                    | Tables and columns directly        |
| Insert Support | ❌ Not supported – use `persist()` instead   | ✅ Supported using `INSERT`        |
| Syntax         | HQL/JPQL syntax (like SQL, but with classes) | Standard SQL syntax                |
| Portability    | ✅ DB-independent                            | ❌ Tied to specific DB SQL dialect |
| Method         | `createQuery("JPQL")`                        | `createNativeQuery("SQL")`         |
| Return Type    | Entities, projections                        | Object arrays or scalar values     |

---

## 🔸 INSERT in JPQL

> JPQL **does not support `INSERT` query syntax**. You must use the `EntityManager.persist()` method to insert data.

```java
// ✅ Insert – Use persist(), not INSERT statement
public static void insert() {
    EntityTransaction transaction = eManager.getTransaction();
    transaction.begin();

    Product product1 = new Product(101, "Mobile", "11-11-2022", 20000);
    Product product2 = new Product(102, "Wooden Desk", "21-02-2022", 7000);
    Product product3 = new Product(103, "Mechanical Pen", "01-08-2022", 20);

    eManager.persist(product1);
    eManager.persist(product2);

    eManager.flush(); // 🔄 Optional - flushes pending changes to DB

    eManager.persist(product3);

    transaction.commit();
}
```

#### 🟢 `flush()` in Hibernate

- Flush synchronizes the **in-memory entity state** with the **database**.
- Not always required explicitly — Hibernate flushes automatically:

  - Before `commit()`
  - Before a query (if needed)

- Use `flush()` when:

  - You want changes to be pushed **before commit**
  - You want to detect DB constraint violations early

---

## 🔸 SELECT Queries in JPQL

### 1️⃣ Read Full Entity Records

```java
public static void readAll() {
    String query = "SELECT p FROM Product p WHERE p.price > :min"; // Use entity and field names

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
```

### 2️⃣ Read Partial Fields (Projection)

```java
public static void read() {
    String query = "SELECT p.id, p.name FROM Product p WHERE p.price > :min";

    Query jpQuery = eManager.createQuery(query);
    jpQuery.setParameter("min", 100);

    List<Object[]> resultList = jpQuery.getResultList();

    for (Object[] row : resultList) {
        System.out.println("ID: " + row[0] + "\tName: " + row[1]);
    }
}
```

---

## 🔸 UPDATE Using JPQL

- You can perform bulk updates using `UPDATE` queries.
- Best practice: define queries using `@NamedQuery`.

```java
// Defined in Entity class
@NamedQuery(
    name = "Product.UpdatePriceById",
    query = "UPDATE Product p SET p.price = :price WHERE p.id = :id"
)
```

```java
// Calling the NamedQuery
public static void update() {
    EntityTransaction transaction = eManager.getTransaction();
    transaction.begin();

    Query jQuery = eManager.createNamedQuery("Product.UpdatePriceById"); // -> Pass it as String
    jQuery.setParameter("price", 25);
    jQuery.setParameter("id", 103);

    int rowsUpdated = jQuery.executeUpdate();
    System.out.println("Update Successful");
    System.out.println("Rows updated: " + rowsUpdated);

    transaction.commit();
}
```

---

## 🔸 DELETE Using JPQL

```java
// In Entity Class
@NamedQuery(
    name = "Product.deleteByName",
    query = "DELETE FROM Product p WHERE p.name = :name"
)
```

```java
public static void delete() {
    EntityTransaction transaction = eManager.getTransaction();
    transaction.begin();

    Query jQuery = eManager.createNamedQuery("Product.deleteByName"); // -> Pass it as String
    jQuery.setParameter("name", "Wooden Desk");

    int rowsDeleted = jQuery.executeUpdate();
    System.out.println("Delete Successful");
    System.out.println("Rows deleted: " + rowsDeleted);

    transaction.commit();
}
```

---

## 🔸 COUNT (Scalar Result)

```java
// In Entity Class
@NamedQuery(
    name = "Product.countProducts",
    query = "SELECT COUNT(p) FROM Product p"
)
```

```java
public static void getCount() {
    Query jQuery = eManager.createNamedQuery("Product.countProducts"); // -> Pass it as String

    Object count = jQuery.getSingleResult(); // returns Long or BigInteger
    int totalProducts = Integer.parseInt(count.toString());

    System.out.println("Total Products: " + totalProducts);
}
```

---

## 🔸 Named Parameters in JPQL

- Use `:paramName` instead of `?` placeholders.
- Example:

  ```java
  Query q = eManager.createQuery("SELECT p FROM Product p WHERE p.price > :min");
  q.setParameter("min", 500);
  ```

---

## 🔸 JPQL Query Method Summary

| Operation | JPQL Query Structure                                   | Notes                                |
| --------- | ------------------------------------------------------ | ------------------------------------ |
| SELECT    | `SELECT p FROM Product p`                              | Works with entity & field names only |
| INSERT    | ❌ Not supported                                       | Use `persist()`                      |
| UPDATE    | `UPDATE Product p SET p.name = :name WHERE p.id = :id` | Use `executeUpdate()`                |
| DELETE    | `DELETE FROM Product p WHERE p.name = :name`           | Use `executeUpdate()`                |
| COUNT     | `SELECT COUNT(p) FROM Product p`                       | Returns Long/BigInteger              |

---

## 🔸 Final Notes

- ✅ JPQL makes your code **database-independent**.
- ✅ Entity classes must be annotated with `@Entity`.
- ❌ JPQL cannot manipulate schema or raw tables.
- ✅ Use `@NamedQuery` or `createQuery()` to avoid hardcoding.
- ✅ Always use **field names**, not column names, in JPQL.
- ✅ Use `flush()` to force changes to DB before commit (optional).

---

---

# ✅ Placeholders vs Named Parameters

#### 🔹 Positional Placeholders `?`

Most common in native SQL:

```java
"SELECT * FROM Product WHERE price > ?"
q.setParameter(1, 100);
```

#### 🔹 Named Parameters (Recommended for JPQL)

Cleaner and self-documenting (only in JPQL):

```java
"SELECT p FROM Product p WHERE p.price > :price"
q.setParameter("price", 100);
```

- ✅ Better readability
- ❌ Not supported in Native SQL

---

---

# ✅ Reusable Queries: `@NamedQuery` & `@NamedNativeQuery`

#### 🔸 `@NamedQuery` (JPQL-Based)

```java
@NamedQuery(
    name = "Product.findExpensive",
    query = "SELECT p FROM Product p WHERE p.price > :price"
)
```

- 🔸 JPQL syntax
- ✅ Works with entities

Usage:

```java
Query q = eManager.createNamedQuery("Product.findExpensive");
q.setParameter("price", 100);
List<Product> list = q.getResultList();
```

---

#### 🔸 `@NamedNativeQuery` (SQL-Based)

```java
@NamedNativeQuery(
    name = "Product.nativeFindExpensive",
    query = "SELECT * FROM Product WHERE price > ?",
    resultClass = Product.class
)
```

- 🔸 Native SQL syntax
- ✅ Requires `resultClass` to map data to entity

Usage:

```java
Query q = eManager.createNamedQuery("Product.nativeFindExpensive");
q.setParameter(1, 100);
List<Product> list = q.getResultList();
```

---

### ✅ Difference Between `@NamedQuery` and `@NamedNativeQuery`

| Feature            | `@NamedQuery`              | `@NamedNativeQuery`         |
| ------------------ | -------------------------- | --------------------------- |
| Query Type         | JPQL                       | SQL                         |
| Mapping Entity     | Yes                        | Yes (with `resultClass`)    |
| Placeholder Style  | Named Parameters (`:name`) | Positional Parameters (`?`) |
| Syntax Flexibility | Limited to JPQL            | Supports full SQL features  |

---
