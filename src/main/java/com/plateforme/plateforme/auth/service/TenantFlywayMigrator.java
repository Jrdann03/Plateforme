package com.plateforme.plateforme.auth.service;

import com.plateforme.plateforme.auth.config.TenantProvisioningProperties;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantFlywayMigrator {

    private final TenantProvisioningProperties properties;

    public void migrate(String databaseName) {

        String jdbcUrl = String.format(
                "jdbc:postgresql://%s:%d/%s",
                properties.host(),
                properties.port(),
                databaseName
        );

        Flyway flyway = Flyway.configure()
                .dataSource(
                        jdbcUrl,
                        properties.username(),
                        properties.password()
                )
                .locations("classpath:db/tenant-migration")
                .load();

        flyway.migrate();
    }
}