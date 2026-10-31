package com.plateforme.plateforme.auth.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Collections;

@Configuration
public class TenantDataSourceConfiguration {

    // On utilise ObjectProvider pour éviter le blocage de cycle au démarrage
    @Primary
    @Bean
    public DataSource dataSource(ObjectProvider<DataSource> physicalDataSourceProvider) {
        // Le DataSource physique (HikariCP) ne sera récupéré qu'au moment exact où l'on en a besoin
        DataSource physicalDataSource = physicalDataSourceProvider.getIfAvailable();

        TenantAwareDataSource routingDataSource = new TenantAwareDataSource();
        routingDataSource.setTargetDataSources(Collections.emptyMap());
        routingDataSource.setDefaultTargetDataSource(physicalDataSource);
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }
}