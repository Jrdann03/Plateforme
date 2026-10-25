package com.plateforme.plateforme.auth.service;

import com.plateforme.plateforme.auth.config.TenantProvisioningProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
@RequiredArgsConstructor
public class TenantSchemaProvisioner {

    private final TenantProvisioningProperties properties;

    public void createSchema(String schemaName) {
        validateSchemaName(schemaName);

        // On se connecte DIRECTEMENT à la base principale (plateforme_db)
        String jdbcUrl = String.format(
                "jdbc:postgresql://%s:%d/%s",
                properties.host(),
                properties.port(),
                properties.database() // C'est la base centrale ici
        );

        try (Connection connection = DriverManager.getConnection(
                jdbcUrl,
                properties.username(),
                properties.password()
        )) {

            if (schemaExists(connection, schemaName)) {
                throw new IllegalStateException(
                        "Le schéma '" + schemaName + "' existe déjà."
                );
            }

            try (Statement statement = connection.createStatement()) {
                // Création du schéma PostgreSQL
                String sql = "CREATE SCHEMA \"" + schemaName + "\"";
                statement.executeUpdate(sql);
            }

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Impossible de créer le schéma '" + schemaName + "'.",
                    e
            );
        }
    }

    private boolean schemaExists(Connection connection, String schemaName) throws Exception {
        String sql = """
                SELECT 1
                FROM information_schema.schemata
                WHERE schema_name = ?
                """;

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, schemaName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void validateSchemaName(String schemaName) {
        if (schemaName == null || schemaName.isBlank()) {
            throw new IllegalArgumentException("Le nom du schéma est obligatoire.");
        }
        if (!schemaName.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException("Nom de schéma invalide.");
        }
        if (schemaName.length() > 63) {
            throw new IllegalArgumentException("Le nom du schéma ne peut pas dépasser 63 caractères.");
        }
    }
}