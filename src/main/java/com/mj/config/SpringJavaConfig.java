package com.mj.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mj.util.SqlLoader;

@Configuration
@EnableWebMvc
@EnableJpaRepositories(basePackages="com.mj.model")
@ComponentScan(basePackages="com.mj")
@PropertySource(value="classpath:prop.properties",encoding="UTF-8")
public class SpringJavaConfig {
	
	@Value("${db.driver}")
	private String DB_DRIVER_CLASS_NAME;
	@Value("${db.url}")
	private String DB_URL;
	@Value("${db.user}")
	private String DB_USER;
	@Value("${db.password}")
	private String DB_PWD;
	
	/*
	 * Spring fake DataSource
	 */
//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource ds = new DriverManagerDataSource();
//		
//		ds.setDriverClassName(DB_DRIVER_CLASS_NAME);
//		ds.setUrl(DB_URL);
//		ds.setUsername(DB_USER);
//		ds.setPassword(DB_PWD);
//		
//		return ds;
//	} 
	
	/*
	 * Use JNDI to get Tomcat connection pool
	 */
	@Bean
	public DataSource dataSource() {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		
		bean.setJndiName("java:comp/env/jdbc/DB01");
		bean.setProxyInterface(DataSource.class);
		
		try {
			bean.afterPropertiesSet();
		} catch (IllegalArgumentException | NamingException e) {
			e.printStackTrace();
			return null;
		}
		
		return (DataSource) bean.getObject();
	} 
	
	/*
	 * Use Bean to set Tomcat connection pool directly
	 */
//	@Bean
//	public DataSource dataSource() {
//		org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
//		ds.setDriverClassName(DB_DRIVER_CLASS_NAME);
//		ds.setUrl(DB_URL);
//		ds.setUsername(DB_USER);
//		ds.setPassword(DB_PWD);
//		
//		return ds;
//	} 
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	
	  HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	  vendorAdapter.setGenerateDdl(true);
	  vendorAdapter.setShowSql(true);
	  vendorAdapter.setDatabase(Database.MYSQL);
	    
	  LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	  factory.setJpaVendorAdapter(vendorAdapter);
	  factory.setPackagesToScan("com.mj.model");
	  factory.setDataSource(dataSource());
	  factory.setMappingResources("com/mj/model/mapper/JsonTable.xml");
	    
	  return factory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
	  JpaTransactionManager txManager = new JpaTransactionManager();
	  txManager.setEntityManagerFactory(entityManagerFactory().getObject());
	  return txManager;
	}
}
