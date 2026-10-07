package com.plateforme.plateforme.auth.service;


import com.plateforme.plateforme.auth.dto.CreateCommercantRequest;
import com.plateforme.plateforme.auth.entity.Commercant;
import com.plateforme.plateforme.auth.entity.CommercantStatus;
import com.plateforme.plateforme.auth.repository.CommercantRepository;
import com.plateforme.plateforme.site.entity.Site;
import com.plateforme.plateforme.site.repository.SiteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommercantService {

    private final CommercantRepository commercantRepository;
    private final SiteRepository siteRepository;

    @Transactional
    public Commercant create(CreateCommercantRequest request) {

        if (commercantRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException(
                    "Un commerçant existe déjà avec cet email."
            );
        }

        if (commercantRepository.existsBySousDomaine(request.sousDomaine())) {
            throw new IllegalArgumentException(
                    "Ce sous-domaine est déjà utilisé."
            );
        }

        UUID uid = UUID.randomUUID();

        String matricule = generateMatricule();

        String databaseName = generateDatabaseName(request.sousDomaine());

        Commercant commercant = Commercant.builder()
                .uid(uid)
                .matricule(matricule)
                .nom(request.nom())
                .prenom(request.prenom())
                .email(request.email())
                .telephone(request.telephone())
                .adresse(request.adresse())
                .sousDomaine(request.sousDomaine())
                .databaseName(databaseName)
                .status(CommercantStatus.ACTIVE)
                .build();

        commercantRepository.save(commercant);

        Site site = Site.builder()
                .commercant(commercant)
                .nom(request.nom())
                .titre("Bienvenue chez " + request.nom())
                .theme("DEFAULT")
                .build();

        siteRepository.save(site);

        commercant.setSite(site);

        return commercant;
    }

    private String generateMatricule() {
        return "COM-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }

    private String generateDatabaseName(String sousDomaine) {
        return "tenant_" + sousDomaine.toLowerCase();
    }
}