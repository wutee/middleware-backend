package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.IngredientsToOrder;
import com.propsy.backend.repository.IngredientsToOrderRepository;
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
 * REST controller for managing IngredientsToOrder.
 */
@RestController
@RequestMapping("/api")
public class IngredientsToOrderResource {

    private final Logger log = LoggerFactory.getLogger(IngredientsToOrderResource.class);

    private static final String ENTITY_NAME = "ingredientsToOrder";

    private IngredientsToOrderRepository ingredientsToOrderRepository;

    public IngredientsToOrderResource(IngredientsToOrderRepository ingredientsToOrderRepository) {
        this.ingredientsToOrderRepository = ingredientsToOrderRepository;
    }

    /**
     * POST  /ingredients-to-orders : Create a new ingredientsToOrder.
     *
     * @param ingredientsToOrder the ingredientsToOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ingredientsToOrder, or with status 400 (Bad Request) if the ingredientsToOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ingredients-to-orders")
    @Timed
    public ResponseEntity<IngredientsToOrder> createIngredientsToOrder(@RequestBody IngredientsToOrder ingredientsToOrder) throws URISyntaxException {
        log.debug("REST request to save IngredientsToOrder : {}", ingredientsToOrder);
        if (ingredientsToOrder.getId() != null) {
            throw new BadRequestAlertException("A new ingredientsToOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngredientsToOrder result = ingredientsToOrderRepository.save(ingredientsToOrder);
        return ResponseEntity.created(new URI("/api/ingredients-to-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ingredients-to-orders : Updates an existing ingredientsToOrder.
     *
     * @param ingredientsToOrder the ingredientsToOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ingredientsToOrder,
     * or with status 400 (Bad Request) if the ingredientsToOrder is not valid,
     * or with status 500 (Internal Server Error) if the ingredientsToOrder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ingredients-to-orders")
    @Timed
    public ResponseEntity<IngredientsToOrder> updateIngredientsToOrder(@RequestBody IngredientsToOrder ingredientsToOrder) throws URISyntaxException {
        log.debug("REST request to update IngredientsToOrder : {}", ingredientsToOrder);
        if (ingredientsToOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IngredientsToOrder result = ingredientsToOrderRepository.save(ingredientsToOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ingredientsToOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ingredients-to-orders : get all the ingredientsToOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ingredientsToOrders in body
     */
    @GetMapping("/ingredients-to-orders")
    @Timed
    public List<IngredientsToOrder> getAllIngredientsToOrders() {
        log.debug("REST request to get all IngredientsToOrders");
        return ingredientsToOrderRepository.findAll();
    }

    /**
     * GET  /ingredients-to-orders/:id : get the "id" ingredientsToOrder.
     *
     * @param id the id of the ingredientsToOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ingredientsToOrder, or with status 404 (Not Found)
     */
    @GetMapping("/ingredients-to-orders/{id}")
    @Timed
    public ResponseEntity<IngredientsToOrder> getIngredientsToOrder(@PathVariable Long id) {
        log.debug("REST request to get IngredientsToOrder : {}", id);
        Optional<IngredientsToOrder> ingredientsToOrder = ingredientsToOrderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ingredientsToOrder);
    }

    /**
     * DELETE  /ingredients-to-orders/:id : delete the "id" ingredientsToOrder.
     *
     * @param id the id of the ingredientsToOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ingredients-to-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteIngredientsToOrder(@PathVariable Long id) {
        log.debug("REST request to delete IngredientsToOrder : {}", id);

        ingredientsToOrderRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
