# Single Row CRUD Operations on Hibernate JPA

## 📦 Project Structure

```

src/
├── com/main/CrudOperation.java
├── com/main/Employee.java
├── META-INF/
│ ├── persistence.xml
│ └── orm.xml

```

### Note:

- Add mapping file in the configuration file.
- `<mapping-file>META-INF/orm.xml</mapping-file>`

---

## 🧠 Java Code: `CrudOperation.java`

This class performs basic **CRUD operations** using JPA `EntityManager` with Hibernate as the provider.

### 🏁 Initialization

```java
eFactory = Persistence.createEntityManagerFactory("config");
eManager = eFactory.createEntityManager();
```

✅ Loads `persistence.xml` and sets up a connection with DB.

---

### 🟩 Insert Method

```java
eManager.persist(entity);
```

✅ Persists multiple `Employee` objects to the DB.

---

### 🟨 Read Method

```java
Employee emp = eManager.find(Employee.class, 3);
```

✅ Finds an employee with ID = 3.

---

### 🟦 Update Method

```java
employee.setPost("HR");
eManager.merge(employee);
```

✅ Updates the post (designation) of the employee.

---

### 🟥 Delete Method

```java
eManager.remove(employee);
```

✅ Deletes the employee by primary key (ID).

---

# 📘 Hibernate ORM XML Mapping (`orm.xml`)

- This file explains how to map a Java class (like `Employee`) to a database table using **Hibernate + JPA XML mapping**.

````markdown
## 🧾 Sample XML: `orm.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<entity-mappings
    version="2.2"
    xmlns="http://xmlns.jcp.org/xml/ns/persistence/orm"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence/orm
                        http://xmlns.jcp.org/xml/ns/persistence/orm_2_2.xsd">

    <entity class="com.main.Employee" access="PROPERTY">
        <table name="Employee"/>

        <attributes>
            <id name="id">
                <column name="Employee_id"/>
            </id>

            <basic name="name">
                <column name="Employee_Name"/>
            </basic>

            <basic name="city">
                <column name="Employee_City"/>
            </basic>

            <basic name="post">
                <column name="Employee_Post"/>
            </basic>

            <basic name="mobileNo">
                <column name="E_MobileNumber"/>
            </basic>
        </attributes>
    </entity>
</entity-mappings>
```
````

---

## 📌 Tag-by-Tag Explanation

### 🔖 `<entity class="..." access="...">`

Maps a specific Java class to a table.

| Attribute | Description                                                |
| --------- | ---------------------------------------------------------- |
| `class`   | Fully-qualified class name (e.g., `com.main.Employee`)     |
| `access`  | How Hibernate accesses data in the entity — **important!** |

#### ✅ Possible values for `access`

| Value      | Description                                      |
| ---------- | ------------------------------------------------ |
| `FIELD`    | Hibernate uses **private fields** directly       |
| `PROPERTY` | Hibernate uses **getters/setters** (recommended) |

> 🧠 Tip: Use `PROPERTY` if your entity class uses private fields and public getter/setters.

---

### 🔖 `<table name="..."/>`

Specifies the database table name to which the class is mapped.

```xml
<table name="Employee"/>
```

> This means the class `Employee` maps to a table called `Employee`.

---

## 🧩 `<attributes>` Section

Used to map Java fields or properties to DB columns.

### 🔖 `<id name="...">`

Defines the **primary key** of the entity.

```xml
<id name="id">
  <column name="Employee_id"/>
</id>
```

| Attribute  | Description                                      |
| ---------- | ------------------------------------------------ |
| `name`     | Java property name to be used as the primary key |
| `<column>` | Defines the DB column to map it to               |

---

### 🔖 `<basic name="...">`

Used to map normal fields like name, city, etc.

```xml
<basic name="name">
  <column name="Employee_Name"/>
</basic>
```

> This tells Hibernate to map `getName()` (or `name`) in Java to `Employee_Name` in DB.

---

## 🛡️ Applying Constraints to Columns

The `<column>` tag allows constraints and metadata to be added to columns.

### 🔖 Common `<column>` Attributes

| Attribute   | Meaning                                             |
| ----------- | --------------------------------------------------- |
| `name`      | DB column name                                      |
| `nullable`  | `false` → NOT NULL, `true` (default) → NULL allowed |
| `unique`    | `true` makes column UNIQUE                          |
| `length`    | Sets max length (for `VARCHAR`)                     |
| `precision` | For decimal precision                               |
| `scale`     | Number of digits after decimal point                |

---

### ✅ Example: Adding Constraints

```xml
<basic name="name">
  <column name="Employee_Name" length="50" nullable="false" unique="true"/>
</basic>
```

This maps the `name` property to:

```sql
Employee_Name VARCHAR(50) NOT NULL UNIQUE
```

---

### 🔖 Example with Primary Key

```xml
<id name="id">
  <column name="Employee_id" nullable="false"/>
</id>
```

> Ensures that the primary key column is **NOT NULL**.

---

## 🧠 Best Practices

- Use `access="PROPERTY"` for clean JavaBeans-style code.
- Always specify `nullable="false"` for primary keys.
- Use `length` on `VARCHAR` columns to control DB size.
- Prefer XML or annotations — but never mix both for the same entity.

---

# 🔍 What Happens If `<table>` or `<column>` Is Not Given in Hibernate (XML Mapping)?

Hibernate uses **default naming conventions** if `table` or `column` names are not explicitly specified in XML or annotations.

---

## ✅ 1. If `<table name="..."/>` Is **NOT** Provided:

```xml
<entity class="com.main.Employee">
    <!-- <table name="Employee"/> is missing -->
</entity>
```

### 🔸 What Happens?

- Hibernate automatically generates the table name using the **simple class name**.
- In this case, the table name will be:
  ➤ `Employee` (same as the class name)

> 🧠 **Note:** Hibernate may apply **naming strategy rules** like converting `CamelCase` to `snake_case` depending on the dialect or strategy settings.

---

## ✅ 2. If `<column name="..."/>` Is **NOT** Provided for a Field:

```xml
<basic name="name">
    <!-- <column name="Employee_Name"/> is missing -->
</basic>
```

### 🔸 What Happens?

- Hibernate will use the **property name** (`name`, `city`, etc.) as the **column name**.
- In this case:

  - Property `name` → column `name`
  - Property `mobileNo` → column `mobileNo`

> 🧠 **Note:** Default column names match the **Java property names**, unless naming strategy says otherwise.

```

```
