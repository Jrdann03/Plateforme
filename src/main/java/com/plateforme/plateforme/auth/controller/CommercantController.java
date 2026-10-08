package com.plateforme.plateforme.auth.controller;


import com.plateforme.plateforme.auth.dto.CommercantResponse;
import com.plateforme.plateforme.auth.dto.CreateCommercantRequest;
import com.plateforme.plateforme.auth.entity.Commercant;
import com.plateforme.plateforme.auth.service.CommercantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/commercants")
@RequiredArgsConstructor
public class CommercantController {

    private final CommercantService commercantService;

    @GetMapping("/h")
    public String hello() {
        return "hello depuis springboot";
    }

    @PostMapping
    public ResponseEntity<CommercantResponse> create(
            @Valid @RequestBody CreateCommercantRequest request
    ) {
        Commercant commercant = commercantService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(commercant));
    }

    private CommercantResponse toResponse(Commercant commercant) {

        CommercantResponse.SiteResponse siteResponse = null;

        if (commercant.getSite() != null) {
            var site = commercant.getSite();

            siteResponse = new CommercantResponse.SiteResponse(
                    site.getId(),
                    site.getNom(),
                    site.getTitre(),
                    site.getDescription(),
                    site.getLogoUrl(),
                    site.getFaviconUrl(),
                    site.getTheme(),
                    site.getCouleurPrincipale(),
                    site.getCouleurSecondaire()
            );
        }

        return new CommercantResponse(
                commercant.getId(),
                commercant.getUid(),
                commercant.getMatricule(),
                commercant.getNom(),
                commercant.getPrenom(),
                commercant.getEmail(),
                commercant.getTelephone(),
                commercant.getAdresse(),
                commercant.getSousDomaine(),
                commercant.getDatabaseName(),
                commercant.getStatus(),
                commercant.getCreatedAt(),
                commercant.getUpdatedAt(),
                siteResponse
        );
    }
}
