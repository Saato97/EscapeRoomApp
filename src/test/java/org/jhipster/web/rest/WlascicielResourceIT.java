package org.jhipster.web.rest;

import org.jhipster.EscapeRoomApp;
import org.jhipster.domain.Wlasciciel;
import org.jhipster.domain.Osoba;
import org.jhipster.repository.WlascicielRepository;
import org.jhipster.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.jhipster.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link WlascicielResource} REST controller.
 */
@SpringBootTest(classes = EscapeRoomApp.class)
public class WlascicielResourceIT {

    @Autowired
    private WlascicielRepository wlascicielRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restWlascicielMockMvc;

    private Wlasciciel wlasciciel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WlascicielResource wlascicielResource = new WlascicielResource(wlascicielRepository);
        this.restWlascicielMockMvc = MockMvcBuilders.standaloneSetup(wlascicielResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wlasciciel createEntity(EntityManager em) {
        Wlasciciel wlasciciel = new Wlasciciel();
        // Add required entity
        Osoba osoba = OsobaResourceIT.createEntity(em);
        em.persist(osoba);
        em.flush();
        wlasciciel.setOsoba(osoba);
        return wlasciciel;
    }

    @BeforeEach
    public void initTest() {
        wlasciciel = createEntity(em);
    }

    @Test
    @Transactional
    public void createWlasciciel() throws Exception {
        int databaseSizeBeforeCreate = wlascicielRepository.findAll().size();

        // Create the Wlasciciel
        restWlascicielMockMvc.perform(post("/api/wlasciciels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wlasciciel)))
            .andExpect(status().isCreated());

        // Validate the Wlasciciel in the database
        List<Wlasciciel> wlascicielList = wlascicielRepository.findAll();
        assertThat(wlascicielList).hasSize(databaseSizeBeforeCreate + 1);
        Wlasciciel testWlasciciel = wlascicielList.get(wlascicielList.size() - 1);
    }

    @Test
    @Transactional
    public void createWlascicielWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wlascicielRepository.findAll().size();

        // Create the Wlasciciel with an existing ID
        wlasciciel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWlascicielMockMvc.perform(post("/api/wlasciciels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wlasciciel)))
            .andExpect(status().isBadRequest());

        // Validate the Wlasciciel in the database
        List<Wlasciciel> wlascicielList = wlascicielRepository.findAll();
        assertThat(wlascicielList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWlasciciels() throws Exception {
        // Initialize the database
        wlascicielRepository.saveAndFlush(wlasciciel);

        // Get all the wlascicielList
        restWlascicielMockMvc.perform(get("/api/wlasciciels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wlasciciel.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getWlasciciel() throws Exception {
        // Initialize the database
        wlascicielRepository.saveAndFlush(wlasciciel);

        // Get the wlasciciel
        restWlascicielMockMvc.perform(get("/api/wlasciciels/{id}", wlasciciel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wlasciciel.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWlasciciel() throws Exception {
        // Get the wlasciciel
        restWlascicielMockMvc.perform(get("/api/wlasciciels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWlasciciel() throws Exception {
        // Initialize the database
        wlascicielRepository.saveAndFlush(wlasciciel);

        int databaseSizeBeforeUpdate = wlascicielRepository.findAll().size();

        // Update the wlasciciel
        Wlasciciel updatedWlasciciel = wlascicielRepository.findById(wlasciciel.getId()).get();
        // Disconnect from session so that the updates on updatedWlasciciel are not directly saved in db
        em.detach(updatedWlasciciel);

        restWlascicielMockMvc.perform(put("/api/wlasciciels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWlasciciel)))
            .andExpect(status().isOk());

        // Validate the Wlasciciel in the database
        List<Wlasciciel> wlascicielList = wlascicielRepository.findAll();
        assertThat(wlascicielList).hasSize(databaseSizeBeforeUpdate);
        Wlasciciel testWlasciciel = wlascicielList.get(wlascicielList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingWlasciciel() throws Exception {
        int databaseSizeBeforeUpdate = wlascicielRepository.findAll().size();

        // Create the Wlasciciel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWlascicielMockMvc.perform(put("/api/wlasciciels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wlasciciel)))
            .andExpect(status().isBadRequest());

        // Validate the Wlasciciel in the database
        List<Wlasciciel> wlascicielList = wlascicielRepository.findAll();
        assertThat(wlascicielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWlasciciel() throws Exception {
        // Initialize the database
        wlascicielRepository.saveAndFlush(wlasciciel);

        int databaseSizeBeforeDelete = wlascicielRepository.findAll().size();

        // Delete the wlasciciel
        restWlascicielMockMvc.perform(delete("/api/wlasciciels/{id}", wlasciciel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Wlasciciel> wlascicielList = wlascicielRepository.findAll();
        assertThat(wlascicielList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wlasciciel.class);
        Wlasciciel wlasciciel1 = new Wlasciciel();
        wlasciciel1.setId(1L);
        Wlasciciel wlasciciel2 = new Wlasciciel();
        wlasciciel2.setId(wlasciciel1.getId());
        assertThat(wlasciciel1).isEqualTo(wlasciciel2);
        wlasciciel2.setId(2L);
        assertThat(wlasciciel1).isNotEqualTo(wlasciciel2);
        wlasciciel1.setId(null);
        assertThat(wlasciciel1).isNotEqualTo(wlasciciel2);
    }
}
