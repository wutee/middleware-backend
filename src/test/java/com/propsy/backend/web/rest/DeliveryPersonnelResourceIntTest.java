package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyBackendv01App;

import com.propsy.backend.domain.DeliveryPersonnel;
import com.propsy.backend.repository.DeliveryPersonnelRepository;
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
 * Test class for the DeliveryPersonnelResource REST controller.
 *
 * @see DeliveryPersonnelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyBackendv01App.class)
public class DeliveryPersonnelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    @Autowired
    private DeliveryPersonnelRepository deliveryPersonnelRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeliveryPersonnelMockMvc;

    private DeliveryPersonnel deliveryPersonnel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveryPersonnelResource deliveryPersonnelResource = new DeliveryPersonnelResource(deliveryPersonnelRepository);
        this.restDeliveryPersonnelMockMvc = MockMvcBuilders.standaloneSetup(deliveryPersonnelResource)
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
    public static DeliveryPersonnel createEntity(EntityManager em) {
        DeliveryPersonnel deliveryPersonnel = new DeliveryPersonnel()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME);
        return deliveryPersonnel;
    }

    @Before
    public void initTest() {
        deliveryPersonnel = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveryPersonnel() throws Exception {
        int databaseSizeBeforeCreate = deliveryPersonnelRepository.findAll().size();

        // Create the DeliveryPersonnel
        restDeliveryPersonnelMockMvc.perform(post("/api/delivery-personnels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryPersonnel)))
            .andExpect(status().isCreated());

        // Validate the DeliveryPersonnel in the database
        List<DeliveryPersonnel> deliveryPersonnelList = deliveryPersonnelRepository.findAll();
        assertThat(deliveryPersonnelList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryPersonnel testDeliveryPersonnel = deliveryPersonnelList.get(deliveryPersonnelList.size() - 1);
        assertThat(testDeliveryPersonnel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeliveryPersonnel.getSurname()).isEqualTo(DEFAULT_SURNAME);
    }

    @Test
    @Transactional
    public void createDeliveryPersonnelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryPersonnelRepository.findAll().size();

        // Create the DeliveryPersonnel with an existing ID
        deliveryPersonnel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryPersonnelMockMvc.perform(post("/api/delivery-personnels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryPersonnel)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryPersonnel in the database
        List<DeliveryPersonnel> deliveryPersonnelList = deliveryPersonnelRepository.findAll();
        assertThat(deliveryPersonnelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeliveryPersonnels() throws Exception {
        // Initialize the database
        deliveryPersonnelRepository.saveAndFlush(deliveryPersonnel);

        // Get all the deliveryPersonnelList
        restDeliveryPersonnelMockMvc.perform(get("/api/delivery-personnels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryPersonnel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())));
    }
    
    @Test
    @Transactional
    public void getDeliveryPersonnel() throws Exception {
        // Initialize the database
        deliveryPersonnelRepository.saveAndFlush(deliveryPersonnel);

        // Get the deliveryPersonnel
        restDeliveryPersonnelMockMvc.perform(get("/api/delivery-personnels/{id}", deliveryPersonnel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryPersonnel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeliveryPersonnel() throws Exception {
        // Get the deliveryPersonnel
        restDeliveryPersonnelMockMvc.perform(get("/api/delivery-personnels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryPersonnel() throws Exception {
        // Initialize the database
        deliveryPersonnelRepository.saveAndFlush(deliveryPersonnel);

        int databaseSizeBeforeUpdate = deliveryPersonnelRepository.findAll().size();

        // Update the deliveryPersonnel
        DeliveryPersonnel updatedDeliveryPersonnel = deliveryPersonnelRepository.findById(deliveryPersonnel.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryPersonnel are not directly saved in db
        em.detach(updatedDeliveryPersonnel);
        updatedDeliveryPersonnel
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME);

        restDeliveryPersonnelMockMvc.perform(put("/api/delivery-personnels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeliveryPersonnel)))
            .andExpect(status().isOk());

        // Validate the DeliveryPersonnel in the database
        List<DeliveryPersonnel> deliveryPersonnelList = deliveryPersonnelRepository.findAll();
        assertThat(deliveryPersonnelList).hasSize(databaseSizeBeforeUpdate);
        DeliveryPersonnel testDeliveryPersonnel = deliveryPersonnelList.get(deliveryPersonnelList.size() - 1);
        assertThat(testDeliveryPersonnel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeliveryPersonnel.getSurname()).isEqualTo(UPDATED_SURNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveryPersonnel() throws Exception {
        int databaseSizeBeforeUpdate = deliveryPersonnelRepository.findAll().size();

        // Create the DeliveryPersonnel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryPersonnelMockMvc.perform(put("/api/delivery-personnels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryPersonnel)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryPersonnel in the database
        List<DeliveryPersonnel> deliveryPersonnelList = deliveryPersonnelRepository.findAll();
        assertThat(deliveryPersonnelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeliveryPersonnel() throws Exception {
        // Initialize the database
        deliveryPersonnelRepository.saveAndFlush(deliveryPersonnel);

        int databaseSizeBeforeDelete = deliveryPersonnelRepository.findAll().size();

        // Get the deliveryPersonnel
        restDeliveryPersonnelMockMvc.perform(delete("/api/delivery-personnels/{id}", deliveryPersonnel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeliveryPersonnel> deliveryPersonnelList = deliveryPersonnelRepository.findAll();
        assertThat(deliveryPersonnelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryPersonnel.class);
        DeliveryPersonnel deliveryPersonnel1 = new DeliveryPersonnel();
        deliveryPersonnel1.setId(1L);
        DeliveryPersonnel deliveryPersonnel2 = new DeliveryPersonnel();
        deliveryPersonnel2.setId(deliveryPersonnel1.getId());
        assertThat(deliveryPersonnel1).isEqualTo(deliveryPersonnel2);
        deliveryPersonnel2.setId(2L);
        assertThat(deliveryPersonnel1).isNotEqualTo(deliveryPersonnel2);
        deliveryPersonnel1.setId(null);
        assertThat(deliveryPersonnel1).isNotEqualTo(deliveryPersonnel2);
    }
}
