package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyBackendv01App;

import com.propsy.backend.domain.FoodOrder;
import com.propsy.backend.repository.FoodOrderRepository;
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
 * Test class for the FoodOrderResource REST controller.
 *
 * @see FoodOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyBackendv01App.class)
public class FoodOrderResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final String DEFAULT_USER_OPINION = "AAAAAAAAAA";
    private static final String UPDATED_USER_OPINION = "BBBBBBBBBB";

    private static final String DEFAULT_USER_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_USER_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_MAN_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_MAN_COMMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_LOYALTY_POINTS = 1;
    private static final Integer UPDATED_LOYALTY_POINTS = 2;

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Mock
    private FoodOrderRepository foodOrderRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFoodOrderMockMvc;

    private FoodOrder foodOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoodOrderResource foodOrderResource = new FoodOrderResource(foodOrderRepository);
        this.restFoodOrderMockMvc = MockMvcBuilders.standaloneSetup(foodOrderResource)
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
    public static FoodOrder createEntity(EntityManager em) {
        FoodOrder foodOrder = new FoodOrder()
            .date(DEFAULT_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .status(DEFAULT_STATUS)
            .price(DEFAULT_PRICE)
            .userOpinion(DEFAULT_USER_OPINION)
            .userComment(DEFAULT_USER_COMMENT)
            .deliveryManComment(DEFAULT_DELIVERY_MAN_COMMENT)
            .loyaltyPoints(DEFAULT_LOYALTY_POINTS);
        return foodOrder;
    }

    @Before
    public void initTest() {
        foodOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoodOrder() throws Exception {
        int databaseSizeBeforeCreate = foodOrderRepository.findAll().size();

        // Create the FoodOrder
        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrder)))
            .andExpect(status().isCreated());

        // Validate the FoodOrder in the database
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeCreate + 1);
        FoodOrder testFoodOrder = foodOrderList.get(foodOrderList.size() - 1);
        assertThat(testFoodOrder.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFoodOrder.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testFoodOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFoodOrder.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFoodOrder.getUserOpinion()).isEqualTo(DEFAULT_USER_OPINION);
        assertThat(testFoodOrder.getUserComment()).isEqualTo(DEFAULT_USER_COMMENT);
        assertThat(testFoodOrder.getDeliveryManComment()).isEqualTo(DEFAULT_DELIVERY_MAN_COMMENT);
        assertThat(testFoodOrder.getLoyaltyPoints()).isEqualTo(DEFAULT_LOYALTY_POINTS);
    }

    @Test
    @Transactional
    public void createFoodOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodOrderRepository.findAll().size();

        // Create the FoodOrder with an existing ID
        foodOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrder)))
            .andExpect(status().isBadRequest());

        // Validate the FoodOrder in the database
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderRepository.findAll().size();
        // set the field null
        foodOrder.setDate(null);

        // Create the FoodOrder, which fails.

        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrder)))
            .andExpect(status().isBadRequest());

        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderRepository.findAll().size();
        // set the field null
        foodOrder.setLastUpdatedDate(null);

        // Create the FoodOrder, which fails.

        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrder)))
            .andExpect(status().isBadRequest());

        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderRepository.findAll().size();
        // set the field null
        foodOrder.setStatus(null);

        // Create the FoodOrder, which fails.

        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrder)))
            .andExpect(status().isBadRequest());

        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderRepository.findAll().size();
        // set the field null
        foodOrder.setPrice(null);

        // Create the FoodOrder, which fails.

        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrder)))
            .andExpect(status().isBadRequest());

        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoodOrders() throws Exception {
        // Initialize the database
        foodOrderRepository.saveAndFlush(foodOrder);

        // Get all the foodOrderList
        restFoodOrderMockMvc.perform(get("/api/food-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(DEFAULT_LAST_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].userOpinion").value(hasItem(DEFAULT_USER_OPINION.toString())))
            .andExpect(jsonPath("$.[*].userComment").value(hasItem(DEFAULT_USER_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].deliveryManComment").value(hasItem(DEFAULT_DELIVERY_MAN_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].loyaltyPoints").value(hasItem(DEFAULT_LOYALTY_POINTS)));
    }
    
    public void getAllFoodOrdersWithEagerRelationshipsIsEnabled() throws Exception {
        FoodOrderResource foodOrderResource = new FoodOrderResource(foodOrderRepositoryMock);
        when(foodOrderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFoodOrderMockMvc = MockMvcBuilders.standaloneSetup(foodOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFoodOrderMockMvc.perform(get("/api/food-orders?eagerload=true"))
        .andExpect(status().isOk());

        verify(foodOrderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllFoodOrdersWithEagerRelationshipsIsNotEnabled() throws Exception {
        FoodOrderResource foodOrderResource = new FoodOrderResource(foodOrderRepositoryMock);
            when(foodOrderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFoodOrderMockMvc = MockMvcBuilders.standaloneSetup(foodOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFoodOrderMockMvc.perform(get("/api/food-orders?eagerload=true"))
        .andExpect(status().isOk());

            verify(foodOrderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFoodOrder() throws Exception {
        // Initialize the database
        foodOrderRepository.saveAndFlush(foodOrder);

        // Get the foodOrder
        restFoodOrderMockMvc.perform(get("/api/food-orders/{id}", foodOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foodOrder.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(DEFAULT_LAST_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.userOpinion").value(DEFAULT_USER_OPINION.toString()))
            .andExpect(jsonPath("$.userComment").value(DEFAULT_USER_COMMENT.toString()))
            .andExpect(jsonPath("$.deliveryManComment").value(DEFAULT_DELIVERY_MAN_COMMENT.toString()))
            .andExpect(jsonPath("$.loyaltyPoints").value(DEFAULT_LOYALTY_POINTS));
    }

    @Test
    @Transactional
    public void getNonExistingFoodOrder() throws Exception {
        // Get the foodOrder
        restFoodOrderMockMvc.perform(get("/api/food-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoodOrder() throws Exception {
        // Initialize the database
        foodOrderRepository.saveAndFlush(foodOrder);

        int databaseSizeBeforeUpdate = foodOrderRepository.findAll().size();

        // Update the foodOrder
        FoodOrder updatedFoodOrder = foodOrderRepository.findById(foodOrder.getId()).get();
        // Disconnect from session so that the updates on updatedFoodOrder are not directly saved in db
        em.detach(updatedFoodOrder);
        updatedFoodOrder
            .date(UPDATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .status(UPDATED_STATUS)
            .price(UPDATED_PRICE)
            .userOpinion(UPDATED_USER_OPINION)
            .userComment(UPDATED_USER_COMMENT)
            .deliveryManComment(UPDATED_DELIVERY_MAN_COMMENT)
            .loyaltyPoints(UPDATED_LOYALTY_POINTS);

        restFoodOrderMockMvc.perform(put("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFoodOrder)))
            .andExpect(status().isOk());

        // Validate the FoodOrder in the database
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeUpdate);
        FoodOrder testFoodOrder = foodOrderList.get(foodOrderList.size() - 1);
        assertThat(testFoodOrder.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFoodOrder.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testFoodOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFoodOrder.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFoodOrder.getUserOpinion()).isEqualTo(UPDATED_USER_OPINION);
        assertThat(testFoodOrder.getUserComment()).isEqualTo(UPDATED_USER_COMMENT);
        assertThat(testFoodOrder.getDeliveryManComment()).isEqualTo(UPDATED_DELIVERY_MAN_COMMENT);
        assertThat(testFoodOrder.getLoyaltyPoints()).isEqualTo(UPDATED_LOYALTY_POINTS);
    }

    @Test
    @Transactional
    public void updateNonExistingFoodOrder() throws Exception {
        int databaseSizeBeforeUpdate = foodOrderRepository.findAll().size();

        // Create the FoodOrder

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodOrderMockMvc.perform(put("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrder)))
            .andExpect(status().isBadRequest());

        // Validate the FoodOrder in the database
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoodOrder() throws Exception {
        // Initialize the database
        foodOrderRepository.saveAndFlush(foodOrder);

        int databaseSizeBeforeDelete = foodOrderRepository.findAll().size();

        // Get the foodOrder
        restFoodOrderMockMvc.perform(delete("/api/food-orders/{id}", foodOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodOrder.class);
        FoodOrder foodOrder1 = new FoodOrder();
        foodOrder1.setId(1L);
        FoodOrder foodOrder2 = new FoodOrder();
        foodOrder2.setId(foodOrder1.getId());
        assertThat(foodOrder1).isEqualTo(foodOrder2);
        foodOrder2.setId(2L);
        assertThat(foodOrder1).isNotEqualTo(foodOrder2);
        foodOrder1.setId(null);
        assertThat(foodOrder1).isNotEqualTo(foodOrder2);
    }
}
