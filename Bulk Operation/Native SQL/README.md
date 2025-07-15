# 📘 Hibernate JPA: Bulk Row Operations Using Native SQL

**_In Short We will execute pure MySQl, Oracle Quries here, Which can Directly executed on Database_**

---

## ✅ What Are Bulk Row Operations?

Bulk operations are **native SQL queries** directly executed on the database using JPA's `EntityManager`. These operations are typically used for:

- Batch **Insert**
- Batch **Update**
- Batch **Delete**
- Direct **Read** using SQL syntax

✅ **Advantages**:

- Fast performance (bypasses JPA-level object tracking)
- Executes directly on DB
- Useful for mass updates or external data interaction

---

## ✅ Syntax Overview

```java
String sql = "Query with Positional Parameters (?, ?)";
Query nativeQuery = eManager.createNativeQuery(sql);
nativeQuery.setParameter(1, value1); // Index starts from 1
nativeQuery.setParameter(2, value2);
int result = nativeQuery.executeUpdate(); // Executes insert, update, delete
```

---

### 🔍 Explanation of Each Line

1. **`createNativeQuery(sql)`**
   Creates a native SQL query object using the raw SQL string.

   ```java
   Query nativeQuery = eManager.createNativeQuery("INSERT INTO Product VALUES (?, ?, ?, ?)");
   ```

2. **`setParameter(index, value)`**
   Binds values to placeholders (`?`) in the SQL query.
   Index **starts from 1**, not 0.

   ```java
   nativeQuery.setParameter(1, 101);
   nativeQuery.setParameter(2, "Mouse");
   ```

3. **`executeUpdate()`**
   Executes the query against the database.
   ✅ Returns the **number of rows affected** (inserted/updated/deleted).

4. **`getResultList()`**
   Used with `SELECT` queries. Returns results as:

   - `List<Object[]>` if partial columns selected
   - `List<Entity>` if mapped class provided and full columns selected

---

## 🛠 CRUD Example Using Native SQL

### 🔸 INSERT Example:

```java
EntityTransaction tx = eManager.getTransaction();
tx.begin();

String sql = "INSERT INTO Product (Product_id, name, manufacturingDate, price) VALUES (?, ?, ?, ?)";
Query q = eManager.createNativeQuery(sql);
q.setParameter(1, 101);
q.setParameter(2, "Keyboard");
q.setParameter(3, "2024-07-01");
q.setParameter(4, 350.00);

int rows = q.executeUpdate(); // ✅ returns 1
System.out.println("Rows inserted: " + rows);

tx.commit();
```

---

### 🔸 UPDATE Example:

```java
EntityTransaction tx = eManager.getTransaction();
tx.begin();

String sql = "UPDATE Product SET price = ? WHERE Product_id = ?";
Query q = eManager.createNativeQuery(sql);
q.setParameter(1, 999.99);
q.setParameter(2, 101);

int rows = q.executeUpdate(); // ✅ returns no. of updated rows
System.out.println("Rows updated: " + rows);

tx.commit();
```

---

### 🔸 DELETE Example:

```java
EntityTransaction tx = eManager.getTransaction();
tx.begin();

String sql = "DELETE FROM Product WHERE price < ?";
Query q = eManager.createNativeQuery(sql);
q.setParameter(1, 100.0);

int rows = q.executeUpdate(); // ✅ returns deleted row count
System.out.println("Rows deleted: " + rows);

tx.commit();
```

---

## 🔍 READ Operation via Native SQL

### ☑ Option 1: Fetching **Selected Columns Only**

```java
String sql = "SELECT Product_id, name, price FROM Product WHERE price > ?";
Query q = eManager.createNativeQuery(sql);
q.setParameter(1, 100.0);

List<Object[]> resultList = q.getResultList(); // ✅ Important

for (Object[] row : resultList) {
    System.out.println("ID: " + row[0] + ", Name: " + row[1] + ", Price: " + row[2]);
}
```

- ✅ Good when you want only a few columns.
- ❌ Cannot map directly to entity unless all columns are included.

---

### ☑ Option 2: Fetching **All Columns** Using Entity

```java
String sql = "SELECT * FROM Product WHERE price > ?";
Query q = eManager.createNativeQuery(sql, Product.class); -> // The <Entity>.class required
q.setParameter(1, 100.0);

List<Product> resultList = q.getResultList(); // ✅ Important

for (Product product : resultList) {
    System.out.println(product);
}
```

- ✅ Good when you want full entity data.
- ❌ Should not be used with partial column selection.

---

## 🔍 When to Use `Product.class`?

| Condition                            | Use `Product.class`? | Return Type      |
| ------------------------------------ | -------------------- | ---------------- |
| SELECT all columns from mapped table | ✅ Yes               | `List<Product>`  |
| SELECT some columns only             | ❌ No                | `List<Object[]>` |
| Using DTO (JPQL only)                | ❌ No                | `List<YourDTO>`  |

---

## 🔁 `persist()` vs `createNativeQuery()`

| Feature                      | `persist()`                          | `createNativeQuery()`                             |
| ---------------------------- | ------------------------------------ | ------------------------------------------------- |
| API Type                     | JPA Object-based                     | SQL-based (native)                                |
| Purpose                      | Persists entity instance             | Executes raw SQL (insert, update, delete, select) |
| Mapping Required             | Yes (entity class must be annotated) | No (can execute on any table)                     |
| Supports Object Relationship | ✅ Yes (ORM aware)                   | ❌ No (pure SQL only)                             |
| Return Value                 | `void`                               | `int` (affected rows) or `List`                   |
| Example                      | `eManager.persist(product);`         | `nativeQuery.executeUpdate();`                    |
| Best Use Case                | Java-centric, portable code          | Performance-focused, complex raw SQL              |

---

## 🔍 When to Use Which?

- Use **`persist()`**:

  - When working within the ORM model
  - When you want JPA to manage entities, caching, relationships

- Use **`createNativeQuery()`**:

  - When performance or SQL flexibility is critical
  - For batch/bulk operations
  - When querying non-entity tables

---

## 🔍 Is Transaction Required?

| Operation | Requires Transaction?               |
| --------- | ----------------------------------- |
| `INSERT`  | ✅ Yes                              |
| `UPDATE`  | ✅ Yes                              |
| `DELETE`  | ✅ Yes                              |
| `SELECT`  | ❌ No (unless explicitly modifying) |

---

## 🧾 Summary

- `createNativeQuery(sql)` allows raw SQL execution.
- `setParameter(1, value)` binds positional parameters.
- `executeUpdate()` returns affected rows.
- `getResultList()` fetches query results (object arrays or entity list).
- Use `Product.class` only when all entity fields are selected.
- `persist()` is JPA-managed; `createNativeQuery()` is native SQL-based.
