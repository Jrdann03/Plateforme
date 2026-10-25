package com.plateforme.plateforme.auth.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getCurrentTenant();
        return (tenant != null) ? tenant : "public";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}