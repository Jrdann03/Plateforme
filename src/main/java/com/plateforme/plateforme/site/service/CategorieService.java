package com.plateforme.plateforme.site.service;

import com.plateforme.plateforme.site.entity.Categorie;
import com.plateforme.plateforme.site.repository.CategorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorieService {

    private final CategorieRepository categorieRepository;

    @Transactional
    public Categorie create(String nom, String description, Long parentId) {
        Categorie parent = null;
        if (parentId != null) {
            parent = categorieRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("Catégorie parente introuvable avec l'id : " + parentId));
        }

        Categorie categorie = Categorie.builder()
                .nom(nom)
                .description(description)
                .parent(parent)
                .build();

        return categorieRepository.save(categorie);
    }

    public List<Categorie> getRootCategories() {
        return categorieRepository.findByParentIsNull();
    }

    public List<Categorie> getAll() {
        return categorieRepository.findAll();
    }
}