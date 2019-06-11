package org.jhipster.web.rest;

import org.jhipster.EscapeRoomApp;
import org.jhipster.domain.Opinie;
import org.jhipster.domain.Wizyty;
import org.jhipster.repository.OpinieRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.jhipster.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link OpinieResource} REST controller.
 */
@SpringBootTest(classes = EscapeRoomApp.class)
public class OpinieResourceIT {

    private static final String DEFAULT_OPINIA = "AAAAAAAAAA";
    private static final String UPDATED_OPINIA = "BBBBBBBBBB";

    @Autowired
    private OpinieRepository opinieRepository;

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

    private MockMvc restOpinieMockMvc;

    private Opinie opinie;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OpinieResource opinieResource = new OpinieResource(opinieRepository);
        this.restOpinieMockMvc = MockMvcBuilders.standaloneSetup(opinieResource)
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
    public static Opinie createEntity(EntityManager em) {
        Opinie opinie = new Opinie()
            .opinia(DEFAULT_OPINIA);
        // Add required entity
        Wizyty wizyty = WizytyResourceIT.createEntity(em);
        em.persist(wizyty);
        em.flush();
        opinie.setWizyty(wizyty);
        return opinie;
    }

    @BeforeEach
    public void initTest() {
        opinie = createEntity(em);
    }

    @Test
    @Transactional
    public void createOpinie() throws Exception {
        int databaseSizeBeforeCreate = opinieRepository.findAll().size();

        // Create the Opinie
        restOpinieMockMvc.perform(post("/api/opinies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opinie)))
            .andExpect(status().isCreated());

        // Validate the Opinie in the database
        List<Opinie> opinieList = opinieRepository.findAll();
        assertThat(opinieList).hasSize(databaseSizeBeforeCreate + 1);
        Opinie testOpinie = opinieList.get(opinieList.size() - 1);
        assertThat(testOpinie.getOpinia()).isEqualTo(DEFAULT_OPINIA);
    }

    @Test
    @Transactional
    public void createOpinieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = opinieRepository.findAll().size();

        // Create the Opinie with an existing ID
        opinie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpinieMockMvc.perform(post("/api/opinies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opinie)))
            .andExpect(status().isBadRequest());

        // Validate the Opinie in the database
        List<Opinie> opinieList = opinieRepository.findAll();
        assertThat(opinieList).hasSize(databaseSizeBeforeCreate);
    }


    
    @Test
    @Transactional
    public void getOpinie() throws Exception {
        // Initialize the database
        opinieRepository.saveAndFlush(opinie);

        // Get the opinie
        restOpinieMockMvc.perform(get("/api/opinies/{id}", opinie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(opinie.getId().intValue()))
            .andExpect(jsonPath("$.opinia").value(DEFAULT_OPINIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOpinie() throws Exception {
        // Get the opinie
        restOpinieMockMvc.perform(get("/api/opinies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOpinie() throws Exception {
        // Initialize the database
        opinieRepository.saveAndFlush(opinie);

        int databaseSizeBeforeUpdate = opinieRepository.findAll().size();

        // Update the opinie
        Opinie updatedOpinie = opinieRepository.findById(opinie.getId()).get();
        // Disconnect from session so that the updates on updatedOpinie are not directly saved in db
        em.detach(updatedOpinie);
        updatedOpinie
            .opinia(UPDATED_OPINIA);

        restOpinieMockMvc.perform(put("/api/opinies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOpinie)))
            .andExpect(status().isOk());

        // Validate the Opinie in the database
        List<Opinie> opinieList = opinieRepository.findAll();
        assertThat(opinieList).hasSize(databaseSizeBeforeUpdate);
        Opinie testOpinie = opinieList.get(opinieList.size() - 1);
        assertThat(testOpinie.getOpinia()).isEqualTo(UPDATED_OPINIA);
    }

    @Test
    @Transactional
    public void updateNonExistingOpinie() throws Exception {
        int databaseSizeBeforeUpdate = opinieRepository.findAll().size();

        // Create the Opinie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpinieMockMvc.perform(put("/api/opinies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opinie)))
            .andExpect(status().isBadRequest());

        // Validate the Opinie in the database
        List<Opinie> opinieList = opinieRepository.findAll();
        assertThat(opinieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOpinie() throws Exception {
        // Initialize the database
        opinieRepository.saveAndFlush(opinie);

        int databaseSizeBeforeDelete = opinieRepository.findAll().size();

        // Delete the opinie
        restOpinieMockMvc.perform(delete("/api/opinies/{id}", opinie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Opinie> opinieList = opinieRepository.findAll();
        assertThat(opinieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Opinie.class);
        Opinie opinie1 = new Opinie();
        opinie1.setId(1L);
        Opinie opinie2 = new Opinie();
        opinie2.setId(opinie1.getId());
        assertThat(opinie1).isEqualTo(opinie2);
        opinie2.setId(2L);
        assertThat(opinie1).isNotEqualTo(opinie2);
        opinie1.setId(null);
        assertThat(opinie1).isNotEqualTo(opinie2);
    }
}
