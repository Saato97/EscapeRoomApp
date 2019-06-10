package org.jhipster.web.rest;

import org.jhipster.EscapeRoomApp;
import org.jhipster.domain.Osoba;
import org.jhipster.repository.OsobaRepository;
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
 * Integration tests for the {@Link OsobaResource} REST controller.
 */
@SpringBootTest(classes = EscapeRoomApp.class)
public class OsobaResourceIT {

    private static final String DEFAULT_IMIE = "AAAAAAAAAA";
    private static final String UPDATED_IMIE = "BBBBBBBBBB";

    private static final String DEFAULT_NAZWISKO = "AAAAAAAAAA";
    private static final String UPDATED_NAZWISKO = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_HASLO = "AAAAAAAAAA";
    private static final String UPDATED_HASLO = "BBBBBBBBBB";

    @Autowired
    private OsobaRepository osobaRepository;

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

    private MockMvc restOsobaMockMvc;

    private Osoba osoba;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OsobaResource osobaResource = new OsobaResource(osobaRepository);
        this.restOsobaMockMvc = MockMvcBuilders.standaloneSetup(osobaResource)
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
    public static Osoba createEntity(EntityManager em) {
        Osoba osoba = new Osoba()
            .imie(DEFAULT_IMIE)
            .nazwisko(DEFAULT_NAZWISKO)
            .login(DEFAULT_LOGIN)
            .haslo(DEFAULT_HASLO);
        return osoba;
    }

    @BeforeEach
    public void initTest() {
        osoba = createEntity(em);
    }

    @Test
    @Transactional
    public void createOsoba() throws Exception {
        int databaseSizeBeforeCreate = osobaRepository.findAll().size();

        // Create the Osoba
        restOsobaMockMvc.perform(post("/api/osobas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(osoba)))
            .andExpect(status().isCreated());

        // Validate the Osoba in the database
        List<Osoba> osobaList = osobaRepository.findAll();
        assertThat(osobaList).hasSize(databaseSizeBeforeCreate + 1);
        Osoba testOsoba = osobaList.get(osobaList.size() - 1);
        assertThat(testOsoba.getImie()).isEqualTo(DEFAULT_IMIE);
        assertThat(testOsoba.getNazwisko()).isEqualTo(DEFAULT_NAZWISKO);
        assertThat(testOsoba.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testOsoba.getHaslo()).isEqualTo(DEFAULT_HASLO);
    }

    @Test
    @Transactional
    public void createOsobaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = osobaRepository.findAll().size();

        // Create the Osoba with an existing ID
        osoba.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOsobaMockMvc.perform(post("/api/osobas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(osoba)))
            .andExpect(status().isBadRequest());

        // Validate the Osoba in the database
        List<Osoba> osobaList = osobaRepository.findAll();
        assertThat(osobaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = osobaRepository.findAll().size();
        // set the field null
        osoba.setLogin(null);

        // Create the Osoba, which fails.

        restOsobaMockMvc.perform(post("/api/osobas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(osoba)))
            .andExpect(status().isBadRequest());

        List<Osoba> osobaList = osobaRepository.findAll();
        assertThat(osobaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHasloIsRequired() throws Exception {
        int databaseSizeBeforeTest = osobaRepository.findAll().size();
        // set the field null
        osoba.setHaslo(null);

        // Create the Osoba, which fails.

        restOsobaMockMvc.perform(post("/api/osobas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(osoba)))
            .andExpect(status().isBadRequest());

        List<Osoba> osobaList = osobaRepository.findAll();
        assertThat(osobaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOsobas() throws Exception {
        // Initialize the database
        osobaRepository.saveAndFlush(osoba);

        // Get all the osobaList
        restOsobaMockMvc.perform(get("/api/osobas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(osoba.getId().intValue())))
            .andExpect(jsonPath("$.[*].imie").value(hasItem(DEFAULT_IMIE.toString())))
            .andExpect(jsonPath("$.[*].nazwisko").value(hasItem(DEFAULT_NAZWISKO.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].haslo").value(hasItem(DEFAULT_HASLO.toString())));
    }
    
    @Test
    @Transactional
    public void getOsoba() throws Exception {
        // Initialize the database
        osobaRepository.saveAndFlush(osoba);

        // Get the osoba
        restOsobaMockMvc.perform(get("/api/osobas/{id}", osoba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(osoba.getId().intValue()))
            .andExpect(jsonPath("$.imie").value(DEFAULT_IMIE.toString()))
            .andExpect(jsonPath("$.nazwisko").value(DEFAULT_NAZWISKO.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.haslo").value(DEFAULT_HASLO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOsoba() throws Exception {
        // Get the osoba
        restOsobaMockMvc.perform(get("/api/osobas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOsoba() throws Exception {
        // Initialize the database
        osobaRepository.saveAndFlush(osoba);

        int databaseSizeBeforeUpdate = osobaRepository.findAll().size();

        // Update the osoba
        Osoba updatedOsoba = osobaRepository.findById(osoba.getId()).get();
        // Disconnect from session so that the updates on updatedOsoba are not directly saved in db
        em.detach(updatedOsoba);
        updatedOsoba
            .imie(UPDATED_IMIE)
            .nazwisko(UPDATED_NAZWISKO)
            .login(UPDATED_LOGIN)
            .haslo(UPDATED_HASLO);

        restOsobaMockMvc.perform(put("/api/osobas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOsoba)))
            .andExpect(status().isOk());

        // Validate the Osoba in the database
        List<Osoba> osobaList = osobaRepository.findAll();
        assertThat(osobaList).hasSize(databaseSizeBeforeUpdate);
        Osoba testOsoba = osobaList.get(osobaList.size() - 1);
        assertThat(testOsoba.getImie()).isEqualTo(UPDATED_IMIE);
        assertThat(testOsoba.getNazwisko()).isEqualTo(UPDATED_NAZWISKO);
        assertThat(testOsoba.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testOsoba.getHaslo()).isEqualTo(UPDATED_HASLO);
    }

    @Test
    @Transactional
    public void updateNonExistingOsoba() throws Exception {
        int databaseSizeBeforeUpdate = osobaRepository.findAll().size();

        // Create the Osoba

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOsobaMockMvc.perform(put("/api/osobas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(osoba)))
            .andExpect(status().isBadRequest());

        // Validate the Osoba in the database
        List<Osoba> osobaList = osobaRepository.findAll();
        assertThat(osobaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOsoba() throws Exception {
        // Initialize the database
        osobaRepository.saveAndFlush(osoba);

        int databaseSizeBeforeDelete = osobaRepository.findAll().size();

        // Delete the osoba
        restOsobaMockMvc.perform(delete("/api/osobas/{id}", osoba.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Osoba> osobaList = osobaRepository.findAll();
        assertThat(osobaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Osoba.class);
        Osoba osoba1 = new Osoba();
        osoba1.setId(1L);
        Osoba osoba2 = new Osoba();
        osoba2.setId(osoba1.getId());
        assertThat(osoba1).isEqualTo(osoba2);
        osoba2.setId(2L);
        assertThat(osoba1).isNotEqualTo(osoba2);
        osoba1.setId(null);
        assertThat(osoba1).isNotEqualTo(osoba2);
    }
}
