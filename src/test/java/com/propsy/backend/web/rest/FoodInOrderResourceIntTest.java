package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyTestApp;

import com.propsy.backend.domain.FoodInOrder;
import com.propsy.backend.repository.FoodInOrderRepository;
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
 * Test class for the FoodInOrderResource REST controller.
 *
 * @see FoodInOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyTestApp.class)
public class FoodInOrderResourceIntTest {

    @Autowired
    private FoodInOrderRepository foodInOrderRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFoodInOrderMockMvc;

    private FoodInOrder foodInOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoodInOrderResource foodInOrderResource = new FoodInOrderResource(foodInOrderRepository);
        this.restFoodInOrderMockMvc = MockMvcBuilders.standaloneSetup(foodInOrderResource)
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
    public static FoodInOrder createEntity(EntityManager em) {
        FoodInOrder foodInOrder = new FoodInOrder();
        return foodInOrder;
    }

    @Before
    public void initTest() {
        foodInOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoodInOrder() throws Exception {
        int databaseSizeBeforeCreate = foodInOrderRepository.findAll().size();

        // Create the FoodInOrder
        restFoodInOrderMockMvc.perform(post("/api/food-in-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodInOrder)))
            .andExpect(status().isCreated());

        // Validate the FoodInOrder in the database
        List<FoodInOrder> foodInOrderList = foodInOrderRepository.findAll();
        assertThat(foodInOrderList).hasSize(databaseSizeBeforeCreate + 1);
        FoodInOrder testFoodInOrder = foodInOrderList.get(foodInOrderList.size() - 1);
    }

    @Test
    @Transactional
    public void createFoodInOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodInOrderRepository.findAll().size();

        // Create the FoodInOrder with an existing ID
        foodInOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodInOrderMockMvc.perform(post("/api/food-in-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodInOrder)))
            .andExpect(status().isBadRequest());

        // Validate the FoodInOrder in the database
        List<FoodInOrder> foodInOrderList = foodInOrderRepository.findAll();
        assertThat(foodInOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFoodInOrders() throws Exception {
        // Initialize the database
        foodInOrderRepository.saveAndFlush(foodInOrder);

        // Get all the foodInOrderList
        restFoodInOrderMockMvc.perform(get("/api/food-in-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodInOrder.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getFoodInOrder() throws Exception {
        // Initialize the database
        foodInOrderRepository.saveAndFlush(foodInOrder);

        // Get the foodInOrder
        restFoodInOrderMockMvc.perform(get("/api/food-in-orders/{id}", foodInOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foodInOrder.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFoodInOrder() throws Exception {
        // Get the foodInOrder
        restFoodInOrderMockMvc.perform(get("/api/food-in-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoodInOrder() throws Exception {
        // Initialize the database
        foodInOrderRepository.saveAndFlush(foodInOrder);

        int databaseSizeBeforeUpdate = foodInOrderRepository.findAll().size();

        // Update the foodInOrder
        FoodInOrder updatedFoodInOrder = foodInOrderRepository.findById(foodInOrder.getId()).get();
        // Disconnect from session so that the updates on updatedFoodInOrder are not directly saved in db
        em.detach(updatedFoodInOrder);

        restFoodInOrderMockMvc.perform(put("/api/food-in-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFoodInOrder)))
            .andExpect(status().isOk());

        // Validate the FoodInOrder in the database
        List<FoodInOrder> foodInOrderList = foodInOrderRepository.findAll();
        assertThat(foodInOrderList).hasSize(databaseSizeBeforeUpdate);
        FoodInOrder testFoodInOrder = foodInOrderList.get(foodInOrderList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFoodInOrder() throws Exception {
        int databaseSizeBeforeUpdate = foodInOrderRepository.findAll().size();

        // Create the FoodInOrder

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodInOrderMockMvc.perform(put("/api/food-in-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodInOrder)))
            .andExpect(status().isBadRequest());

        // Validate the FoodInOrder in the database
        List<FoodInOrder> foodInOrderList = foodInOrderRepository.findAll();
        assertThat(foodInOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoodInOrder() throws Exception {
        // Initialize the database
        foodInOrderRepository.saveAndFlush(foodInOrder);

        int databaseSizeBeforeDelete = foodInOrderRepository.findAll().size();

        // Get the foodInOrder
        restFoodInOrderMockMvc.perform(delete("/api/food-in-orders/{id}", foodInOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FoodInOrder> foodInOrderList = foodInOrderRepository.findAll();
        assertThat(foodInOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodInOrder.class);
        FoodInOrder foodInOrder1 = new FoodInOrder();
        foodInOrder1.setId(1L);
        FoodInOrder foodInOrder2 = new FoodInOrder();
        foodInOrder2.setId(foodInOrder1.getId());
        assertThat(foodInOrder1).isEqualTo(foodInOrder2);
        foodInOrder2.setId(2L);
        assertThat(foodInOrder1).isNotEqualTo(foodInOrder2);
        foodInOrder1.setId(null);
        assertThat(foodInOrder1).isNotEqualTo(foodInOrder2);
    }
}
