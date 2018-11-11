package com.propsy.backend.repository;

import com.propsy.backend.domain.Translation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Translation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {

}
