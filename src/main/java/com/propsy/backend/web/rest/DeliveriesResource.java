package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.Deliveries;
import com.propsy.backend.repository.DeliveriesRepository;
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
 * REST controller for managing Deliveries.
 */
@RestController
@RequestMapping("/api")
public class DeliveriesResource {

    private final Logger log = LoggerFactory.getLogger(DeliveriesResource.class);

    private static final String ENTITY_NAME = "deliveries";

    private DeliveriesRepository deliveriesRepository;

    public DeliveriesResource(DeliveriesRepository deliveriesRepository) {
        this.deliveriesRepository = deliveriesRepository;
    }

    /**
     * POST  /deliveries : Create a new deliveries.
     *
     * @param deliveries the deliveries to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deliveries, or with status 400 (Bad Request) if the deliveries has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deliveries")
    @Timed
    public ResponseEntity<Deliveries> createDeliveries(@RequestBody Deliveries deliveries) throws URISyntaxException {
        log.debug("REST request to save Deliveries : {}", deliveries);
        if (deliveries.getId() != null) {
            throw new BadRequestAlertException("A new deliveries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Deliveries result = deliveriesRepository.save(deliveries);
        return ResponseEntity.created(new URI("/api/deliveries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deliveries : Updates an existing deliveries.
     *
     * @param deliveries the deliveries to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deliveries,
     * or with status 400 (Bad Request) if the deliveries is not valid,
     * or with status 500 (Internal Server Error) if the deliveries couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deliveries")
    @Timed
    public ResponseEntity<Deliveries> updateDeliveries(@RequestBody Deliveries deliveries) throws URISyntaxException {
        log.debug("REST request to update Deliveries : {}", deliveries);
        if (deliveries.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Deliveries result = deliveriesRepository.save(deliveries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deliveries.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deliveries : get all the deliveries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deliveries in body
     */
    @GetMapping("/deliveries")
    @Timed
    public List<Deliveries> getAllDeliveries() {
        log.debug("REST request to get all Deliveries");
        return deliveriesRepository.findAll();
    }

    /**
     * GET  /deliveries/:id : get the "id" deliveries.
     *
     * @param id the id of the deliveries to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deliveries, or with status 404 (Not Found)
     */
    @GetMapping("/deliveries/{id}")
    @Timed
    public ResponseEntity<Deliveries> getDeliveries(@PathVariable Long id) {
        log.debug("REST request to get Deliveries : {}", id);
        Optional<Deliveries> deliveries = deliveriesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deliveries);
    }

    /**
     * DELETE  /deliveries/:id : delete the "id" deliveries.
     *
     * @param id the id of the deliveries to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deliveries/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeliveries(@PathVariable Long id) {
        log.debug("REST request to delete Deliveries : {}", id);

        deliveriesRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
