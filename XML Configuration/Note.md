# Hibernate XML-Based Configuration and Code Workflow (Full Explanation)

This markdown document provides a complete explanation of how Hibernate works using **XML-based configuration**. It includes:

1. `persistence.xml` (configuration file)
2. `orm.xml` (entity mapping file)
3. `Student.java` (entity class)
4. `Launch.java` (main execution class)
5. Project folder structure

---

## 🗂️ Project Structure

```
src/
 └── com/
     └── HibernetExamples/
         ├── Launch.java
         └── Student.java
META-INF/
 ├── persistence.xml
 └── orm.xml
```

---

## 1. `persistence.xml`

Located in: `META-INF/persistence.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
    xmlns="http://xmlns.jcp.org/xml/ns/persistence"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">

    <persistence-unit name="config">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <mapping-file>META-INF/orm.xml</mapping-file>

        <properties>
            <!-- Database Configuration -->
            <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/hibernetexamples"/>
            <property name="javax.persistence.jdbc.user" value="root"/>
            <property name="javax.persistence.jdbc.password" value="@Pass123"/>

            <!-- Hibernate Dialect for MySQL -->
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL8Dialect"/>

            <!-- Auto-create or update tables -->
            <property name="hibernate.hbm2ddl.auto" value="update"/>

            <!-- Log SQL statements in console -->
            <property name="hibernate.show_sql" value="true"/>
        </properties>
    </persistence-unit>
</persistence>
```

### Explanation:

- Defines DB connection and Hibernate provider.
- Specifies the mapping file (`orm.xml`).
- Sets SQL dialect, DDL mode (`update`), and SQL logging.

---

## 2. `orm.xml`

Located in: `META-INF/orm.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<entity-mappings version="2.2"
    xmlns="http://xmlns.jcp.org/xml/ns/persistence/orm"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence/orm http://xmlns.jcp.org/xml/ns/persistence/orm_2_2.xsd">

    <entity class="com.HibernetExamples.Student" access="PROPERTY">
        <table name="Student_Records"/>
        <attributes>
            <id name="id">
                <column name="Student_id"/>
            </id>
            <basic name="name">
                <column name="Student_name"/>
            </basic>
            <basic name="course">
                <column name="Student_course"/>
            </basic>
            <basic name="city">
                <column name="Student_city"/>
            </basic>
            <basic name="mobileNo">
                <column name="Student_mobile_Number"/>
            </basic>
        </attributes>
    </entity>
</entity-mappings>
```

### Explanation:

- Maps the `Student` class to the DB table `Student_Records`.
- Maps each Java property to a specific DB column name.
- Uses `PROPERTY` access, so getters/setters are used.

---

## 3. `Student.java`

Located in: `com.HibernetExamples.Student`

```java
package com.HibernetExamples;

public class Student {
    private int id;
    private String name;
    private String course;
    private String city;
    private long mobileNo;

    public Student() {}

    public Student(int id, String name, String course, String city, long mobileNo) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.city = city;
        this.mobileNo = mobileNo;
    }

    // Getters and setters (used because of access="PROPERTY" in orm.xml)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public long getMobileNo() { return mobileNo; }
    public void setMobileNo(long mobileNo) { this.mobileNo = mobileNo; }
}
```

### Explanation:

- POJO (Plain Old Java Object) with properties.
- Used for entity representation and mapping.

---

## 4. `Launch.java`

Located in: `com.HibernetExamples.Launch`

```java
package com.HibernetExamples;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Launch {
    public static void main(String[] args) {

        // Load configuration from persistence.xml
        EntityManagerFactory eFactory = Persistence.createEntityManagerFactory("config");
        EntityManager eManager = eFactory.createEntityManager();

        // Begin transaction
        EntityTransaction transaction = eManager.getTransaction();
        transaction.begin();

        // Create and persist Student records
        eManager.persist(new Student(101, "Alice", "CS", "NY", 1111111111L));
        eManager.persist(new Student(102, "Bob", "EE", "LA", 2222222222L));
        eManager.persist(new Student(103, "Charlie", "ME", "Chi", 3333333333L));
        eManager.persist(new Student(104, "Diana", "Phy", "Aus", 4444444444L));
        eManager.persist(new Student(105, "Eve", "Chem", "Mia", 5555555555L));

        // Commit changes and close resources
        transaction.commit();
        System.out.println("Data Inserted Successfully.");

        eManager.close();
        eFactory.close();
    }
}
```

### Explanation:

- Reads configuration from `persistence.xml`
- Uses JPA EntityManager to persist Student objects
- Data is inserted into the `Student_Records` table as defined in `orm.xml`

---

## ✅ Output

- SQL queries printed in the console (because of `hibernate.show_sql=true`)
- Data inserted in MySQL table `Student_Records`

---

## ✅ Summary Flow:

1. `persistence.xml` → defines DB and ORM settings.
2. `orm.xml` → maps Java class to DB table.
3. `Student.java` → POJO for data representation.
4. `Launch.java` → inserts data using JPA.

---

Let me know if you'd like the same guide for annotation-based Hibernate, CRUD operations, or relationships!
