package org.jhipster.web.rest;

import org.jhipster.EscapeRoomApp;
import org.jhipster.domain.Wizyty;
import org.jhipster.domain.Klient;
import org.jhipster.repository.WizytyRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.jhipster.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link WizytyResource} REST controller.
 */
@SpringBootTest(classes = EscapeRoomApp.class)
public class WizytyResourceIT {

    private static final LocalDate DEFAULT_DATA_WIZYTY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_WIZYTY = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private WizytyRepository wizytyRepository;

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

    private MockMvc restWizytyMockMvc;

    private Wizyty wizyty;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WizytyResource wizytyResource = new WizytyResource(wizytyRepository);
        this.restWizytyMockMvc = MockMvcBuilders.standaloneSetup(wizytyResource)
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
    public static Wizyty createEntity(EntityManager em) {
        Wizyty wizyty = new Wizyty()
            .dataWizyty(DEFAULT_DATA_WIZYTY);
        // Add required entity
        Klient klient = KlientResourceIT.createEntity(em);
        em.persist(klient);
        em.flush();
        wizyty.setKlient(klient);
        return wizyty;
    }

    @BeforeEach
    public void initTest() {
        wizyty = createEntity(em);
    }

    @Test
    @Transactional
    public void createWizyty() throws Exception {
        int databaseSizeBeforeCreate = wizytyRepository.findAll().size();

        // Create the Wizyty
        restWizytyMockMvc.perform(post("/api/wizyties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wizyty)))
            .andExpect(status().isCreated());

        // Validate the Wizyty in the database
        List<Wizyty> wizytyList = wizytyRepository.findAll();
        assertThat(wizytyList).hasSize(databaseSizeBeforeCreate + 1);
        Wizyty testWizyty = wizytyList.get(wizytyList.size() - 1);
        assertThat(testWizyty.getDataWizyty()).isEqualTo(DEFAULT_DATA_WIZYTY);
    }

    @Test
    @Transactional
    public void createWizytyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wizytyRepository.findAll().size();

        // Create the Wizyty with an existing ID
        wizyty.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWizytyMockMvc.perform(post("/api/wizyties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wizyty)))
            .andExpect(status().isBadRequest());

        // Validate the Wizyty in the database
        List<Wizyty> wizytyList = wizytyRepository.findAll();
        assertThat(wizytyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWizyties() throws Exception {
        // Initialize the database
        wizytyRepository.saveAndFlush(wizyty);

        // Get all the wizytyList
        restWizytyMockMvc.perform(get("/api/wizyties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wizyty.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataWizyty").value(hasItem(DEFAULT_DATA_WIZYTY.toString())));
    }
    
    @Test
    @Transactional
    public void getWizyty() throws Exception {
        // Initialize the database
        wizytyRepository.saveAndFlush(wizyty);

        // Get the wizyty
        restWizytyMockMvc.perform(get("/api/wizyties/{id}", wizyty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wizyty.getId().intValue()))
            .andExpect(jsonPath("$.dataWizyty").value(DEFAULT_DATA_WIZYTY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWizyty() throws Exception {
        // Get the wizyty
        restWizytyMockMvc.perform(get("/api/wizyties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWizyty() throws Exception {
        // Initialize the database
        wizytyRepository.saveAndFlush(wizyty);

        int databaseSizeBeforeUpdate = wizytyRepository.findAll().size();

        // Update the wizyty
        Wizyty updatedWizyty = wizytyRepository.findById(wizyty.getId()).get();
        // Disconnect from session so that the updates on updatedWizyty are not directly saved in db
        em.detach(updatedWizyty);
        updatedWizyty
            .dataWizyty(UPDATED_DATA_WIZYTY);

        restWizytyMockMvc.perform(put("/api/wizyties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWizyty)))
            .andExpect(status().isOk());

        // Validate the Wizyty in the database
        List<Wizyty> wizytyList = wizytyRepository.findAll();
        assertThat(wizytyList).hasSize(databaseSizeBeforeUpdate);
        Wizyty testWizyty = wizytyList.get(wizytyList.size() - 1);
        assertThat(testWizyty.getDataWizyty()).isEqualTo(UPDATED_DATA_WIZYTY);
    }

    @Test
    @Transactional
    public void updateNonExistingWizyty() throws Exception {
        int databaseSizeBeforeUpdate = wizytyRepository.findAll().size();

        // Create the Wizyty

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWizytyMockMvc.perform(put("/api/wizyties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wizyty)))
            .andExpect(status().isBadRequest());

        // Validate the Wizyty in the database
        List<Wizyty> wizytyList = wizytyRepository.findAll();
        assertThat(wizytyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWizyty() throws Exception {
        // Initialize the database
        wizytyRepository.saveAndFlush(wizyty);

        int databaseSizeBeforeDelete = wizytyRepository.findAll().size();

        // Delete the wizyty
        restWizytyMockMvc.perform(delete("/api/wizyties/{id}", wizyty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Wizyty> wizytyList = wizytyRepository.findAll();
        assertThat(wizytyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wizyty.class);
        Wizyty wizyty1 = new Wizyty();
        wizyty1.setId(1L);
        Wizyty wizyty2 = new Wizyty();
        wizyty2.setId(wizyty1.getId());
        assertThat(wizyty1).isEqualTo(wizyty2);
        wizyty2.setId(2L);
        assertThat(wizyty1).isNotEqualTo(wizyty2);
        wizyty1.setId(null);
        assertThat(wizyty1).isNotEqualTo(wizyty2);
    }
}
