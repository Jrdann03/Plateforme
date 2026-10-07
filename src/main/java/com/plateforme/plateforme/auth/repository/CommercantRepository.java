package com.plateforme.plateforme.auth.repository;

import com.plateforme.plateforme.auth.entity.Commercant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommercantRepository extends JpaRepository<Commercant, Long> {

    boolean existsByEmail(String email);

    boolean existsBySousDomaine(String sousDomaine);

    boolean existsByUid(UUID uid);

    boolean existsByMatricule(String matricule);

    Optional<Commercant> findByUid(UUID uid);

    Optional<Commercant> findBySousDomaine(String sousDomaine);
}