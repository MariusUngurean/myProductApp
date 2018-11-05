package com.mungurean.productApp.module;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
//@PropertySource({"classpath:/resources/persistence.properties"})
@ComponentScan({"com.mungurean.productApp.module.model"})
public class PersistenceConfig implements TransactionManagementConfigurer {

    @Autowired
    private Environment env;

    public PersistenceConfig() {
        super();
    }

    @Bean
    public DAOImpl dao() {
        return new DAOImpl();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(restDataSource());
        em.setPackagesToScan("com.mungurean.productApp.module");
        em.setPersistenceUnitName("projectDatabase");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        // em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Bean
    public DataSource restDataSource() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres?currentSchema=public");
        dataSource.setUsername("postgres");
        dataSource.setPassword("123456");
//        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("jdbc.driverClassName")));
//        dataSource.setUrl(Objects.requireNonNull(env.getProperty("jdbc.url")));
//        dataSource.setUsername(Objects.requireNonNull(env.getProperty("jdbc.user")));
//        dataSource.setPassword(Objects.requireNonNull(env.getProperty("jdbc.pass")));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        transactionManager.setPersistenceUnitName("projectDatabase");
        transactionManager.setDataSource(restDataSource());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }

//    final Properties hibernateProperties() {
//        final Properties hibernateProperties = new Properties();
//        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//        hibernateProperties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
//        return hibernateProperties;
//    }

}
