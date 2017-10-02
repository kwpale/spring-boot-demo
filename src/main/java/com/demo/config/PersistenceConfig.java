package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.support.MergingPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackageClasses = {com.demo.repo.PackageMarker.class})
public class PersistenceConfig {

    @Bean
    public PersistenceUnitManager persistenceUnitManager(DataSource dataSource){
        MergingPersistenceUnitManager persistenceUnitManager = new MergingPersistenceUnitManager();
        persistenceUnitManager.setPackagesToScan("com.demo.entity");
        persistenceUnitManager.setDefaultDataSource(dataSource);
        return persistenceUnitManager;
    }
}
