package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyTestApp;

import com.propsy.backend.domain.Deliveries;
import com.propsy.backend.repository.DeliveriesRepository;
import com.propsy.backend.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.propsy.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeliveriesResource REST controller.
 *
 * @see DeliveriesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyTestApp.class)
public class DeliveriesResourceIntTest {

    @Autowired
    private DeliveriesRepository deliveriesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeliveriesMockMvc;

    private Deliveries deliveries;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveriesResource deliveriesResource = new DeliveriesResource(deliveriesRepository);
        this.restDeliveriesMockMvc = MockMvcBuilders.standaloneSetup(deliveriesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deliveries createEntity(EntityManager em) {
        Deliveries deliveries = new Deliveries();
        return deliveries;
    }

    @Before
    public void initTest() {
        deliveries = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveries() throws Exception {
        int databaseSizeBeforeCreate = deliveriesRepository.findAll().size();

        // Create the Deliveries
        restDeliveriesMockMvc.perform(post("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveries)))
            .andExpect(status().isCreated());

        // Validate the Deliveries in the database
        List<Deliveries> deliveriesList = deliveriesRepository.findAll();
        assertThat(deliveriesList).hasSize(databaseSizeBeforeCreate + 1);
        Deliveries testDeliveries = deliveriesList.get(deliveriesList.size() - 1);
    }

    @Test
    @Transactional
    public void createDeliveriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveriesRepository.findAll().size();

        // Create the Deliveries with an existing ID
        deliveries.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveriesMockMvc.perform(post("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveries)))
            .andExpect(status().isBadRequest());

        // Validate the Deliveries in the database
        List<Deliveries> deliveriesList = deliveriesRepository.findAll();
        assertThat(deliveriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeliveries() throws Exception {
        // Initialize the database
        deliveriesRepository.saveAndFlush(deliveries);

        // Get all the deliveriesList
        restDeliveriesMockMvc.perform(get("/api/deliveries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveries.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDeliveries() throws Exception {
        // Initialize the database
        deliveriesRepository.saveAndFlush(deliveries);

        // Get the deliveries
        restDeliveriesMockMvc.perform(get("/api/deliveries/{id}", deliveries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deliveries.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDeliveries() throws Exception {
        // Get the deliveries
        restDeliveriesMockMvc.perform(get("/api/deliveries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveries() throws Exception {
        // Initialize the database
        deliveriesRepository.saveAndFlush(deliveries);

        int databaseSizeBeforeUpdate = deliveriesRepository.findAll().size();

        // Update the deliveries
        Deliveries updatedDeliveries = deliveriesRepository.findById(deliveries.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveries are not directly saved in db
        em.detach(updatedDeliveries);

        restDeliveriesMockMvc.perform(put("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeliveries)))
            .andExpect(status().isOk());

        // Validate the Deliveries in the database
        List<Deliveries> deliveriesList = deliveriesRepository.findAll();
        assertThat(deliveriesList).hasSize(databaseSizeBeforeUpdate);
        Deliveries testDeliveries = deliveriesList.get(deliveriesList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveries() throws Exception {
        int databaseSizeBeforeUpdate = deliveriesRepository.findAll().size();

        // Create the Deliveries

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveriesMockMvc.perform(put("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveries)))
            .andExpect(status().isBadRequest());

        // Validate the Deliveries in the database
        List<Deliveries> deliveriesList = deliveriesRepository.findAll();
        assertThat(deliveriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeliveries() throws Exception {
        // Initialize the database
        deliveriesRepository.saveAndFlush(deliveries);

        int databaseSizeBeforeDelete = deliveriesRepository.findAll().size();

        // Get the deliveries
        restDeliveriesMockMvc.perform(delete("/api/deliveries/{id}", deliveries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Deliveries> deliveriesList = deliveriesRepository.findAll();
        assertThat(deliveriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deliveries.class);
        Deliveries deliveries1 = new Deliveries();
        deliveries1.setId(1L);
        Deliveries deliveries2 = new Deliveries();
        deliveries2.setId(deliveries1.getId());
        assertThat(deliveries1).isEqualTo(deliveries2);
        deliveries2.setId(2L);
        assertThat(deliveries1).isNotEqualTo(deliveries2);
        deliveries1.setId(null);
        assertThat(deliveries1).isNotEqualTo(deliveries2);
    }
}
