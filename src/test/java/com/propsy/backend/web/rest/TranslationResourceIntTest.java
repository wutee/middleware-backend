package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyBackendv01App;

import com.propsy.backend.domain.Translation;
import com.propsy.backend.repository.TranslationRepository;
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
 * Test class for the TranslationResource REST controller.
 *
 * @see TranslationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyBackendv01App.class)
public class TranslationResourceIntTest {

    private static final String DEFAULT_TRANSLATION = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATION = "BBBBBBBBBB";

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTranslationMockMvc;

    private Translation translation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TranslationResource translationResource = new TranslationResource(translationRepository);
        this.restTranslationMockMvc = MockMvcBuilders.standaloneSetup(translationResource)
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
    public static Translation createEntity(EntityManager em) {
        Translation translation = new Translation()
            .translation(DEFAULT_TRANSLATION);
        return translation;
    }

    @Before
    public void initTest() {
        translation = createEntity(em);
    }

    @Test
    @Transactional
    public void createTranslation() throws Exception {
        int databaseSizeBeforeCreate = translationRepository.findAll().size();

        // Create the Translation
        restTranslationMockMvc.perform(post("/api/translations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translation)))
            .andExpect(status().isCreated());

        // Validate the Translation in the database
        List<Translation> translationList = translationRepository.findAll();
        assertThat(translationList).hasSize(databaseSizeBeforeCreate + 1);
        Translation testTranslation = translationList.get(translationList.size() - 1);
        assertThat(testTranslation.getTranslation()).isEqualTo(DEFAULT_TRANSLATION);
    }

    @Test
    @Transactional
    public void createTranslationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = translationRepository.findAll().size();

        // Create the Translation with an existing ID
        translation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTranslationMockMvc.perform(post("/api/translations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translation)))
            .andExpect(status().isBadRequest());

        // Validate the Translation in the database
        List<Translation> translationList = translationRepository.findAll();
        assertThat(translationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTranslations() throws Exception {
        // Initialize the database
        translationRepository.saveAndFlush(translation);

        // Get all the translationList
        restTranslationMockMvc.perform(get("/api/translations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(translation.getId().intValue())))
            .andExpect(jsonPath("$.[*].translation").value(hasItem(DEFAULT_TRANSLATION.toString())));
    }
    
    @Test
    @Transactional
    public void getTranslation() throws Exception {
        // Initialize the database
        translationRepository.saveAndFlush(translation);

        // Get the translation
        restTranslationMockMvc.perform(get("/api/translations/{id}", translation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(translation.getId().intValue()))
            .andExpect(jsonPath("$.translation").value(DEFAULT_TRANSLATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTranslation() throws Exception {
        // Get the translation
        restTranslationMockMvc.perform(get("/api/translations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTranslation() throws Exception {
        // Initialize the database
        translationRepository.saveAndFlush(translation);

        int databaseSizeBeforeUpdate = translationRepository.findAll().size();

        // Update the translation
        Translation updatedTranslation = translationRepository.findById(translation.getId()).get();
        // Disconnect from session so that the updates on updatedTranslation are not directly saved in db
        em.detach(updatedTranslation);
        updatedTranslation
            .translation(UPDATED_TRANSLATION);

        restTranslationMockMvc.perform(put("/api/translations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTranslation)))
            .andExpect(status().isOk());

        // Validate the Translation in the database
        List<Translation> translationList = translationRepository.findAll();
        assertThat(translationList).hasSize(databaseSizeBeforeUpdate);
        Translation testTranslation = translationList.get(translationList.size() - 1);
        assertThat(testTranslation.getTranslation()).isEqualTo(UPDATED_TRANSLATION);
    }

    @Test
    @Transactional
    public void updateNonExistingTranslation() throws Exception {
        int databaseSizeBeforeUpdate = translationRepository.findAll().size();

        // Create the Translation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranslationMockMvc.perform(put("/api/translations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translation)))
            .andExpect(status().isBadRequest());

        // Validate the Translation in the database
        List<Translation> translationList = translationRepository.findAll();
        assertThat(translationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTranslation() throws Exception {
        // Initialize the database
        translationRepository.saveAndFlush(translation);

        int databaseSizeBeforeDelete = translationRepository.findAll().size();

        // Get the translation
        restTranslationMockMvc.perform(delete("/api/translations/{id}", translation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Translation> translationList = translationRepository.findAll();
        assertThat(translationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Translation.class);
        Translation translation1 = new Translation();
        translation1.setId(1L);
        Translation translation2 = new Translation();
        translation2.setId(translation1.getId());
        assertThat(translation1).isEqualTo(translation2);
        translation2.setId(2L);
        assertThat(translation1).isNotEqualTo(translation2);
        translation1.setId(null);
        assertThat(translation1).isNotEqualTo(translation2);
    }
}
