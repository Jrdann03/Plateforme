package com.plateforme.plateforme.auth.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TenantAwareDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // Cette méthode n'est plus strictement obligatoire si on intercepte getConnection,
        // mais elle est requise par AbstractRoutingDataSource. On peut retourner null
        // puisque nous allons forcer le search_path dynamiquement à chaque connexion.
        return null;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = super.getConnection();
        applyTenantSchema(connection);
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = super.getConnection(username, password);
        applyTenantSchema(connection);
        return connection;
    }

    private void applyTenantSchema(Connection connection) throws SQLException {
        String tenant = TenantContext.getCurrentTenant();

        if (tenant == null || tenant.isBlank() || !tenant.matches("[a-zA-Z0-9_]+")) {
            tenant = "public";
        }

        // On force le search_path de manière atomique sur cette connexion précise
        try (Statement statement = connection.createStatement()) {
            statement.execute("SET search_path TO \"" + tenant + "\";");
        } catch (SQLException e) {
            connection.close();
            throw new SQLException("Impossible d'appliquer le schéma du tenant : " + tenant, e);
        }
    }
}