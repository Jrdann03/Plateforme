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

        String requestUri = request.getRequestURI();
        String method = request.getMethod();
        String headerTenant = request.getHeader("X-Tenant-Subdomain");

        System.out.println(">>> [REQUÊTE] " + method + " " + requestUri + " | Header X-Tenant-Subdomain : " + headerTenant);

        String serverName = request.getServerName();
        String tenantSchema = resolveTenantFromHeaderOrDomain(request, serverName);

        System.out.println(">>> [DEBUG] tenantSchema calculé avant le if : " + tenantSchema);

        try {
            if (tenantSchema != null && !tenantSchema.isBlank()) {
                System.out.println(">>> [DEBUG] Passage dans le IF -> Application de : " + tenantSchema);
                TenantContext.setCurrentTenant(tenantSchema);
            } else {
                System.out.println(">>> [DEBUG] Passage dans le ELSE -> Application de : public");
                TenantContext.setCurrentTenant("public");
            }

            System.out.println(">>> [FILTER CHECK] Juste après setCurrentTenant, get() vaut : " + TenantContext.getCurrentTenant());

            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    private String resolveTenantFromHeaderOrDomain(HttpServletRequest request, String serverName) {
        String headerTenant = request.getHeader("X-Tenant-Subdomain");
        if (headerTenant != null && !headerTenant.isBlank()) {
            return "tenant_" + headerTenant.toLowerCase().replaceAll("-", "_");
        }

        String[] parts = serverName.split("\\.");
        if (parts.length > 2 || (parts.length == 2 && parts[1].equals("localhost"))) {
            String subDomain = parts[0];
            if (!subDomain.equalsIgnoreCase("www") && !subDomain.equalsIgnoreCase("api")) {
                return "tenant_" + subDomain.toLowerCase().replaceAll("-", "_");
            }
        }

        return "public";
    }
}