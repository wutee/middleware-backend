package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyTestApp;

import com.propsy.backend.domain.IngredientsToOrder;
import com.propsy.backend.repository.IngredientsToOrderRepository;
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
 * Test class for the IngredientsToOrderResource REST controller.
 *
 * @see IngredientsToOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyTestApp.class)
public class IngredientsToOrderResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private IngredientsToOrderRepository ingredientsToOrderRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIngredientsToOrderMockMvc;

    private IngredientsToOrder ingredientsToOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IngredientsToOrderResource ingredientsToOrderResource = new IngredientsToOrderResource(ingredientsToOrderRepository);
        this.restIngredientsToOrderMockMvc = MockMvcBuilders.standaloneSetup(ingredientsToOrderResource)
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
    public static IngredientsToOrder createEntity(EntityManager em) {
        IngredientsToOrder ingredientsToOrder = new IngredientsToOrder()
            .quantity(DEFAULT_QUANTITY);
        return ingredientsToOrder;
    }

    @Before
    public void initTest() {
        ingredientsToOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredientsToOrder() throws Exception {
        int databaseSizeBeforeCreate = ingredientsToOrderRepository.findAll().size();

        // Create the IngredientsToOrder
        restIngredientsToOrderMockMvc.perform(post("/api/ingredients-to-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientsToOrder)))
            .andExpect(status().isCreated());

        // Validate the IngredientsToOrder in the database
        List<IngredientsToOrder> ingredientsToOrderList = ingredientsToOrderRepository.findAll();
        assertThat(ingredientsToOrderList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientsToOrder testIngredientsToOrder = ingredientsToOrderList.get(ingredientsToOrderList.size() - 1);
        assertThat(testIngredientsToOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createIngredientsToOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientsToOrderRepository.findAll().size();

        // Create the IngredientsToOrder with an existing ID
        ingredientsToOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientsToOrderMockMvc.perform(post("/api/ingredients-to-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientsToOrder)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientsToOrder in the database
        List<IngredientsToOrder> ingredientsToOrderList = ingredientsToOrderRepository.findAll();
        assertThat(ingredientsToOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIngredientsToOrders() throws Exception {
        // Initialize the database
        ingredientsToOrderRepository.saveAndFlush(ingredientsToOrder);

        // Get all the ingredientsToOrderList
        restIngredientsToOrderMockMvc.perform(get("/api/ingredients-to-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientsToOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }
    
    @Test
    @Transactional
    public void getIngredientsToOrder() throws Exception {
        // Initialize the database
        ingredientsToOrderRepository.saveAndFlush(ingredientsToOrder);

        // Get the ingredientsToOrder
        restIngredientsToOrderMockMvc.perform(get("/api/ingredients-to-orders/{id}", ingredientsToOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientsToOrder.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingIngredientsToOrder() throws Exception {
        // Get the ingredientsToOrder
        restIngredientsToOrderMockMvc.perform(get("/api/ingredients-to-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredientsToOrder() throws Exception {
        // Initialize the database
        ingredientsToOrderRepository.saveAndFlush(ingredientsToOrder);

        int databaseSizeBeforeUpdate = ingredientsToOrderRepository.findAll().size();

        // Update the ingredientsToOrder
        IngredientsToOrder updatedIngredientsToOrder = ingredientsToOrderRepository.findById(ingredientsToOrder.getId()).get();
        // Disconnect from session so that the updates on updatedIngredientsToOrder are not directly saved in db
        em.detach(updatedIngredientsToOrder);
        updatedIngredientsToOrder
            .quantity(UPDATED_QUANTITY);

        restIngredientsToOrderMockMvc.perform(put("/api/ingredients-to-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIngredientsToOrder)))
            .andExpect(status().isOk());

        // Validate the IngredientsToOrder in the database
        List<IngredientsToOrder> ingredientsToOrderList = ingredientsToOrderRepository.findAll();
        assertThat(ingredientsToOrderList).hasSize(databaseSizeBeforeUpdate);
        IngredientsToOrder testIngredientsToOrder = ingredientsToOrderList.get(ingredientsToOrderList.size() - 1);
        assertThat(testIngredientsToOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredientsToOrder() throws Exception {
        int databaseSizeBeforeUpdate = ingredientsToOrderRepository.findAll().size();

        // Create the IngredientsToOrder

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientsToOrderMockMvc.perform(put("/api/ingredients-to-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientsToOrder)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientsToOrder in the database
        List<IngredientsToOrder> ingredientsToOrderList = ingredientsToOrderRepository.findAll();
        assertThat(ingredientsToOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIngredientsToOrder() throws Exception {
        // Initialize the database
        ingredientsToOrderRepository.saveAndFlush(ingredientsToOrder);

        int databaseSizeBeforeDelete = ingredientsToOrderRepository.findAll().size();

        // Get the ingredientsToOrder
        restIngredientsToOrderMockMvc.perform(delete("/api/ingredients-to-orders/{id}", ingredientsToOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IngredientsToOrder> ingredientsToOrderList = ingredientsToOrderRepository.findAll();
        assertThat(ingredientsToOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientsToOrder.class);
        IngredientsToOrder ingredientsToOrder1 = new IngredientsToOrder();
        ingredientsToOrder1.setId(1L);
        IngredientsToOrder ingredientsToOrder2 = new IngredientsToOrder();
        ingredientsToOrder2.setId(ingredientsToOrder1.getId());
        assertThat(ingredientsToOrder1).isEqualTo(ingredientsToOrder2);
        ingredientsToOrder2.setId(2L);
        assertThat(ingredientsToOrder1).isNotEqualTo(ingredientsToOrder2);
        ingredientsToOrder1.setId(null);
        assertThat(ingredientsToOrder1).isNotEqualTo(ingredientsToOrder2);
    }
}
