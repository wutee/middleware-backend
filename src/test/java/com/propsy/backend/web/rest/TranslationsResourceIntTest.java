package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyTestApp;

import com.propsy.backend.domain.Translations;
import com.propsy.backend.repository.TranslationsRepository;
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
 * Test class for the TranslationsResource REST controller.
 *
 * @see TranslationsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyTestApp.class)
public class TranslationsResourceIntTest {

    private static final String DEFAULT_TRANSLATION = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATION = "BBBBBBBBBB";

    @Autowired
    private TranslationsRepository translationsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTranslationsMockMvc;

    private Translations translations;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TranslationsResource translationsResource = new TranslationsResource(translationsRepository);
        this.restTranslationsMockMvc = MockMvcBuilders.standaloneSetup(translationsResource)
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
    public static Translations createEntity(EntityManager em) {
        Translations translations = new Translations()
            .translation(DEFAULT_TRANSLATION);
        return translations;
    }

    @Before
    public void initTest() {
        translations = createEntity(em);
    }

    @Test
    @Transactional
    public void createTranslations() throws Exception {
        int databaseSizeBeforeCreate = translationsRepository.findAll().size();

        // Create the Translations
        restTranslationsMockMvc.perform(post("/api/translations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translations)))
            .andExpect(status().isCreated());

        // Validate the Translations in the database
        List<Translations> translationsList = translationsRepository.findAll();
        assertThat(translationsList).hasSize(databaseSizeBeforeCreate + 1);
        Translations testTranslations = translationsList.get(translationsList.size() - 1);
        assertThat(testTranslations.getTranslation()).isEqualTo(DEFAULT_TRANSLATION);
    }

    @Test
    @Transactional
    public void createTranslationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = translationsRepository.findAll().size();

        // Create the Translations with an existing ID
        translations.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTranslationsMockMvc.perform(post("/api/translations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translations)))
            .andExpect(status().isBadRequest());

        // Validate the Translations in the database
        List<Translations> translationsList = translationsRepository.findAll();
        assertThat(translationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTranslations() throws Exception {
        // Initialize the database
        translationsRepository.saveAndFlush(translations);

        // Get all the translationsList
        restTranslationsMockMvc.perform(get("/api/translations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(translations.getId().intValue())))
            .andExpect(jsonPath("$.[*].translation").value(hasItem(DEFAULT_TRANSLATION.toString())));
    }
    
    @Test
    @Transactional
    public void getTranslations() throws Exception {
        // Initialize the database
        translationsRepository.saveAndFlush(translations);

        // Get the translations
        restTranslationsMockMvc.perform(get("/api/translations/{id}", translations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(translations.getId().intValue()))
            .andExpect(jsonPath("$.translation").value(DEFAULT_TRANSLATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTranslations() throws Exception {
        // Get the translations
        restTranslationsMockMvc.perform(get("/api/translations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTranslations() throws Exception {
        // Initialize the database
        translationsRepository.saveAndFlush(translations);

        int databaseSizeBeforeUpdate = translationsRepository.findAll().size();

        // Update the translations
        Translations updatedTranslations = translationsRepository.findById(translations.getId()).get();
        // Disconnect from session so that the updates on updatedTranslations are not directly saved in db
        em.detach(updatedTranslations);
        updatedTranslations
            .translation(UPDATED_TRANSLATION);

        restTranslationsMockMvc.perform(put("/api/translations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTranslations)))
            .andExpect(status().isOk());

        // Validate the Translations in the database
        List<Translations> translationsList = translationsRepository.findAll();
        assertThat(translationsList).hasSize(databaseSizeBeforeUpdate);
        Translations testTranslations = translationsList.get(translationsList.size() - 1);
        assertThat(testTranslations.getTranslation()).isEqualTo(UPDATED_TRANSLATION);
    }

    @Test
    @Transactional
    public void updateNonExistingTranslations() throws Exception {
        int databaseSizeBeforeUpdate = translationsRepository.findAll().size();

        // Create the Translations

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranslationsMockMvc.perform(put("/api/translations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translations)))
            .andExpect(status().isBadRequest());

        // Validate the Translations in the database
        List<Translations> translationsList = translationsRepository.findAll();
        assertThat(translationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTranslations() throws Exception {
        // Initialize the database
        translationsRepository.saveAndFlush(translations);

        int databaseSizeBeforeDelete = translationsRepository.findAll().size();

        // Get the translations
        restTranslationsMockMvc.perform(delete("/api/translations/{id}", translations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Translations> translationsList = translationsRepository.findAll();
        assertThat(translationsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Translations.class);
        Translations translations1 = new Translations();
        translations1.setId(1L);
        Translations translations2 = new Translations();
        translations2.setId(translations1.getId());
        assertThat(translations1).isEqualTo(translations2);
        translations2.setId(2L);
        assertThat(translations1).isNotEqualTo(translations2);
        translations1.setId(null);
        assertThat(translations1).isNotEqualTo(translations2);
    }
}
