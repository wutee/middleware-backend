package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.DeliveryMan;
import com.propsy.backend.repository.DeliveryManRepository;
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
 * REST controller for managing DeliveryMan.
 */
@RestController
@RequestMapping("/api")
public class DeliveryManResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryManResource.class);

    private static final String ENTITY_NAME = "deliveryMan";

    private DeliveryManRepository deliveryManRepository;

    public DeliveryManResource(DeliveryManRepository deliveryManRepository) {
        this.deliveryManRepository = deliveryManRepository;
    }

    /**
     * POST  /delivery-men : Create a new deliveryMan.
     *
     * @param deliveryMan the deliveryMan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deliveryMan, or with status 400 (Bad Request) if the deliveryMan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/delivery-men")
    @Timed
    public ResponseEntity<DeliveryMan> createDeliveryMan(@RequestBody DeliveryMan deliveryMan) throws URISyntaxException {
        log.debug("REST request to save DeliveryMan : {}", deliveryMan);
        if (deliveryMan.getId() != null) {
            throw new BadRequestAlertException("A new deliveryMan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryMan result = deliveryManRepository.save(deliveryMan);
        return ResponseEntity.created(new URI("/api/delivery-men/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /delivery-men : Updates an existing deliveryMan.
     *
     * @param deliveryMan the deliveryMan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deliveryMan,
     * or with status 400 (Bad Request) if the deliveryMan is not valid,
     * or with status 500 (Internal Server Error) if the deliveryMan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/delivery-men")
    @Timed
    public ResponseEntity<DeliveryMan> updateDeliveryMan(@RequestBody DeliveryMan deliveryMan) throws URISyntaxException {
        log.debug("REST request to update DeliveryMan : {}", deliveryMan);
        if (deliveryMan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeliveryMan result = deliveryManRepository.save(deliveryMan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deliveryMan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /delivery-men : get all the deliveryMen.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deliveryMen in body
     */
    @GetMapping("/delivery-men")
    @Timed
    public List<DeliveryMan> getAllDeliveryMen() {
        log.debug("REST request to get all DeliveryMen");
        return deliveryManRepository.findAll();
    }

    /**
     * GET  /delivery-men/:id : get the "id" deliveryMan.
     *
     * @param id the id of the deliveryMan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deliveryMan, or with status 404 (Not Found)
     */
    @GetMapping("/delivery-men/{id}")
    @Timed
    public ResponseEntity<DeliveryMan> getDeliveryMan(@PathVariable Long id) {
        log.debug("REST request to get DeliveryMan : {}", id);
        Optional<DeliveryMan> deliveryMan = deliveryManRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deliveryMan);
    }

    /**
     * DELETE  /delivery-men/:id : delete the "id" deliveryMan.
     *
     * @param id the id of the deliveryMan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/delivery-men/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeliveryMan(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryMan : {}", id);

        deliveryManRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
