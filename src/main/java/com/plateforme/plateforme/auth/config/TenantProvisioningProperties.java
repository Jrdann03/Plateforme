package com.plateforme.plateforme.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tenant.provisioning")
public record TenantProvisioningProperties(
        String host,
        int port,
        String database,
        String username,
        String password
) {
}