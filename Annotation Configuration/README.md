# Working of Hibernate JPA without using `persistence.xml`, by manually configuring and bootstrapping it using `PersistenceUnitInfo` and `HibernatePersistenceProvider`.

---

## ✅ Goal

You're replacing `persistence.xml` by:

- Loading properties from `config.properties`
- Creating a custom `PersistenceUnitInfoImp` class
- Using `HibernatePersistenceProvider` to bootstrap JPA

---

## 🔧 Step-by-Step Workflow

### 🔹 1. Load Properties from File

```java
InputStream iReader = CrudOperation.class.getClassLoader()
        .getResourceAsStream("files/config.properties");

Properties properties = new Properties();
properties.load(iReader);
```

- This loads JDBC, Hibernate, and JPA-related properties (like URL, driver, user, dialect, etc.).
- You later convert `Properties` to `Map<String, String>` for Hibernate.

```java
Map<String, String> map = new HashMap<>();
for (String key : properties.stringPropertyNames()) {
	map.put(key, properties.getProperty(key));
}
```

---

### 🔹 2. Create EntityManagerFactory Programmatically

```java
EntityManagerFactory eFactory =
	new HibernatePersistenceProvider()
	.createContainerEntityManagerFactory(
		new PersistenceUnitInfoImp(properties),
		map
	);
```

### ✅ What’s happening?

You're directly creating the `EntityManagerFactory` without XML by supplying:

- A custom `PersistenceUnitInfoImp` object (which tells JPA about your unit config)
- A map of properties (acting like `persistence.xml` replacements)

---

## 🧠 Role of `PersistenceUnitInfo`

Normally, JPA looks for `persistence.xml` under `META-INF`. But if you're not using XML, you need to **manually provide that information**.

So, we implement the `PersistenceUnitInfo` interface to create a `PersistenceUnitInfoImp` class that provides the necessary config.

---

## 🔍 Important Methods in `PersistenceUnitInfoImp`

Let’s go through only the **necessary methods** that matter in your setup.

---

### ✅ `getPersistenceUnitName()`

```java
@Override
public String getPersistenceUnitName() {
	return "config"; // can be any name, but must match what you use
}
```

> Used to identify this persistence unit. You can name it anything (not used in bootstrapping when XML is skipped).

---

### ✅ `getManagedClassNames()`

```java
@Override
public List<String> getManagedClassNames() {
	return Arrays.asList("com.main.Employee");
}
```

> Returns the list of entity classes JPA should manage. Without `persistence.xml`, you must specify the class names explicitly here.

---

### ✅ `getProperties()`

```java
@Override
public Properties getProperties() {
	return this.properties;
}
```

> Returns all loaded configuration like `hibernate.dialect`, `javax.persistence.jdbc.url`, etc. This is equivalent to the content of `persistence.xml`.

---

### ✅ `getTransactionType()`

```java
@Override
public PersistenceUnitTransactionType getTransactionType() {
	return PersistenceUnitTransactionType.RESOURCE_LOCAL;
}
```

> Tells JPA to use local transactions (usually when you're not using JTA or a full Java EE container).

---

### ✅ `excludeUnlistedClasses()`

```java
@Override
public boolean excludeUnlistedClasses() {
	return false;
}
```

> Tells the provider to **scan the classpath** if you didn’t list everything explicitly. But even if this is `false`, you still must list the classes if scanning is not configured.

---

### ✅ Other Methods (safe defaults)

These are often not needed and return either `null` or defaults:

```java
public URL getPersistenceUnitRootUrl() { return null; }
public DataSource getJtaDataSource() { return null; }
public DataSource getNonJtaDataSource() { return null; }
public String getPersistenceProviderClassName() { return null; }
public List<String> getMappingFileNames() { return null; }
public SharedCacheMode getSharedCacheMode() { return null; }
public ValidationMode getValidationMode() { return null; }
public ClassLoader getClassLoader() { return null; }
public void addTransformer(ClassTransformer t) {}
```

Hibernate doesn’t strictly need these unless advanced use cases (like JNDI, weaving, XML mapping, etc.).

---

## 🔄 Complete Flow Summary

1. **Load properties** from `files/config.properties`
2. **Convert them** to a map
3. **Implement** `PersistenceUnitInfo` via `PersistenceUnitInfoImp`
4. **Pass** this and the map to `HibernatePersistenceProvider.createContainerEntityManagerFactory(...)`
5. **Obtain EntityManager**
6. **Perform operations (insert, read, etc.)**

---

## ✅ Benefits of this Approach

- No need for `persistence.xml`
- Easier to configure dynamically
- Good for standalone apps, testing, microservices
- All config in `.properties` + code

---
