package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.Translations;
import com.propsy.backend.repository.TranslationsRepository;
import com.propsy.backend.web.rest.errors.BadRequestAlertException;
import com.propsy.backend.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Translations.
 */
@RestController
@RequestMapping("/api")
public class TranslationsResource {

    private final Logger log = LoggerFactory.getLogger(TranslationsResource.class);

    private static final String ENTITY_NAME = "translations";

    private TranslationsRepository translationsRepository;

    public TranslationsResource(TranslationsRepository translationsRepository) {
        this.translationsRepository = translationsRepository;
    }

    /**
     * POST  /translations : Create a new translations.
     *
     * @param translations the translations to create
     * @return the ResponseEntity with status 201 (Created) and with body the new translations, or with status 400 (Bad Request) if the translations has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/translations")
    @Timed
    public ResponseEntity<Translations> createTranslations(@RequestBody Translations translations) throws URISyntaxException {
        log.debug("REST request to save Translations : {}", translations);
        if (translations.getId() != null) {
            throw new BadRequestAlertException("A new translations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Translations result = translationsRepository.save(translations);
        return ResponseEntity.created(new URI("/api/translations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /translations : Updates an existing translations.
     *
     * @param translations the translations to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated translations,
     * or with status 400 (Bad Request) if the translations is not valid,
     * or with status 500 (Internal Server Error) if the translations couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/translations")
    @Timed
    public ResponseEntity<Translations> updateTranslations(@RequestBody Translations translations) throws URISyntaxException {
        log.debug("REST request to update Translations : {}", translations);
        if (translations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Translations result = translationsRepository.save(translations);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, translations.getId().toString()))
            .body(result);
    }

    /**
     * GET  /translations : get all the translations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of translations in body
     */
    @GetMapping("/translations")
    @Timed
    public List<Translations> getAllTranslations() {
        log.debug("REST request to get all Translations");
        return translationsRepository.findAll();
    }

    /**
     * GET  /translations/:id : get the "id" translations.
     *
     * @param id the id of the translations to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the translations, or with status 404 (Not Found)
     */
    @GetMapping("/translations/{id}")
    @Timed
    public ResponseEntity<Translations> getTranslations(@PathVariable Long id) {
        log.debug("REST request to get Translations : {}", id);
        Optional<Translations> translations = translationsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(translations);
    }

    /**
     * DELETE  /translations/:id : delete the "id" translations.
     *
     * @param id the id of the translations to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/translations/{id}")
    @Timed
    public ResponseEntity<Void> deleteTranslations(@PathVariable Long id) {
        log.debug("REST request to delete Translations : {}", id);

        translationsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
