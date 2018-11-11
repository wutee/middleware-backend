package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.DeliveryPersonnel;
import com.propsy.backend.repository.DeliveryPersonnelRepository;
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
 * REST controller for managing DeliveryPersonnel.
 */
@RestController
@RequestMapping("/api")
public class DeliveryPersonnelResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryPersonnelResource.class);

    private static final String ENTITY_NAME = "deliveryPersonnel";

    private DeliveryPersonnelRepository deliveryPersonnelRepository;

    public DeliveryPersonnelResource(DeliveryPersonnelRepository deliveryPersonnelRepository) {
        this.deliveryPersonnelRepository = deliveryPersonnelRepository;
    }

    /**
     * POST  /delivery-personnels : Create a new deliveryPersonnel.
     *
     * @param deliveryPersonnel the deliveryPersonnel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deliveryPersonnel, or with status 400 (Bad Request) if the deliveryPersonnel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/delivery-personnels")
    @Timed
    public ResponseEntity<DeliveryPersonnel> createDeliveryPersonnel(@RequestBody DeliveryPersonnel deliveryPersonnel) throws URISyntaxException {
        log.debug("REST request to save DeliveryPersonnel : {}", deliveryPersonnel);
        if (deliveryPersonnel.getId() != null) {
            throw new BadRequestAlertException("A new deliveryPersonnel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryPersonnel result = deliveryPersonnelRepository.save(deliveryPersonnel);
        return ResponseEntity.created(new URI("/api/delivery-personnels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /delivery-personnels : Updates an existing deliveryPersonnel.
     *
     * @param deliveryPersonnel the deliveryPersonnel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deliveryPersonnel,
     * or with status 400 (Bad Request) if the deliveryPersonnel is not valid,
     * or with status 500 (Internal Server Error) if the deliveryPersonnel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/delivery-personnels")
    @Timed
    public ResponseEntity<DeliveryPersonnel> updateDeliveryPersonnel(@RequestBody DeliveryPersonnel deliveryPersonnel) throws URISyntaxException {
        log.debug("REST request to update DeliveryPersonnel : {}", deliveryPersonnel);
        if (deliveryPersonnel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeliveryPersonnel result = deliveryPersonnelRepository.save(deliveryPersonnel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deliveryPersonnel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /delivery-personnels : get all the deliveryPersonnels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deliveryPersonnels in body
     */
    @GetMapping("/delivery-personnels")
    @Timed
    public List<DeliveryPersonnel> getAllDeliveryPersonnels() {
        log.debug("REST request to get all DeliveryPersonnels");
        return deliveryPersonnelRepository.findAll();
    }

    /**
     * GET  /delivery-personnels/:id : get the "id" deliveryPersonnel.
     *
     * @param id the id of the deliveryPersonnel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deliveryPersonnel, or with status 404 (Not Found)
     */
    @GetMapping("/delivery-personnels/{id}")
    @Timed
    public ResponseEntity<DeliveryPersonnel> getDeliveryPersonnel(@PathVariable Long id) {
        log.debug("REST request to get DeliveryPersonnel : {}", id);
        Optional<DeliveryPersonnel> deliveryPersonnel = deliveryPersonnelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deliveryPersonnel);
    }

    /**
     * DELETE  /delivery-personnels/:id : delete the "id" deliveryPersonnel.
     *
     * @param id the id of the deliveryPersonnel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/delivery-personnels/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeliveryPersonnel(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryPersonnel : {}", id);

        deliveryPersonnelRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
