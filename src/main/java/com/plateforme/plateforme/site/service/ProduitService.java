package com.plateforme.plateforme.site.service;

import com.plateforme.plateforme.site.entity.Categorie;
import com.plateforme.plateforme.site.entity.Produit;
import com.plateforme.plateforme.site.repository.CategorieRepository;
import com.plateforme.plateforme.site.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;

    @Transactional
    public Produit create(String nom, String description, BigDecimal prix, Integer stock, Long categorieId) {
        Categorie categorie = null;
        if (categorieId != null) {
            categorie = categorieRepository.findById(categorieId)
                    .orElseThrow(() -> new IllegalArgumentException("Catégorie introuvable avec l'id : " + categorieId));
        }

        Produit produit = Produit.builder()
                .nom(nom)
                .description(description)
                .prix(prix)
                .stock(stock != null ? stock : 0)
                .categorie(categorie)
                .build();

        return produitRepository.save(produit);
    }

    public List<Produit> getAll() {
        return produitRepository.findAll();
    }
}