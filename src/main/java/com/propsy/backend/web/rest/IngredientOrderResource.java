package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.IngredientOrder;
import com.propsy.backend.repository.IngredientOrderRepository;
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
 * REST controller for managing IngredientOrder.
 */
@RestController
@RequestMapping("/api")
public class IngredientOrderResource {

    private final Logger log = LoggerFactory.getLogger(IngredientOrderResource.class);

    private static final String ENTITY_NAME = "ingredientOrder";

    private IngredientOrderRepository ingredientOrderRepository;

    public IngredientOrderResource(IngredientOrderRepository ingredientOrderRepository) {
        this.ingredientOrderRepository = ingredientOrderRepository;
    }

    /**
     * POST  /ingredient-orders : Create a new ingredientOrder.
     *
     * @param ingredientOrder the ingredientOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ingredientOrder, or with status 400 (Bad Request) if the ingredientOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ingredient-orders")
    @Timed
    public ResponseEntity<IngredientOrder> createIngredientOrder(@RequestBody IngredientOrder ingredientOrder) throws URISyntaxException {
        log.debug("REST request to save IngredientOrder : {}", ingredientOrder);
        if (ingredientOrder.getId() != null) {
            throw new BadRequestAlertException("A new ingredientOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngredientOrder result = ingredientOrderRepository.save(ingredientOrder);
        return ResponseEntity.created(new URI("/api/ingredient-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ingredient-orders : Updates an existing ingredientOrder.
     *
     * @param ingredientOrder the ingredientOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ingredientOrder,
     * or with status 400 (Bad Request) if the ingredientOrder is not valid,
     * or with status 500 (Internal Server Error) if the ingredientOrder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ingredient-orders")
    @Timed
    public ResponseEntity<IngredientOrder> updateIngredientOrder(@RequestBody IngredientOrder ingredientOrder) throws URISyntaxException {
        log.debug("REST request to update IngredientOrder : {}", ingredientOrder);
        if (ingredientOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IngredientOrder result = ingredientOrderRepository.save(ingredientOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ingredientOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ingredient-orders : get all the ingredientOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ingredientOrders in body
     */
    @GetMapping("/ingredient-orders")
    @Timed
    public List<IngredientOrder> getAllIngredientOrders() {
        log.debug("REST request to get all IngredientOrders");
        return ingredientOrderRepository.findAll();
    }

    /**
     * GET  /ingredient-orders/:id : get the "id" ingredientOrder.
     *
     * @param id the id of the ingredientOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ingredientOrder, or with status 404 (Not Found)
     */
    @GetMapping("/ingredient-orders/{id}")
    @Timed
    public ResponseEntity<IngredientOrder> getIngredientOrder(@PathVariable Long id) {
        log.debug("REST request to get IngredientOrder : {}", id);
        Optional<IngredientOrder> ingredientOrder = ingredientOrderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ingredientOrder);
    }

    /**
     * DELETE  /ingredient-orders/:id : delete the "id" ingredientOrder.
     *
     * @param id the id of the ingredientOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ingredient-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteIngredientOrder(@PathVariable Long id) {
        log.debug("REST request to delete IngredientOrder : {}", id);

        ingredientOrderRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
