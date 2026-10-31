package com.plateforme.plateforme.auth.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TenantRoutingService {

    private final JdbcTemplate jdbcTemplate;

    public TenantRoutingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void applyCurrentTenantSchema() {
        String tenant = TenantContext.getCurrentTenant();
        if (tenant == null || tenant.isBlank()) {
            tenant = "public";
        }
        // On sécurise le nom du schéma pour éviter les injections SQL
        String safeTenant = tenant.replaceAll("[^a-zA-Z0-9_]", "");
        jdbcTemplate.execute("SET search_path TO \"" + safeTenant + "\";");
    }
}