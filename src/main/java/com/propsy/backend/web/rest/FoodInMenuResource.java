package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.FoodInMenu;
import com.propsy.backend.repository.FoodInMenuRepository;
import com.propsy.backend.web.rest.errors.BadRequestAlertException;
import com.propsy.backend.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FoodInMenu.
 */
@RestController
@RequestMapping("/api")
public class FoodInMenuResource {

    private final Logger log = LoggerFactory.getLogger(FoodInMenuResource.class);

    private static final String ENTITY_NAME = "foodInMenu";

    private FoodInMenuRepository foodInMenuRepository;

    public FoodInMenuResource(FoodInMenuRepository foodInMenuRepository) {
        this.foodInMenuRepository = foodInMenuRepository;
    }

    /**
     * POST  /food-in-menus : Create a new foodInMenu.
     *
     * @param foodInMenu the foodInMenu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new foodInMenu, or with status 400 (Bad Request) if the foodInMenu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/food-in-menus")
    @Timed
    public ResponseEntity<FoodInMenu> createFoodInMenu(@Valid @RequestBody FoodInMenu foodInMenu) throws URISyntaxException {
        log.debug("REST request to save FoodInMenu : {}", foodInMenu);
        if (foodInMenu.getId() != null) {
            throw new BadRequestAlertException("A new foodInMenu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodInMenu result = foodInMenuRepository.save(foodInMenu);
        return ResponseEntity.created(new URI("/api/food-in-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /food-in-menus : Updates an existing foodInMenu.
     *
     * @param foodInMenu the foodInMenu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated foodInMenu,
     * or with status 400 (Bad Request) if the foodInMenu is not valid,
     * or with status 500 (Internal Server Error) if the foodInMenu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/food-in-menus")
    @Timed
    public ResponseEntity<FoodInMenu> updateFoodInMenu(@Valid @RequestBody FoodInMenu foodInMenu) throws URISyntaxException {
        log.debug("REST request to update FoodInMenu : {}", foodInMenu);
        if (foodInMenu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoodInMenu result = foodInMenuRepository.save(foodInMenu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, foodInMenu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /food-in-menus : get all the foodInMenus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of foodInMenus in body
     */
    @GetMapping("/food-in-menus")
    @Timed
    public List<FoodInMenu> getAllFoodInMenus() {
        log.debug("REST request to get all FoodInMenus");
        return foodInMenuRepository.findAll();
    }

    /**
     * GET  /food-in-menus/:id : get the "id" foodInMenu.
     *
     * @param id the id of the foodInMenu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the foodInMenu, or with status 404 (Not Found)
     */
    @GetMapping("/food-in-menus/{id}")
    @Timed
    public ResponseEntity<FoodInMenu> getFoodInMenu(@PathVariable Long id) {
        log.debug("REST request to get FoodInMenu : {}", id);
        Optional<FoodInMenu> foodInMenu = foodInMenuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(foodInMenu);
    }

    /**
     * DELETE  /food-in-menus/:id : delete the "id" foodInMenu.
     *
     * @param id the id of the foodInMenu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/food-in-menus/{id}")
    @Timed
    public ResponseEntity<Void> deleteFoodInMenu(@PathVariable Long id) {
        log.debug("REST request to delete FoodInMenu : {}", id);

        foodInMenuRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
