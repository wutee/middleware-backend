package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyBackendv01App;

import com.propsy.backend.domain.IngredientOrder;
import com.propsy.backend.repository.IngredientOrderRepository;
import com.propsy.backend.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static com.propsy.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the IngredientOrderResource REST controller.
 *
 * @see IngredientOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyBackendv01App.class)
public class IngredientOrderResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_TOTAL_PRICE = 1F;
    private static final Float UPDATED_TOTAL_PRICE = 2F;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private IngredientOrderRepository ingredientOrderRepository;

    @Mock
    private IngredientOrderRepository ingredientOrderRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIngredientOrderMockMvc;

    private IngredientOrder ingredientOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IngredientOrderResource ingredientOrderResource = new IngredientOrderResource(ingredientOrderRepository);
        this.restIngredientOrderMockMvc = MockMvcBuilders.standaloneSetup(ingredientOrderResource)
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
    public static IngredientOrder createEntity(EntityManager em) {
        IngredientOrder ingredientOrder = new IngredientOrder()
            .date(DEFAULT_DATE)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .comment(DEFAULT_COMMENT);
        return ingredientOrder;
    }

    @Before
    public void initTest() {
        ingredientOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredientOrder() throws Exception {
        int databaseSizeBeforeCreate = ingredientOrderRepository.findAll().size();

        // Create the IngredientOrder
        restIngredientOrderMockMvc.perform(post("/api/ingredient-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientOrder)))
            .andExpect(status().isCreated());

        // Validate the IngredientOrder in the database
        List<IngredientOrder> ingredientOrderList = ingredientOrderRepository.findAll();
        assertThat(ingredientOrderList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientOrder testIngredientOrder = ingredientOrderList.get(ingredientOrderList.size() - 1);
        assertThat(testIngredientOrder.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testIngredientOrder.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testIngredientOrder.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createIngredientOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientOrderRepository.findAll().size();

        // Create the IngredientOrder with an existing ID
        ingredientOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientOrderMockMvc.perform(post("/api/ingredient-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientOrder)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientOrder in the database
        List<IngredientOrder> ingredientOrderList = ingredientOrderRepository.findAll();
        assertThat(ingredientOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientOrderRepository.findAll().size();
        // set the field null
        ingredientOrder.setDate(null);

        // Create the IngredientOrder, which fails.

        restIngredientOrderMockMvc.perform(post("/api/ingredient-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientOrder)))
            .andExpect(status().isBadRequest());

        List<IngredientOrder> ingredientOrderList = ingredientOrderRepository.findAll();
        assertThat(ingredientOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientOrderRepository.findAll().size();
        // set the field null
        ingredientOrder.setTotalPrice(null);

        // Create the IngredientOrder, which fails.

        restIngredientOrderMockMvc.perform(post("/api/ingredient-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientOrder)))
            .andExpect(status().isBadRequest());

        List<IngredientOrder> ingredientOrderList = ingredientOrderRepository.findAll();
        assertThat(ingredientOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIngredientOrders() throws Exception {
        // Initialize the database
        ingredientOrderRepository.saveAndFlush(ingredientOrder);

        // Get all the ingredientOrderList
        restIngredientOrderMockMvc.perform(get("/api/ingredient-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }
    
    public void getAllIngredientOrdersWithEagerRelationshipsIsEnabled() throws Exception {
        IngredientOrderResource ingredientOrderResource = new IngredientOrderResource(ingredientOrderRepositoryMock);
        when(ingredientOrderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restIngredientOrderMockMvc = MockMvcBuilders.standaloneSetup(ingredientOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restIngredientOrderMockMvc.perform(get("/api/ingredient-orders?eagerload=true"))
        .andExpect(status().isOk());

        verify(ingredientOrderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllIngredientOrdersWithEagerRelationshipsIsNotEnabled() throws Exception {
        IngredientOrderResource ingredientOrderResource = new IngredientOrderResource(ingredientOrderRepositoryMock);
            when(ingredientOrderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restIngredientOrderMockMvc = MockMvcBuilders.standaloneSetup(ingredientOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restIngredientOrderMockMvc.perform(get("/api/ingredient-orders?eagerload=true"))
        .andExpect(status().isOk());

            verify(ingredientOrderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getIngredientOrder() throws Exception {
        // Initialize the database
        ingredientOrderRepository.saveAndFlush(ingredientOrder);

        // Get the ingredientOrder
        restIngredientOrderMockMvc.perform(get("/api/ingredient-orders/{id}", ingredientOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientOrder.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIngredientOrder() throws Exception {
        // Get the ingredientOrder
        restIngredientOrderMockMvc.perform(get("/api/ingredient-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredientOrder() throws Exception {
        // Initialize the database
        ingredientOrderRepository.saveAndFlush(ingredientOrder);

        int databaseSizeBeforeUpdate = ingredientOrderRepository.findAll().size();

        // Update the ingredientOrder
        IngredientOrder updatedIngredientOrder = ingredientOrderRepository.findById(ingredientOrder.getId()).get();
        // Disconnect from session so that the updates on updatedIngredientOrder are not directly saved in db
        em.detach(updatedIngredientOrder);
        updatedIngredientOrder
            .date(UPDATED_DATE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .comment(UPDATED_COMMENT);

        restIngredientOrderMockMvc.perform(put("/api/ingredient-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIngredientOrder)))
            .andExpect(status().isOk());

        // Validate the IngredientOrder in the database
        List<IngredientOrder> ingredientOrderList = ingredientOrderRepository.findAll();
        assertThat(ingredientOrderList).hasSize(databaseSizeBeforeUpdate);
        IngredientOrder testIngredientOrder = ingredientOrderList.get(ingredientOrderList.size() - 1);
        assertThat(testIngredientOrder.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testIngredientOrder.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testIngredientOrder.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredientOrder() throws Exception {
        int databaseSizeBeforeUpdate = ingredientOrderRepository.findAll().size();

        // Create the IngredientOrder

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientOrderMockMvc.perform(put("/api/ingredient-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientOrder)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientOrder in the database
        List<IngredientOrder> ingredientOrderList = ingredientOrderRepository.findAll();
        assertThat(ingredientOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIngredientOrder() throws Exception {
        // Initialize the database
        ingredientOrderRepository.saveAndFlush(ingredientOrder);

        int databaseSizeBeforeDelete = ingredientOrderRepository.findAll().size();

        // Get the ingredientOrder
        restIngredientOrderMockMvc.perform(delete("/api/ingredient-orders/{id}", ingredientOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IngredientOrder> ingredientOrderList = ingredientOrderRepository.findAll();
        assertThat(ingredientOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientOrder.class);
        IngredientOrder ingredientOrder1 = new IngredientOrder();
        ingredientOrder1.setId(1L);
        IngredientOrder ingredientOrder2 = new IngredientOrder();
        ingredientOrder2.setId(ingredientOrder1.getId());
        assertThat(ingredientOrder1).isEqualTo(ingredientOrder2);
        ingredientOrder2.setId(2L);
        assertThat(ingredientOrder1).isNotEqualTo(ingredientOrder2);
        ingredientOrder1.setId(null);
        assertThat(ingredientOrder1).isNotEqualTo(ingredientOrder2);
    }
}
