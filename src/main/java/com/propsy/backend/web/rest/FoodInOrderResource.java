package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.FoodInOrder;
import com.propsy.backend.repository.FoodInOrderRepository;
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
 * REST controller for managing FoodInOrder.
 */
@RestController
@RequestMapping("/api")
public class FoodInOrderResource {

    private final Logger log = LoggerFactory.getLogger(FoodInOrderResource.class);

    private static final String ENTITY_NAME = "foodInOrder";

    private FoodInOrderRepository foodInOrderRepository;

    public FoodInOrderResource(FoodInOrderRepository foodInOrderRepository) {
        this.foodInOrderRepository = foodInOrderRepository;
    }

    /**
     * POST  /food-in-orders : Create a new foodInOrder.
     *
     * @param foodInOrder the foodInOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new foodInOrder, or with status 400 (Bad Request) if the foodInOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/food-in-orders")
    @Timed
    public ResponseEntity<FoodInOrder> createFoodInOrder(@RequestBody FoodInOrder foodInOrder) throws URISyntaxException {
        log.debug("REST request to save FoodInOrder : {}", foodInOrder);
        if (foodInOrder.getId() != null) {
            throw new BadRequestAlertException("A new foodInOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodInOrder result = foodInOrderRepository.save(foodInOrder);
        return ResponseEntity.created(new URI("/api/food-in-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /food-in-orders : Updates an existing foodInOrder.
     *
     * @param foodInOrder the foodInOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated foodInOrder,
     * or with status 400 (Bad Request) if the foodInOrder is not valid,
     * or with status 500 (Internal Server Error) if the foodInOrder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/food-in-orders")
    @Timed
    public ResponseEntity<FoodInOrder> updateFoodInOrder(@RequestBody FoodInOrder foodInOrder) throws URISyntaxException {
        log.debug("REST request to update FoodInOrder : {}", foodInOrder);
        if (foodInOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoodInOrder result = foodInOrderRepository.save(foodInOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, foodInOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /food-in-orders : get all the foodInOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of foodInOrders in body
     */
    @GetMapping("/food-in-orders")
    @Timed
    public List<FoodInOrder> getAllFoodInOrders() {
        log.debug("REST request to get all FoodInOrders");
        return foodInOrderRepository.findAll();
    }

    /**
     * GET  /food-in-orders/:id : get the "id" foodInOrder.
     *
     * @param id the id of the foodInOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the foodInOrder, or with status 404 (Not Found)
     */
    @GetMapping("/food-in-orders/{id}")
    @Timed
    public ResponseEntity<FoodInOrder> getFoodInOrder(@PathVariable Long id) {
        log.debug("REST request to get FoodInOrder : {}", id);
        Optional<FoodInOrder> foodInOrder = foodInOrderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(foodInOrder);
    }

    /**
     * DELETE  /food-in-orders/:id : delete the "id" foodInOrder.
     *
     * @param id the id of the foodInOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/food-in-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteFoodInOrder(@PathVariable Long id) {
        log.debug("REST request to delete FoodInOrder : {}", id);

        foodInOrderRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
