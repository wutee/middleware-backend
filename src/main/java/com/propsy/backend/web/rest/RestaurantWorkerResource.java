package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.RestaurantWorker;
import com.propsy.backend.repository.RestaurantWorkerRepository;
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
 * REST controller for managing RestaurantWorker.
 */
@RestController
@RequestMapping("/api")
public class RestaurantWorkerResource {

    private final Logger log = LoggerFactory.getLogger(RestaurantWorkerResource.class);

    private static final String ENTITY_NAME = "restaurantWorker";

    private RestaurantWorkerRepository restaurantWorkerRepository;

    public RestaurantWorkerResource(RestaurantWorkerRepository restaurantWorkerRepository) {
        this.restaurantWorkerRepository = restaurantWorkerRepository;
    }

    /**
     * POST  /restaurant-workers : Create a new restaurantWorker.
     *
     * @param restaurantWorker the restaurantWorker to create
     * @return the ResponseEntity with status 201 (Created) and with body the new restaurantWorker, or with status 400 (Bad Request) if the restaurantWorker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/restaurant-workers")
    @Timed
    public ResponseEntity<RestaurantWorker> createRestaurantWorker(@RequestBody RestaurantWorker restaurantWorker) throws URISyntaxException {
        log.debug("REST request to save RestaurantWorker : {}", restaurantWorker);
        if (restaurantWorker.getId() != null) {
            throw new BadRequestAlertException("A new restaurantWorker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RestaurantWorker result = restaurantWorkerRepository.save(restaurantWorker);
        return ResponseEntity.created(new URI("/api/restaurant-workers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /restaurant-workers : Updates an existing restaurantWorker.
     *
     * @param restaurantWorker the restaurantWorker to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated restaurantWorker,
     * or with status 400 (Bad Request) if the restaurantWorker is not valid,
     * or with status 500 (Internal Server Error) if the restaurantWorker couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/restaurant-workers")
    @Timed
    public ResponseEntity<RestaurantWorker> updateRestaurantWorker(@RequestBody RestaurantWorker restaurantWorker) throws URISyntaxException {
        log.debug("REST request to update RestaurantWorker : {}", restaurantWorker);
        if (restaurantWorker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RestaurantWorker result = restaurantWorkerRepository.save(restaurantWorker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, restaurantWorker.getId().toString()))
            .body(result);
    }

    /**
     * GET  /restaurant-workers : get all the restaurantWorkers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of restaurantWorkers in body
     */
    @GetMapping("/restaurant-workers")
    @Timed
    public List<RestaurantWorker> getAllRestaurantWorkers() {
        log.debug("REST request to get all RestaurantWorkers");
        return restaurantWorkerRepository.findAll();
    }

    /**
     * GET  /restaurant-workers/:id : get the "id" restaurantWorker.
     *
     * @param id the id of the restaurantWorker to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the restaurantWorker, or with status 404 (Not Found)
     */
    @GetMapping("/restaurant-workers/{id}")
    @Timed
    public ResponseEntity<RestaurantWorker> getRestaurantWorker(@PathVariable Long id) {
        log.debug("REST request to get RestaurantWorker : {}", id);
        Optional<RestaurantWorker> restaurantWorker = restaurantWorkerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(restaurantWorker);
    }

    /**
     * DELETE  /restaurant-workers/:id : delete the "id" restaurantWorker.
     *
     * @param id the id of the restaurantWorker to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/restaurant-workers/{id}")
    @Timed
    public ResponseEntity<Void> deleteRestaurantWorker(@PathVariable Long id) {
        log.debug("REST request to delete RestaurantWorker : {}", id);

        restaurantWorkerRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
