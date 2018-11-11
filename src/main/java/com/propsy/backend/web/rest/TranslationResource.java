package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.Translation;
import com.propsy.backend.repository.TranslationRepository;
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
 * REST controller for managing Translation.
 */
@RestController
@RequestMapping("/api")
public class TranslationResource {

    private final Logger log = LoggerFactory.getLogger(TranslationResource.class);

    private static final String ENTITY_NAME = "translation";

    private TranslationRepository translationRepository;

    public TranslationResource(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    /**
     * POST  /translations : Create a new translation.
     *
     * @param translation the translation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new translation, or with status 400 (Bad Request) if the translation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/translations")
    @Timed
    public ResponseEntity<Translation> createTranslation(@RequestBody Translation translation) throws URISyntaxException {
        log.debug("REST request to save Translation : {}", translation);
        if (translation.getId() != null) {
            throw new BadRequestAlertException("A new translation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Translation result = translationRepository.save(translation);
        return ResponseEntity.created(new URI("/api/translations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /translations : Updates an existing translation.
     *
     * @param translation the translation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated translation,
     * or with status 400 (Bad Request) if the translation is not valid,
     * or with status 500 (Internal Server Error) if the translation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/translations")
    @Timed
    public ResponseEntity<Translation> updateTranslation(@RequestBody Translation translation) throws URISyntaxException {
        log.debug("REST request to update Translation : {}", translation);
        if (translation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Translation result = translationRepository.save(translation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, translation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /translations : get all the translations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of translations in body
     */
    @GetMapping("/translations")
    @Timed
    public List<Translation> getAllTranslations() {
        log.debug("REST request to get all Translations");
        return translationRepository.findAll();
    }

    /**
     * GET  /translations/:id : get the "id" translation.
     *
     * @param id the id of the translation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the translation, or with status 404 (Not Found)
     */
    @GetMapping("/translations/{id}")
    @Timed
    public ResponseEntity<Translation> getTranslation(@PathVariable Long id) {
        log.debug("REST request to get Translation : {}", id);
        Optional<Translation> translation = translationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(translation);
    }

    /**
     * DELETE  /translations/:id : delete the "id" translation.
     *
     * @param id the id of the translation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/translations/{id}")
    @Timed
    public ResponseEntity<Void> deleteTranslation(@PathVariable Long id) {
        log.debug("REST request to delete Translation : {}", id);

        translationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
