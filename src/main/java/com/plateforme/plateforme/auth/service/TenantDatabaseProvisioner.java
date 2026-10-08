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
public class TenantDatabaseProvisioner {

    private final TenantProvisioningProperties properties;

    public void createDatabase(String databaseName) {

        validateDatabaseName(databaseName);

        String jdbcUrl = String.format(
                "jdbc:postgresql://%s:%d/%s",
                properties.host(),
                properties.port(),
                properties.database()
        );

        try (Connection connection = DriverManager.getConnection(
                jdbcUrl,
                properties.username(),
                properties.password()
        )) {

            if (databaseExists(connection, databaseName)) {
                throw new IllegalStateException(
                        "La base de données '" + databaseName + "' existe déjà."
                );
            }

            try (Statement statement = connection.createStatement()) {

                String sql = "CREATE DATABASE \"" + databaseName + "\" TEMPLATE template0";

                statement.executeUpdate(sql);
            }

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Impossible de créer la base de données '" + databaseName + "'.",
                    e
            );
        }
    }

    private boolean databaseExists(
            Connection connection,
            String databaseName
    ) throws Exception {

        String sql = """
                SELECT 1
                FROM pg_database
                WHERE datname = ?
                """;

        try (var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, databaseName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void validateDatabaseName(String databaseName) {

        if (databaseName == null || databaseName.isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom de la base de données est obligatoire."
            );
        }

        if (!databaseName.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException(
                    "Nom de base de données invalide."
            );
        }

        if (databaseName.length() > 63) {
            throw new IllegalArgumentException(
                    "Le nom de la base de données ne peut pas dépasser 63 caractères."
            );
        }
    }
}