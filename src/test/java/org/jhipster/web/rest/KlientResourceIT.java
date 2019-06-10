package org.jhipster.web.rest;

import org.jhipster.EscapeRoomApp;
import org.jhipster.domain.Klient;
import org.jhipster.domain.Osoba;
import org.jhipster.repository.KlientRepository;
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
 * Integration tests for the {@Link KlientResource} REST controller.
 */
@SpringBootTest(classes = EscapeRoomApp.class)
public class KlientResourceIT {

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private KlientRepository klientRepository;

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

    private MockMvc restKlientMockMvc;

    private Klient klient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KlientResource klientResource = new KlientResource(klientRepository);
        this.restKlientMockMvc = MockMvcBuilders.standaloneSetup(klientResource)
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
    public static Klient createEntity(EntityManager em) {
        Klient klient = new Klient()
            .telefon(DEFAULT_TELEFON)
            .email(DEFAULT_EMAIL);
        // Add required entity
        Osoba osoba = OsobaResourceIT.createEntity(em);
        em.persist(osoba);
        em.flush();
        klient.setOsoba(osoba);
        return klient;
    }

    @BeforeEach
    public void initTest() {
        klient = createEntity(em);
    }

    @Test
    @Transactional
    public void createKlient() throws Exception {
        int databaseSizeBeforeCreate = klientRepository.findAll().size();

        // Create the Klient
        restKlientMockMvc.perform(post("/api/klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klient)))
            .andExpect(status().isCreated());

        // Validate the Klient in the database
        List<Klient> klientList = klientRepository.findAll();
        assertThat(klientList).hasSize(databaseSizeBeforeCreate + 1);
        Klient testKlient = klientList.get(klientList.size() - 1);
        assertThat(testKlient.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testKlient.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createKlientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = klientRepository.findAll().size();

        // Create the Klient with an existing ID
        klient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKlientMockMvc.perform(post("/api/klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klient)))
            .andExpect(status().isBadRequest());

        // Validate the Klient in the database
        List<Klient> klientList = klientRepository.findAll();
        assertThat(klientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllKlients() throws Exception {
        // Initialize the database
        klientRepository.saveAndFlush(klient);

        // Get all the klientList
        restKlientMockMvc.perform(get("/api/klients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(klient.getId().intValue())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getKlient() throws Exception {
        // Initialize the database
        klientRepository.saveAndFlush(klient);

        // Get the klient
        restKlientMockMvc.perform(get("/api/klients/{id}", klient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(klient.getId().intValue()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKlient() throws Exception {
        // Get the klient
        restKlientMockMvc.perform(get("/api/klients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKlient() throws Exception {
        // Initialize the database
        klientRepository.saveAndFlush(klient);

        int databaseSizeBeforeUpdate = klientRepository.findAll().size();

        // Update the klient
        Klient updatedKlient = klientRepository.findById(klient.getId()).get();
        // Disconnect from session so that the updates on updatedKlient are not directly saved in db
        em.detach(updatedKlient);
        updatedKlient
            .telefon(UPDATED_TELEFON)
            .email(UPDATED_EMAIL);

        restKlientMockMvc.perform(put("/api/klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKlient)))
            .andExpect(status().isOk());

        // Validate the Klient in the database
        List<Klient> klientList = klientRepository.findAll();
        assertThat(klientList).hasSize(databaseSizeBeforeUpdate);
        Klient testKlient = klientList.get(klientList.size() - 1);
        assertThat(testKlient.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testKlient.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingKlient() throws Exception {
        int databaseSizeBeforeUpdate = klientRepository.findAll().size();

        // Create the Klient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKlientMockMvc.perform(put("/api/klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klient)))
            .andExpect(status().isBadRequest());

        // Validate the Klient in the database
        List<Klient> klientList = klientRepository.findAll();
        assertThat(klientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKlient() throws Exception {
        // Initialize the database
        klientRepository.saveAndFlush(klient);

        int databaseSizeBeforeDelete = klientRepository.findAll().size();

        // Delete the klient
        restKlientMockMvc.perform(delete("/api/klients/{id}", klient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Klient> klientList = klientRepository.findAll();
        assertThat(klientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Klient.class);
        Klient klient1 = new Klient();
        klient1.setId(1L);
        Klient klient2 = new Klient();
        klient2.setId(klient1.getId());
        assertThat(klient1).isEqualTo(klient2);
        klient2.setId(2L);
        assertThat(klient1).isNotEqualTo(klient2);
        klient1.setId(null);
        assertThat(klient1).isNotEqualTo(klient2);
    }
}
