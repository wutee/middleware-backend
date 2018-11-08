package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyTestApp;

import com.propsy.backend.domain.CustomerOrder;
import com.propsy.backend.repository.CustomerOrderRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.propsy.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomerOrderResource REST controller.
 *
 * @see CustomerOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyTestApp.class)
public class CustomerOrderResourceIntTest {

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
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerOrderMockMvc;

    private CustomerOrder customerOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerOrderResource customerOrderResource = new CustomerOrderResource(customerOrderRepository);
        this.restCustomerOrderMockMvc = MockMvcBuilders.standaloneSetup(customerOrderResource)
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
    public static CustomerOrder createEntity(EntityManager em) {
        CustomerOrder customerOrder = new CustomerOrder()
            .date(DEFAULT_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .status(DEFAULT_STATUS)
            .price(DEFAULT_PRICE)
            .userOpinion(DEFAULT_USER_OPINION)
            .userComment(DEFAULT_USER_COMMENT)
            .deliveryManComment(DEFAULT_DELIVERY_MAN_COMMENT)
            .loyaltyPoints(DEFAULT_LOYALTY_POINTS);
        return customerOrder;
    }

    @Before
    public void initTest() {
        customerOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerOrder() throws Exception {
        int databaseSizeBeforeCreate = customerOrderRepository.findAll().size();

        // Create the CustomerOrder
        restCustomerOrderMockMvc.perform(post("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isCreated());

        // Validate the CustomerOrder in the database
        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerOrder testCustomerOrder = customerOrderList.get(customerOrderList.size() - 1);
        assertThat(testCustomerOrder.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCustomerOrder.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testCustomerOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustomerOrder.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testCustomerOrder.getUserOpinion()).isEqualTo(DEFAULT_USER_OPINION);
        assertThat(testCustomerOrder.getUserComment()).isEqualTo(DEFAULT_USER_COMMENT);
        assertThat(testCustomerOrder.getDeliveryManComment()).isEqualTo(DEFAULT_DELIVERY_MAN_COMMENT);
        assertThat(testCustomerOrder.getLoyaltyPoints()).isEqualTo(DEFAULT_LOYALTY_POINTS);
    }

    @Test
    @Transactional
    public void createCustomerOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerOrderRepository.findAll().size();

        // Create the CustomerOrder with an existing ID
        customerOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerOrderMockMvc.perform(post("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerOrder in the database
        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOrderRepository.findAll().size();
        // set the field null
        customerOrder.setDate(null);

        // Create the CustomerOrder, which fails.

        restCustomerOrderMockMvc.perform(post("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isBadRequest());

        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOrderRepository.findAll().size();
        // set the field null
        customerOrder.setLastUpdatedDate(null);

        // Create the CustomerOrder, which fails.

        restCustomerOrderMockMvc.perform(post("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isBadRequest());

        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOrderRepository.findAll().size();
        // set the field null
        customerOrder.setStatus(null);

        // Create the CustomerOrder, which fails.

        restCustomerOrderMockMvc.perform(post("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isBadRequest());

        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOrderRepository.findAll().size();
        // set the field null
        customerOrder.setPrice(null);

        // Create the CustomerOrder, which fails.

        restCustomerOrderMockMvc.perform(post("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isBadRequest());

        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerOrders() throws Exception {
        // Initialize the database
        customerOrderRepository.saveAndFlush(customerOrder);

        // Get all the customerOrderList
        restCustomerOrderMockMvc.perform(get("/api/customer-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(DEFAULT_LAST_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].userOpinion").value(hasItem(DEFAULT_USER_OPINION.toString())))
            .andExpect(jsonPath("$.[*].userComment").value(hasItem(DEFAULT_USER_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].deliveryManComment").value(hasItem(DEFAULT_DELIVERY_MAN_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].loyaltyPoints").value(hasItem(DEFAULT_LOYALTY_POINTS)));
    }
    
    @Test
    @Transactional
    public void getCustomerOrder() throws Exception {
        // Initialize the database
        customerOrderRepository.saveAndFlush(customerOrder);

        // Get the customerOrder
        restCustomerOrderMockMvc.perform(get("/api/customer-orders/{id}", customerOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerOrder.getId().intValue()))
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
    public void getNonExistingCustomerOrder() throws Exception {
        // Get the customerOrder
        restCustomerOrderMockMvc.perform(get("/api/customer-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerOrder() throws Exception {
        // Initialize the database
        customerOrderRepository.saveAndFlush(customerOrder);

        int databaseSizeBeforeUpdate = customerOrderRepository.findAll().size();

        // Update the customerOrder
        CustomerOrder updatedCustomerOrder = customerOrderRepository.findById(customerOrder.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerOrder are not directly saved in db
        em.detach(updatedCustomerOrder);
        updatedCustomerOrder
            .date(UPDATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .status(UPDATED_STATUS)
            .price(UPDATED_PRICE)
            .userOpinion(UPDATED_USER_OPINION)
            .userComment(UPDATED_USER_COMMENT)
            .deliveryManComment(UPDATED_DELIVERY_MAN_COMMENT)
            .loyaltyPoints(UPDATED_LOYALTY_POINTS);

        restCustomerOrderMockMvc.perform(put("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerOrder)))
            .andExpect(status().isOk());

        // Validate the CustomerOrder in the database
        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeUpdate);
        CustomerOrder testCustomerOrder = customerOrderList.get(customerOrderList.size() - 1);
        assertThat(testCustomerOrder.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCustomerOrder.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testCustomerOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomerOrder.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testCustomerOrder.getUserOpinion()).isEqualTo(UPDATED_USER_OPINION);
        assertThat(testCustomerOrder.getUserComment()).isEqualTo(UPDATED_USER_COMMENT);
        assertThat(testCustomerOrder.getDeliveryManComment()).isEqualTo(UPDATED_DELIVERY_MAN_COMMENT);
        assertThat(testCustomerOrder.getLoyaltyPoints()).isEqualTo(UPDATED_LOYALTY_POINTS);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerOrder() throws Exception {
        int databaseSizeBeforeUpdate = customerOrderRepository.findAll().size();

        // Create the CustomerOrder

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerOrderMockMvc.perform(put("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerOrder in the database
        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerOrder() throws Exception {
        // Initialize the database
        customerOrderRepository.saveAndFlush(customerOrder);

        int databaseSizeBeforeDelete = customerOrderRepository.findAll().size();

        // Get the customerOrder
        restCustomerOrderMockMvc.perform(delete("/api/customer-orders/{id}", customerOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerOrder.class);
        CustomerOrder customerOrder1 = new CustomerOrder();
        customerOrder1.setId(1L);
        CustomerOrder customerOrder2 = new CustomerOrder();
        customerOrder2.setId(customerOrder1.getId());
        assertThat(customerOrder1).isEqualTo(customerOrder2);
        customerOrder2.setId(2L);
        assertThat(customerOrder1).isNotEqualTo(customerOrder2);
        customerOrder1.setId(null);
        assertThat(customerOrder1).isNotEqualTo(customerOrder2);
    }
}
