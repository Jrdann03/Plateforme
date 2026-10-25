package com.plateforme.plateforme.auth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String serverName = request.getServerName(); // ex: boutique1.localhost ou boutique1.monsite.com
        String tenantSchema = resolveTenantFromHeaderOrDomain(request, serverName);

        try {
            if (tenantSchema != null && !tenantSchema.isBlank()) {
                TenantContext.setCurrentTenant(tenantSchema);
            } else {
                TenantContext.setCurrentTenant("public"); // Schéma par défaut pour la plateforme centrale
            }
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear(); // Très important pour éviter les fuites de mémoire entre threads
        }
    }

    private String resolveTenantFromHeaderOrDomain(HttpServletRequest request, String serverName) {
        // Option 1 : Permettre de tester via un Header HTTP (très pratique en dev/Postman avec "X-Tenant-Subdomain")
        String headerTenant = request.getHeader("X-Tenant-Subdomain");
        if (headerTenant != null && !headerTenant.isBlank()) {
            return "tenant_" + headerTenant.toLowerCase().replaceAll("-", "_");
        }

        // Option 2 : Extraire du sous-domaine (ex: boutique1.localhost -> sous-domaine = boutique1)
        String[] parts = serverName.split("\\.");
        if (parts.length > 2 || (parts.length == 2 && parts[1].equals("localhost"))) {
            String subDomain = parts[0];
            // Ignore les sous-domaines techniques de base si besoin (ex: www, api)
            if (!subDomain.equalsIgnoreCase("www") && !subDomain.equalsIgnoreCase("api")) {
                return "tenant_" + subDomain.toLowerCase().replaceAll("-", "_");
            }
        }

        return "public";
    }
}