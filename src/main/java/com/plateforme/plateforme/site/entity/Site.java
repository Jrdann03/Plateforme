package com.plateforme.plateforme.site.entity;


import com.plateforme.plateforme.auth.entity.Commercant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "site")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "commercant_id",
            nullable = false,
            unique = true
    )
    private Commercant commercant;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(length = 255)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "favicon_url", length = 500)
    private String faviconUrl;

    @Column(nullable = false, length = 50)
    private String theme;

    @Column(name = "couleur_principale", length = 20)
    private String couleurPrincipale;

    @Column(name = "couleur_secondaire", length = 20)
    private String couleurSecondaire;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
