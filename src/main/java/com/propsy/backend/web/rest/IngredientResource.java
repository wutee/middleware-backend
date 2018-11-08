package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.Ingredient;
import com.propsy.backend.repository.IngredientRepository;
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
 * REST controller for managing Ingredient.
 */
@RestController
@RequestMapping("/api")
public class IngredientResource {

    private final Logger log = LoggerFactory.getLogger(IngredientResource.class);

    private static final String ENTITY_NAME = "ingredient";

    private IngredientRepository ingredientRepository;

    public IngredientResource(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * POST  /ingredients : Create a new ingredient.
     *
     * @param ingredient the ingredient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ingredient, or with status 400 (Bad Request) if the ingredient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ingredients")
    @Timed
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) throws URISyntaxException {
        log.debug("REST request to save Ingredient : {}", ingredient);
        if (ingredient.getId() != null) {
            throw new BadRequestAlertException("A new ingredient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ingredient result = ingredientRepository.save(ingredient);
        return ResponseEntity.created(new URI("/api/ingredients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ingredients : Updates an existing ingredient.
     *
     * @param ingredient the ingredient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ingredient,
     * or with status 400 (Bad Request) if the ingredient is not valid,
     * or with status 500 (Internal Server Error) if the ingredient couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ingredients")
    @Timed
    public ResponseEntity<Ingredient> updateIngredient(@RequestBody Ingredient ingredient) throws URISyntaxException {
        log.debug("REST request to update Ingredient : {}", ingredient);
        if (ingredient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ingredient result = ingredientRepository.save(ingredient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ingredient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ingredients : get all the ingredients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ingredients in body
     */
    @GetMapping("/ingredients")
    @Timed
    public List<Ingredient> getAllIngredients() {
        log.debug("REST request to get all Ingredients");
        return ingredientRepository.findAll();
    }

    /**
     * GET  /ingredients/:id : get the "id" ingredient.
     *
     * @param id the id of the ingredient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ingredient, or with status 404 (Not Found)
     */
    @GetMapping("/ingredients/{id}")
    @Timed
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Long id) {
        log.debug("REST request to get Ingredient : {}", id);
        Optional<Ingredient> ingredient = ingredientRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ingredient);
    }

    /**
     * DELETE  /ingredients/:id : delete the "id" ingredient.
     *
     * @param id the id of the ingredient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ingredients/{id}")
    @Timed
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        log.debug("REST request to delete Ingredient : {}", id);

        ingredientRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
