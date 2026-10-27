package com.plateforme.plateforme.site.controller;

import com.plateforme.plateforme.site.entity.Categorie;
import com.plateforme.plateforme.site.entity.Produit;
import com.plateforme.plateforme.site.service.CategorieService;
import com.plateforme.plateforme.site.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/site/catalogue")
@RequiredArgsConstructor
public class CatalogueController {

    private final CategorieService categorieService;
    private final ProduitService produitService;

    // --- CATÉGORIES ---

    @PostMapping("/categories")
    public ResponseEntity<Categorie> createCategorie(@RequestBody CategorieRequest request) {
        Categorie categorie = categorieService.create(request.nom(), request.description(), request.parentId());
        return ResponseEntity.status(HttpStatus.CREATED).body(categorie);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Categorie>> getCategories() {
        return ResponseEntity.ok(categorieService.getRootCategories());
    }

    // --- PRODUITS ---

    @PostMapping("/produits")
    public ResponseEntity<Produit> createProduit(@RequestBody ProduitRequest request) {
        Produit produit = produitService.create(
                request.nom(),
                request.description(),
                request.prix(),
                request.stock(),
                request.categorieId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(produit);
    }

    @GetMapping("/produits")
    public ResponseEntity<List<Produit>> getProduits() {
        return ResponseEntity.ok(produitService.getAll());
    }

    // Records DTO internes pour les requêtes
    public record CategorieRequest(String nom, String description, Long parentId) {}
    public record ProduitRequest(String nom, String description, BigDecimal prix, Integer stock, Long categorieId) {}
}