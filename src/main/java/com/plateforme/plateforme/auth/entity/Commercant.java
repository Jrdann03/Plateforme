package com.plateforme.plateforme.auth.entity;

import com.plateforme.plateforme.site.entity.Site;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "commercant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commercant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uid;

    @Column(nullable = false, unique = true, length = 50)
    private String matricule;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(length = 30)
    private String telephone;

    @Column(length = 255)
    private String adresse;

    @Column(name = "sous_domaine", nullable = false, unique = true, length = 100)
    private String sousDomaine;

    @Column(name = "database_name", nullable = false, unique = true, length = 100)
    private String databaseName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CommercantStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "commercant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Site site;

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