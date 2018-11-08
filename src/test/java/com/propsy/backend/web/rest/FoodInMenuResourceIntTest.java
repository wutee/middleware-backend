package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyTestApp;

import com.propsy.backend.domain.FoodInMenu;
import com.propsy.backend.repository.FoodInMenuRepository;
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
 * Test class for the FoodInMenuResource REST controller.
 *
 * @see FoodInMenuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyTestApp.class)
public class FoodInMenuResourceIntTest {

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    @Autowired
    private FoodInMenuRepository foodInMenuRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFoodInMenuMockMvc;

    private FoodInMenu foodInMenu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoodInMenuResource foodInMenuResource = new FoodInMenuResource(foodInMenuRepository);
        this.restFoodInMenuMockMvc = MockMvcBuilders.standaloneSetup(foodInMenuResource)
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
    public static FoodInMenu createEntity(EntityManager em) {
        FoodInMenu foodInMenu = new FoodInMenu()
            .price(DEFAULT_PRICE);
        return foodInMenu;
    }

    @Before
    public void initTest() {
        foodInMenu = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoodInMenu() throws Exception {
        int databaseSizeBeforeCreate = foodInMenuRepository.findAll().size();

        // Create the FoodInMenu
        restFoodInMenuMockMvc.perform(post("/api/food-in-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodInMenu)))
            .andExpect(status().isCreated());

        // Validate the FoodInMenu in the database
        List<FoodInMenu> foodInMenuList = foodInMenuRepository.findAll();
        assertThat(foodInMenuList).hasSize(databaseSizeBeforeCreate + 1);
        FoodInMenu testFoodInMenu = foodInMenuList.get(foodInMenuList.size() - 1);
        assertThat(testFoodInMenu.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createFoodInMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodInMenuRepository.findAll().size();

        // Create the FoodInMenu with an existing ID
        foodInMenu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodInMenuMockMvc.perform(post("/api/food-in-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodInMenu)))
            .andExpect(status().isBadRequest());

        // Validate the FoodInMenu in the database
        List<FoodInMenu> foodInMenuList = foodInMenuRepository.findAll();
        assertThat(foodInMenuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodInMenuRepository.findAll().size();
        // set the field null
        foodInMenu.setPrice(null);

        // Create the FoodInMenu, which fails.

        restFoodInMenuMockMvc.perform(post("/api/food-in-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodInMenu)))
            .andExpect(status().isBadRequest());

        List<FoodInMenu> foodInMenuList = foodInMenuRepository.findAll();
        assertThat(foodInMenuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoodInMenus() throws Exception {
        // Initialize the database
        foodInMenuRepository.saveAndFlush(foodInMenu);

        // Get all the foodInMenuList
        restFoodInMenuMockMvc.perform(get("/api/food-in-menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodInMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getFoodInMenu() throws Exception {
        // Initialize the database
        foodInMenuRepository.saveAndFlush(foodInMenu);

        // Get the foodInMenu
        restFoodInMenuMockMvc.perform(get("/api/food-in-menus/{id}", foodInMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foodInMenu.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFoodInMenu() throws Exception {
        // Get the foodInMenu
        restFoodInMenuMockMvc.perform(get("/api/food-in-menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoodInMenu() throws Exception {
        // Initialize the database
        foodInMenuRepository.saveAndFlush(foodInMenu);

        int databaseSizeBeforeUpdate = foodInMenuRepository.findAll().size();

        // Update the foodInMenu
        FoodInMenu updatedFoodInMenu = foodInMenuRepository.findById(foodInMenu.getId()).get();
        // Disconnect from session so that the updates on updatedFoodInMenu are not directly saved in db
        em.detach(updatedFoodInMenu);
        updatedFoodInMenu
            .price(UPDATED_PRICE);

        restFoodInMenuMockMvc.perform(put("/api/food-in-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFoodInMenu)))
            .andExpect(status().isOk());

        // Validate the FoodInMenu in the database
        List<FoodInMenu> foodInMenuList = foodInMenuRepository.findAll();
        assertThat(foodInMenuList).hasSize(databaseSizeBeforeUpdate);
        FoodInMenu testFoodInMenu = foodInMenuList.get(foodInMenuList.size() - 1);
        assertThat(testFoodInMenu.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingFoodInMenu() throws Exception {
        int databaseSizeBeforeUpdate = foodInMenuRepository.findAll().size();

        // Create the FoodInMenu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodInMenuMockMvc.perform(put("/api/food-in-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodInMenu)))
            .andExpect(status().isBadRequest());

        // Validate the FoodInMenu in the database
        List<FoodInMenu> foodInMenuList = foodInMenuRepository.findAll();
        assertThat(foodInMenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoodInMenu() throws Exception {
        // Initialize the database
        foodInMenuRepository.saveAndFlush(foodInMenu);

        int databaseSizeBeforeDelete = foodInMenuRepository.findAll().size();

        // Get the foodInMenu
        restFoodInMenuMockMvc.perform(delete("/api/food-in-menus/{id}", foodInMenu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FoodInMenu> foodInMenuList = foodInMenuRepository.findAll();
        assertThat(foodInMenuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodInMenu.class);
        FoodInMenu foodInMenu1 = new FoodInMenu();
        foodInMenu1.setId(1L);
        FoodInMenu foodInMenu2 = new FoodInMenu();
        foodInMenu2.setId(foodInMenu1.getId());
        assertThat(foodInMenu1).isEqualTo(foodInMenu2);
        foodInMenu2.setId(2L);
        assertThat(foodInMenu1).isNotEqualTo(foodInMenu2);
        foodInMenu1.setId(null);
        assertThat(foodInMenu1).isNotEqualTo(foodInMenu2);
    }
}
