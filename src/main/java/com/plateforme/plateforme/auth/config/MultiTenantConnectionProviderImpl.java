package com.plateforme.plateforme.auth.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider<String> {

    @Autowired
    private DataSource dataSource;

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
            if (tenantIdentifier == null || tenantIdentifier.isBlank() || !tenantIdentifier.matches("[a-zA-Z0-9_]+")) {
                tenantIdentifier = "public";
            }
            // On force le search_path sur la connexion active
            connection.createStatement().execute("SET search_path TO \"" + tenantIdentifier + "\";");
        } catch (SQLException e) {
            connection.close();
            throw new SQLException("Impossible de basculer vers le schéma : " + tenantIdentifier, e);
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try {
            // Remet le search_path à public avant de rendre la connexion au pool par sécurité
            connection.createStatement().execute("SET search_path TO public;");
        } catch (SQLException e) {
            // Log si nécessaire
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
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }
}