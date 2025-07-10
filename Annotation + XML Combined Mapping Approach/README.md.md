# 🔁 Hibernate: Annotation + XML Combined Mapping Approach

Hibernate supports using **both Annotations and XML** together.
This is useful when:

- You want to control **metadata through annotations**, but
- Still manage **which entities to load** or **override mappings** in XML

---

## ✅ 1. Hybrid Mapping Setup

### 📁 `persistence.xml`

```xml
<persistence-unit name="config">
    ...
    <!-- You specify the class name to map (not orm.xml) -->
    <class>com.main.Employee</class>
</persistence-unit>
```

> 🔸 This means Hibernate will use annotations **inside the `Employee` class**.

---

## ✅ 2. Entity Class Using Annotations

```java
package com.main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Marks this class as a Hibernate-managed entity
@Entity

// Optional: Maps to table name "EMP_2" instead of default "Employee"
@Table(name = "EMP_2")
public class Employee {

    // Marks this field as the primary key
    @Id
    @Column(name = "e_id")  // Optional: If not given, uses field name "id"
    private int id;

    // Maps field to column 'e_name', sets max length to 25
    @Column(name = "e_name", length = 25, nullable = false, unique = true)
    private String name;

    // Maps field to column 'e_city'
    @Column(name = "e_city")
    private String city;

    // Maps field to column 'e_post'
    @Column(name = "e_post")
    private String post;

    // Maps field to column 'e_phone'
    @Column(name = "e_phone", nullable = false)
    private long mobileNo;

    // --- Omitted: constructors, getters/setters, toString ---
}
```

---

## 🔍 Annotation Summary (with Constraints)

| Annotation           | Purpose                         | Notes               |
| -------------------- | ------------------------------- | ------------------- |
| `@Entity`            | Declares the class as an Entity | Required            |
| `@Table(name="...")` | Maps to a custom DB table name  | Optional            |
| `@Id`                | Marks primary key               | Required            |
| `@Column`            | Maps field to DB column         | Can add constraints |

---

## ⚙️ Common `@Column` Attributes

| Attribute    | Description                        |
| ------------ | ---------------------------------- |
| `name`       | DB column name                     |
| `length`     | Max character length (for strings) |
| `nullable`   | `false` = NOT NULL                 |
| `unique`     | `true` = UNIQUE constraint         |
| `updatable`  | `false` = value won't be updated   |
| `insertable` | `false` = value won't be inserted  |

---

### 🧪 Example Constraints via Annotation:

```java
@Column(name = "e_name", length = 25, nullable = false, unique = true)
private String name;
```

This means:

- Column name will be `e_name`
- `NOT NULL` constraint will be applied
- `UNIQUE` constraint will be applied
- Maximum 25 characters allowed

---

## 🧠 Why Use XML with Annotations?

- Sometimes used in **legacy code** where XML mappings already exist
- Can **override annotations with XML** if needed
- Keeps DB config and entity config separate (separation of concerns)

---

## ✅ What Happens If We Use Both Annotation and XML in Hibernate?

### 🧩 Situation:

You use annotations **in the entity class**, and also define **XML mapping** (e.g., in `orm.xml` or via `@MappingFile` in `persistence.xml`).

---

### 🧨 Which One Wins?

**By default:**

> 🔸 **XML takes precedence over annotations** when both are provided for the same mapping.

### ✅ Example:

If `Employee` class has:

```java
@Column(name = "e_name")
private String name;
```

And in `orm.xml` you define:

```xml
<basic name="name">
    <column name="emp_full_name"/>
</basic>
```

➡️ Hibernate will use **`emp_full_name`** as the column name, because the XML mapping **overrides the annotation**.

---

### 📌 Why?

Because XML was originally used before annotations, and Hibernate gives XML higher priority for **backward compatibility**.

---

## 🧠 Best Practice:

- Use **either annotations OR XML**, not both for the same entity (to avoid confusion).
- If you must use both, clearly document **what is overridden in XML**.

---
