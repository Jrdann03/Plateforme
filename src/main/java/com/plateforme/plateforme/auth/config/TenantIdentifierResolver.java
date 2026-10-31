package com.plateforme.plateforme.auth.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getCurrentTenant();
        String currentThread = Thread.currentThread().getName();

        System.out.println(">>> [RESOLVER] Thread: " + currentThread + " | Tenant lu depuis TenantContext : " + tenant);

        if (tenant != null && !tenant.isBlank()) {
            return tenant;
        }
        return "public";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}