package com.spring.app.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.spring.app.core.repositories.AuthorityRepo;

@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})
@ComponentScan(basePackages = "com.spring")
public class JpaConfig {
	
	@Autowired
    private Environment environment;
	
	
 
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }
	
	 @Bean
	    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
	        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	        factoryBean.setDataSource(dataSource());
	        factoryBean.setPackagesToScan(new String[] { "com.spring.app.core.entities" });
	        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
	        factoryBean.setJpaProperties(jpaProperties());
	        return factoryBean;
	    }
	 
	 @Bean
	    public JpaVendorAdapter jpaVendorAdapter() {
	        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
	        return hibernateJpaVendorAdapter;
	    }
	 	
	 private Properties jpaProperties() {
	        Properties properties = new Properties();
	        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
	        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
	        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
	        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
	        return properties;
	    }
	 
	@Bean
	@Autowired
	 public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		 JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(entityManagerFactory);
	        return transactionManager;
	 }

}