package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyBackendv01App;

import com.propsy.backend.domain.RestaurantWorker;
import com.propsy.backend.repository.RestaurantWorkerRepository;
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
import java.util.ArrayList;
import java.util.List;


import static com.propsy.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RestaurantWorkerResource REST controller.
 *
 * @see RestaurantWorkerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyBackendv01App.class)
public class RestaurantWorkerResourceIntTest {

    private static final String DEFAULT_WORKER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WORKER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WORKER_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_WORKER_SURNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_WORKING_STATUS = 1;
    private static final Integer UPDATED_WORKING_STATUS = 2;

    @Autowired
    private RestaurantWorkerRepository restaurantWorkerRepository;

    @Mock
    private RestaurantWorkerRepository restaurantWorkerRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRestaurantWorkerMockMvc;

    private RestaurantWorker restaurantWorker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RestaurantWorkerResource restaurantWorkerResource = new RestaurantWorkerResource(restaurantWorkerRepository);
        this.restRestaurantWorkerMockMvc = MockMvcBuilders.standaloneSetup(restaurantWorkerResource)
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
    public static RestaurantWorker createEntity(EntityManager em) {
        RestaurantWorker restaurantWorker = new RestaurantWorker()
            .workerName(DEFAULT_WORKER_NAME)
            .workerSurname(DEFAULT_WORKER_SURNAME)
            .workingStatus(DEFAULT_WORKING_STATUS);
        return restaurantWorker;
    }

    @Before
    public void initTest() {
        restaurantWorker = createEntity(em);
    }

    @Test
    @Transactional
    public void createRestaurantWorker() throws Exception {
        int databaseSizeBeforeCreate = restaurantWorkerRepository.findAll().size();

        // Create the RestaurantWorker
        restRestaurantWorkerMockMvc.perform(post("/api/restaurant-workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantWorker)))
            .andExpect(status().isCreated());

        // Validate the RestaurantWorker in the database
        List<RestaurantWorker> restaurantWorkerList = restaurantWorkerRepository.findAll();
        assertThat(restaurantWorkerList).hasSize(databaseSizeBeforeCreate + 1);
        RestaurantWorker testRestaurantWorker = restaurantWorkerList.get(restaurantWorkerList.size() - 1);
        assertThat(testRestaurantWorker.getWorkerName()).isEqualTo(DEFAULT_WORKER_NAME);
        assertThat(testRestaurantWorker.getWorkerSurname()).isEqualTo(DEFAULT_WORKER_SURNAME);
        assertThat(testRestaurantWorker.getWorkingStatus()).isEqualTo(DEFAULT_WORKING_STATUS);
    }

    @Test
    @Transactional
    public void createRestaurantWorkerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = restaurantWorkerRepository.findAll().size();

        // Create the RestaurantWorker with an existing ID
        restaurantWorker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestaurantWorkerMockMvc.perform(post("/api/restaurant-workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantWorker)))
            .andExpect(status().isBadRequest());

        // Validate the RestaurantWorker in the database
        List<RestaurantWorker> restaurantWorkerList = restaurantWorkerRepository.findAll();
        assertThat(restaurantWorkerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkWorkerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantWorkerRepository.findAll().size();
        // set the field null
        restaurantWorker.setWorkerName(null);

        // Create the RestaurantWorker, which fails.

        restRestaurantWorkerMockMvc.perform(post("/api/restaurant-workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantWorker)))
            .andExpect(status().isBadRequest());

        List<RestaurantWorker> restaurantWorkerList = restaurantWorkerRepository.findAll();
        assertThat(restaurantWorkerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWorkerSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantWorkerRepository.findAll().size();
        // set the field null
        restaurantWorker.setWorkerSurname(null);

        // Create the RestaurantWorker, which fails.

        restRestaurantWorkerMockMvc.perform(post("/api/restaurant-workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantWorker)))
            .andExpect(status().isBadRequest());

        List<RestaurantWorker> restaurantWorkerList = restaurantWorkerRepository.findAll();
        assertThat(restaurantWorkerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRestaurantWorkers() throws Exception {
        // Initialize the database
        restaurantWorkerRepository.saveAndFlush(restaurantWorker);

        // Get all the restaurantWorkerList
        restRestaurantWorkerMockMvc.perform(get("/api/restaurant-workers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurantWorker.getId().intValue())))
            .andExpect(jsonPath("$.[*].workerName").value(hasItem(DEFAULT_WORKER_NAME.toString())))
            .andExpect(jsonPath("$.[*].workerSurname").value(hasItem(DEFAULT_WORKER_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].workingStatus").value(hasItem(DEFAULT_WORKING_STATUS)));
    }
    
    public void getAllRestaurantWorkersWithEagerRelationshipsIsEnabled() throws Exception {
        RestaurantWorkerResource restaurantWorkerResource = new RestaurantWorkerResource(restaurantWorkerRepositoryMock);
        when(restaurantWorkerRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRestaurantWorkerMockMvc = MockMvcBuilders.standaloneSetup(restaurantWorkerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRestaurantWorkerMockMvc.perform(get("/api/restaurant-workers?eagerload=true"))
        .andExpect(status().isOk());

        verify(restaurantWorkerRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllRestaurantWorkersWithEagerRelationshipsIsNotEnabled() throws Exception {
        RestaurantWorkerResource restaurantWorkerResource = new RestaurantWorkerResource(restaurantWorkerRepositoryMock);
            when(restaurantWorkerRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRestaurantWorkerMockMvc = MockMvcBuilders.standaloneSetup(restaurantWorkerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRestaurantWorkerMockMvc.perform(get("/api/restaurant-workers?eagerload=true"))
        .andExpect(status().isOk());

            verify(restaurantWorkerRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRestaurantWorker() throws Exception {
        // Initialize the database
        restaurantWorkerRepository.saveAndFlush(restaurantWorker);

        // Get the restaurantWorker
        restRestaurantWorkerMockMvc.perform(get("/api/restaurant-workers/{id}", restaurantWorker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(restaurantWorker.getId().intValue()))
            .andExpect(jsonPath("$.workerName").value(DEFAULT_WORKER_NAME.toString()))
            .andExpect(jsonPath("$.workerSurname").value(DEFAULT_WORKER_SURNAME.toString()))
            .andExpect(jsonPath("$.workingStatus").value(DEFAULT_WORKING_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingRestaurantWorker() throws Exception {
        // Get the restaurantWorker
        restRestaurantWorkerMockMvc.perform(get("/api/restaurant-workers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRestaurantWorker() throws Exception {
        // Initialize the database
        restaurantWorkerRepository.saveAndFlush(restaurantWorker);

        int databaseSizeBeforeUpdate = restaurantWorkerRepository.findAll().size();

        // Update the restaurantWorker
        RestaurantWorker updatedRestaurantWorker = restaurantWorkerRepository.findById(restaurantWorker.getId()).get();
        // Disconnect from session so that the updates on updatedRestaurantWorker are not directly saved in db
        em.detach(updatedRestaurantWorker);
        updatedRestaurantWorker
            .workerName(UPDATED_WORKER_NAME)
            .workerSurname(UPDATED_WORKER_SURNAME)
            .workingStatus(UPDATED_WORKING_STATUS);

        restRestaurantWorkerMockMvc.perform(put("/api/restaurant-workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRestaurantWorker)))
            .andExpect(status().isOk());

        // Validate the RestaurantWorker in the database
        List<RestaurantWorker> restaurantWorkerList = restaurantWorkerRepository.findAll();
        assertThat(restaurantWorkerList).hasSize(databaseSizeBeforeUpdate);
        RestaurantWorker testRestaurantWorker = restaurantWorkerList.get(restaurantWorkerList.size() - 1);
        assertThat(testRestaurantWorker.getWorkerName()).isEqualTo(UPDATED_WORKER_NAME);
        assertThat(testRestaurantWorker.getWorkerSurname()).isEqualTo(UPDATED_WORKER_SURNAME);
        assertThat(testRestaurantWorker.getWorkingStatus()).isEqualTo(UPDATED_WORKING_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRestaurantWorker() throws Exception {
        int databaseSizeBeforeUpdate = restaurantWorkerRepository.findAll().size();

        // Create the RestaurantWorker

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestaurantWorkerMockMvc.perform(put("/api/restaurant-workers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantWorker)))
            .andExpect(status().isBadRequest());

        // Validate the RestaurantWorker in the database
        List<RestaurantWorker> restaurantWorkerList = restaurantWorkerRepository.findAll();
        assertThat(restaurantWorkerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRestaurantWorker() throws Exception {
        // Initialize the database
        restaurantWorkerRepository.saveAndFlush(restaurantWorker);

        int databaseSizeBeforeDelete = restaurantWorkerRepository.findAll().size();

        // Get the restaurantWorker
        restRestaurantWorkerMockMvc.perform(delete("/api/restaurant-workers/{id}", restaurantWorker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RestaurantWorker> restaurantWorkerList = restaurantWorkerRepository.findAll();
        assertThat(restaurantWorkerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestaurantWorker.class);
        RestaurantWorker restaurantWorker1 = new RestaurantWorker();
        restaurantWorker1.setId(1L);
        RestaurantWorker restaurantWorker2 = new RestaurantWorker();
        restaurantWorker2.setId(restaurantWorker1.getId());
        assertThat(restaurantWorker1).isEqualTo(restaurantWorker2);
        restaurantWorker2.setId(2L);
        assertThat(restaurantWorker1).isNotEqualTo(restaurantWorker2);
        restaurantWorker1.setId(null);
        assertThat(restaurantWorker1).isNotEqualTo(restaurantWorker2);
    }
}
