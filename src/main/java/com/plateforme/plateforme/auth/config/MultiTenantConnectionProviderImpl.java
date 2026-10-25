package com.plateforme.plateforme.auth.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider<String> {

    private final DataSource dataSource;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        Connection connection = getAnyConnection();
        try {
            if (tenantIdentifier == null || !tenantIdentifier.matches("[a-zA-Z0-9_]+")) {
                tenantIdentifier = "public";
            }
            connection.createStatement().execute("SET search_path TO \"" + tenantIdentifier + "\";");
        } catch (SQLException e) {
            connection.close();
            throw new SQLException("Impossible de basculer vers le schéma du tenant : " + tenantIdentifier, e);
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try {
            connection.createStatement().execute("SET search_path TO public;");
        } catch (SQLException e) {
            // Ignorer ou logger
        }
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) { // <-- Ajout de 'public' ici
        return null;
    }
}