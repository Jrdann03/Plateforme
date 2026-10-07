package com.plateforme.plateforme.site.repository;


import com.plateforme.plateforme.site.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

    Optional<Site> findByCommercantId(Long commercantId);
}