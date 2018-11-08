package com.propsy.backend.repository;

import com.propsy.backend.domain.Translations;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Translations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranslationsRepository extends JpaRepository<Translations, Long> {

}
