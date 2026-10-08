package com.plateforme.plateforme.auth.dto;


import com.plateforme.plateforme.auth.entity.CommercantStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommercantResponse(
        Long id,
        UUID uid,
        String matricule,
        String nom,
        String prenom,
        String email,
        String telephone,
        String adresse,
        String sousDomaine,
        String databaseName,
        CommercantStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        SiteResponse site
) {

    public record SiteResponse(
            Long id,
            String nom,
            String titre,
            String description,
            String logoUrl,
            String faviconUrl,
            String theme,
            String couleurPrincipale,
            String couleurSecondaire
    ) {
    }
}