package com.svadhan.customeronboarding.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondEntityManagerFactoryBean",
        basePackages = {"com.svadhan.customeronboarding.bankingdatabase.repository"},
        transactionManagerRef = "secondTransactionManager"
)
public class BankingDatabaseConfig {

    @Autowired
    private Environment environment;


    @Primary
    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix = "spring.db2.datasource")
    public DataSource datasource() {
        return new DriverManagerDataSource();
    }

    @Primary
    @Bean(name = "secondEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(datasource());
        //Vendor Adaptor
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);

        bean.setPackagesToScan("com.svadhan.customeronboarding.bankingdatabase.entity");

        HashMap<String, Object> props = new HashMap<>();
        props.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        props.put("hibernate.show_sql", "true");
        bean.setJpaPropertyMap(props);
        return bean;
    }

    //Transaction manager
    @Bean(name = "secondTransactionManager")
//    @Primary
    public PlatformTransactionManager transactionManager(
            @Qualifier("secondEntityManagerFactoryBean") EntityManagerFactory entityManagerFactory
    ) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return new JpaTransactionManager(entityManagerFactory);
    }


}