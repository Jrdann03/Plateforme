package com.plateforme.plateforme.site.repository;


import com.plateforme.plateforme.site.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    // Permet de récupérer toutes les catégories racines (qui n'ont pas de parent)
    List<Categorie> findByParentIsNull();
}