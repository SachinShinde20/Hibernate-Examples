package com.main;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
public class PersistenceUnitInfoImp implements PersistenceUnitInfo {

	private final Properties props;

	public PersistenceUnitInfoImp(Properties props) {
		this.props = props;
	}

	@Override
	public String getPersistenceUnitName() {
		return "config";
	}

	@Override
	public String getPersistenceProviderClassName() {
		return "org.hibernate.jpa.HibernatePersistenceProvider";
	}

	@Override
	public List<String> getManagedClassNames() {
		return Arrays.asList("com.main.Employee");
	}

	@Override
	public PersistenceUnitTransactionType getTransactionType() {
		return PersistenceUnitTransactionType.RESOURCE_LOCAL;
	}

	@Override
	public Properties getProperties() {
		return props;
	}

	@Override
	public boolean excludeUnlistedClasses() {
		return false;
	}

	@Override
	public ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	// Optional methods
	@Override public List<URL> getJarFileUrls() { return null; }
	@Override public List<String> getMappingFileNames() { return null; }
	@Override public URL getPersistenceUnitRootUrl() { return null; }
	@Override public SharedCacheMode getSharedCacheMode() { return SharedCacheMode.UNSPECIFIED; }
	@Override public ValidationMode getValidationMode() { return ValidationMode.AUTO; }
	@Override public ClassLoader getNewTempClassLoader() { return null; }
	@Override public void addTransformer(ClassTransformer transformer) {}
	@Override public DataSource getJtaDataSource() { return null; }
	@Override public DataSource getNonJtaDataSource() { return null; }
	@Override public String getPersistenceXMLSchemaVersion() { return "2.2"; }
}
