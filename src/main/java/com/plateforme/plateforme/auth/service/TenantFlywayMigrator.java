package com.plateforme.plateforme.auth.service;

import com.plateforme.plateforme.auth.config.TenantProvisioningProperties;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantFlywayMigrator {

    private final TenantProvisioningProperties properties;

    public void migrate(String schemaName) {
        // On pointe toujours vers la base principale (plateforme_db)
        String jdbcUrl = String.format(
                "jdbc:postgresql://%s:%d/%s",
                properties.host(),
                properties.port(),
                properties.database() // Base centrale
        );

        Flyway flyway = Flyway.configure()
                .dataSource(
                        jdbcUrl,
                        properties.username(),
                        properties.password()
                )
                .schemas(schemaName) // <-- C'est ici qu'on indique à Flyway d'utiliser le schéma du client
                .locations("classpath:db/tenant-migration")
                .load();

        flyway.migrate();
    }
}