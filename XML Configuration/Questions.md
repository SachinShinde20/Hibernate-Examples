## ✅ What is the Difference Between `persist()` and `merge()` in Hibernate?

| Feature   | `persist()`                          | `merge()`                                        |
| --------- | ------------------------------------ | ------------------------------------------------ |
| Purpose   | Inserts a **new** object into DB     | Updates **existing** OR inserts **if not found** |
| Exception | Throws if primary key already exists | Overwrites existing object or adds new one       |

- Note: Enevn if you Update a single value the full row will upadated

---

### 🧪 Example:

```java
Student s = new Student(1, "A", "CS", "City", 111);

// persist() – only for new objects
entityManager.persist(s);

// merge() – can reattach detached objects
Student detached = new Student(1, "Updated", "IT", "New City", 222);
Student updated = entityManager.merge(detached);
```

---

## ✅ What is `find()` and `remove()`?

### 🔹 `find(Class, id)`:

- Used to **retrieve** an entity using its **primary key**
- Returns `null` if not found

```java
Student s = entityManager.find(Student.class, 101);
System.out.println(s);
```

---

### 🔹 `remove(entity)`:

- Used to **delete** an entity from the database
- Requires a **managed object**

```java
Student s = entityManager.find(Student.class, 101);
if (s != null) {
    entityManager.remove(s);
}
```

> 🔥 Tip: Always check `null` before calling `remove()` to avoid exceptions.

---

## ✅ Why Might Hibernate Create Table Columns in a Different Order Than Declared?

### 🔹 Reason:

- Hibernate uses **Reflection API** to analyze classes.
- Java **does not preserve field order** in bytecode.
- Different JVMs and compilers may order fields **alphabetically** or differently.

### 🧠 So:

> Column order in DB may not match the order of fields in Java class.

---

### ✅ How to Control Column Order?

Hibernate doesn’t support ordering directly. But you can:

- Manually create tables using SQL
- Or use schema generation scripts (DDL) before running Hibernate

---
