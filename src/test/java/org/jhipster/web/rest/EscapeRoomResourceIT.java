package org.jhipster.web.rest;

import org.jhipster.EscapeRoomApp;
import org.jhipster.domain.EscapeRoom;
import org.jhipster.repository.EscapeRoomRepository;
import org.jhipster.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.jhipster.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.jhipster.domain.enumeration.Poziom;
/**
 * Integration tests for the {@Link EscapeRoomResource} REST controller.
 */
@SpringBootTest(classes = EscapeRoomApp.class)
public class EscapeRoomResourceIT {

    private static final byte[] DEFAULT_ZDJECIE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ZDJECIE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ZDJECIE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ZDJECIE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ULICA = "AAAAAAAAAA";
    private static final String UPDATED_ULICA = "BBBBBBBBBB";

    private static final String DEFAULT_MIASTO = "AAAAAAAAAA";
    private static final String UPDATED_MIASTO = "BBBBBBBBBB";

    private static final String DEFAULT_KOD_POCZTOWY = "AAAAAAAAAA";
    private static final String UPDATED_KOD_POCZTOWY = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_STRONA_WWW = "AAAAAAAAAA";
    private static final String UPDATED_STRONA_WWW = "BBBBBBBBBB";

    private static final String DEFAULT_NAZWA = "AAAAAAAAAA";
    private static final String UPDATED_NAZWA = "BBBBBBBBBB";

    private static final String DEFAULT_OPIS = "AAAAAAAAAA";
    private static final String UPDATED_OPIS = "BBBBBBBBBB";

    private static final String DEFAULT_ILOSC_OSOB = "AAAAAAAAAA";
    private static final String UPDATED_ILOSC_OSOB = "BBBBBBBBBB";

    private static final Integer DEFAULT_CENA = 1;
    private static final Integer UPDATED_CENA = 2;

    private static final Integer DEFAULT_PKT_DO_ZDOBYCIA = 1;
    private static final Integer UPDATED_PKT_DO_ZDOBYCIA = 2;

    private static final Integer DEFAULT_WYMAGANA_ILOSC_PKT = 1;
    private static final Integer UPDATED_WYMAGANA_ILOSC_PKT = 2;

    private static final Poziom DEFAULT_POZIOM_TRUDNOSCI = Poziom.LATWY;
    private static final Poziom UPDATED_POZIOM_TRUDNOSCI = Poziom.SREDNI;

    private static final String DEFAULT_CZAS_NA_PRZEJSCIE = "AAAAAAAAAA";
    private static final String UPDATED_CZAS_NA_PRZEJSCIE = "BBBBBBBBBB";

    @Autowired
    private EscapeRoomRepository escapeRoomRepository;

    @Mock
    private EscapeRoomRepository escapeRoomRepositoryMock;

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

    private MockMvc restEscapeRoomMockMvc;

    private EscapeRoom escapeRoom;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EscapeRoomResource escapeRoomResource = new EscapeRoomResource(escapeRoomRepository);
        this.restEscapeRoomMockMvc = MockMvcBuilders.standaloneSetup(escapeRoomResource)
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
    public static EscapeRoom createEntity(EntityManager em) {
        EscapeRoom escapeRoom = new EscapeRoom()
            .zdjecie(DEFAULT_ZDJECIE)
            .zdjecieContentType(DEFAULT_ZDJECIE_CONTENT_TYPE)
            .ulica(DEFAULT_ULICA)
            .miasto(DEFAULT_MIASTO)
            .kodPocztowy(DEFAULT_KOD_POCZTOWY)
            .email(DEFAULT_EMAIL)
            .telefon(DEFAULT_TELEFON)
            .stronaWWW(DEFAULT_STRONA_WWW)
            .nazwa(DEFAULT_NAZWA)
            .opis(DEFAULT_OPIS)
            .iloscOsob(DEFAULT_ILOSC_OSOB)
            .cena(DEFAULT_CENA)
            .pktDoZdobycia(DEFAULT_PKT_DO_ZDOBYCIA)
            .wymaganaIloscPkt(DEFAULT_WYMAGANA_ILOSC_PKT)
            .poziomTrudnosci(DEFAULT_POZIOM_TRUDNOSCI)
            .czasNaPrzejscie(DEFAULT_CZAS_NA_PRZEJSCIE);
        return escapeRoom;
    }

    @BeforeEach
    public void initTest() {
        escapeRoom = createEntity(em);
    }

    @Test
    @Transactional
    public void createEscapeRoom() throws Exception {
        int databaseSizeBeforeCreate = escapeRoomRepository.findAll().size();

        // Create the EscapeRoom
        restEscapeRoomMockMvc.perform(post("/api/escape-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escapeRoom)))
            .andExpect(status().isCreated());

        // Validate the EscapeRoom in the database
        List<EscapeRoom> escapeRoomList = escapeRoomRepository.findAll();
        assertThat(escapeRoomList).hasSize(databaseSizeBeforeCreate + 1);
        EscapeRoom testEscapeRoom = escapeRoomList.get(escapeRoomList.size() - 1);
        assertThat(testEscapeRoom.getZdjecie()).isEqualTo(DEFAULT_ZDJECIE);
        assertThat(testEscapeRoom.getZdjecieContentType()).isEqualTo(DEFAULT_ZDJECIE_CONTENT_TYPE);
        assertThat(testEscapeRoom.getUlica()).isEqualTo(DEFAULT_ULICA);
        assertThat(testEscapeRoom.getMiasto()).isEqualTo(DEFAULT_MIASTO);
        assertThat(testEscapeRoom.getKodPocztowy()).isEqualTo(DEFAULT_KOD_POCZTOWY);
        assertThat(testEscapeRoom.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEscapeRoom.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testEscapeRoom.getStronaWWW()).isEqualTo(DEFAULT_STRONA_WWW);
        assertThat(testEscapeRoom.getNazwa()).isEqualTo(DEFAULT_NAZWA);
        assertThat(testEscapeRoom.getOpis()).isEqualTo(DEFAULT_OPIS);
        assertThat(testEscapeRoom.getIloscOsob()).isEqualTo(DEFAULT_ILOSC_OSOB);
        assertThat(testEscapeRoom.getCena()).isEqualTo(DEFAULT_CENA);
        assertThat(testEscapeRoom.getPktDoZdobycia()).isEqualTo(DEFAULT_PKT_DO_ZDOBYCIA);
        assertThat(testEscapeRoom.getWymaganaIloscPkt()).isEqualTo(DEFAULT_WYMAGANA_ILOSC_PKT);
        assertThat(testEscapeRoom.getPoziomTrudnosci()).isEqualTo(DEFAULT_POZIOM_TRUDNOSCI);
        assertThat(testEscapeRoom.getCzasNaPrzejscie()).isEqualTo(DEFAULT_CZAS_NA_PRZEJSCIE);
    }

    @Test
    @Transactional
    public void createEscapeRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = escapeRoomRepository.findAll().size();

        // Create the EscapeRoom with an existing ID
        escapeRoom.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEscapeRoomMockMvc.perform(post("/api/escape-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escapeRoom)))
            .andExpect(status().isBadRequest());

        // Validate the EscapeRoom in the database
        List<EscapeRoom> escapeRoomList = escapeRoomRepository.findAll();
        assertThat(escapeRoomList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPoziomTrudnosciIsRequired() throws Exception {
        int databaseSizeBeforeTest = escapeRoomRepository.findAll().size();
        // set the field null
        escapeRoom.setPoziomTrudnosci(null);

        // Create the EscapeRoom, which fails.

        restEscapeRoomMockMvc.perform(post("/api/escape-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escapeRoom)))
            .andExpect(status().isBadRequest());

        List<EscapeRoom> escapeRoomList = escapeRoomRepository.findAll();
        assertThat(escapeRoomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEscapeRooms() throws Exception {
        // Initialize the database
        escapeRoomRepository.saveAndFlush(escapeRoom);

        // Get all the escapeRoomList
        restEscapeRoomMockMvc.perform(get("/api/escape-rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escapeRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].zdjecieContentType").value(hasItem(DEFAULT_ZDJECIE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].zdjecie").value(hasItem(Base64Utils.encodeToString(DEFAULT_ZDJECIE))))
            .andExpect(jsonPath("$.[*].ulica").value(hasItem(DEFAULT_ULICA.toString())))
            .andExpect(jsonPath("$.[*].miasto").value(hasItem(DEFAULT_MIASTO.toString())))
            .andExpect(jsonPath("$.[*].kodPocztowy").value(hasItem(DEFAULT_KOD_POCZTOWY.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())))
            .andExpect(jsonPath("$.[*].stronaWWW").value(hasItem(DEFAULT_STRONA_WWW.toString())))
            .andExpect(jsonPath("$.[*].nazwa").value(hasItem(DEFAULT_NAZWA.toString())))
            .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS.toString())))
            .andExpect(jsonPath("$.[*].iloscOsob").value(hasItem(DEFAULT_ILOSC_OSOB.toString())))
            .andExpect(jsonPath("$.[*].cena").value(hasItem(DEFAULT_CENA)))
            .andExpect(jsonPath("$.[*].pktDoZdobycia").value(hasItem(DEFAULT_PKT_DO_ZDOBYCIA)))
            .andExpect(jsonPath("$.[*].wymaganaIloscPkt").value(hasItem(DEFAULT_WYMAGANA_ILOSC_PKT)))
            .andExpect(jsonPath("$.[*].poziomTrudnosci").value(hasItem(DEFAULT_POZIOM_TRUDNOSCI.toString())))
            .andExpect(jsonPath("$.[*].czasNaPrzejscie").value(hasItem(DEFAULT_CZAS_NA_PRZEJSCIE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllEscapeRoomsWithEagerRelationshipsIsEnabled() throws Exception {
        EscapeRoomResource escapeRoomResource = new EscapeRoomResource(escapeRoomRepositoryMock);
        when(escapeRoomRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restEscapeRoomMockMvc = MockMvcBuilders.standaloneSetup(escapeRoomResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEscapeRoomMockMvc.perform(get("/api/escape-rooms?eagerload=true"))
        .andExpect(status().isOk());

        verify(escapeRoomRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllEscapeRoomsWithEagerRelationshipsIsNotEnabled() throws Exception {
        EscapeRoomResource escapeRoomResource = new EscapeRoomResource(escapeRoomRepositoryMock);
            when(escapeRoomRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restEscapeRoomMockMvc = MockMvcBuilders.standaloneSetup(escapeRoomResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEscapeRoomMockMvc.perform(get("/api/escape-rooms?eagerload=true"))
        .andExpect(status().isOk());

            verify(escapeRoomRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEscapeRoom() throws Exception {
        // Initialize the database
        escapeRoomRepository.saveAndFlush(escapeRoom);

        // Get the escapeRoom
        restEscapeRoomMockMvc.perform(get("/api/escape-rooms/{id}", escapeRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(escapeRoom.getId().intValue()))
            .andExpect(jsonPath("$.zdjecieContentType").value(DEFAULT_ZDJECIE_CONTENT_TYPE))
            .andExpect(jsonPath("$.zdjecie").value(Base64Utils.encodeToString(DEFAULT_ZDJECIE)))
            .andExpect(jsonPath("$.ulica").value(DEFAULT_ULICA.toString()))
            .andExpect(jsonPath("$.miasto").value(DEFAULT_MIASTO.toString()))
            .andExpect(jsonPath("$.kodPocztowy").value(DEFAULT_KOD_POCZTOWY.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.stronaWWW").value(DEFAULT_STRONA_WWW.toString()))
            .andExpect(jsonPath("$.nazwa").value(DEFAULT_NAZWA.toString()))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS.toString()))
            .andExpect(jsonPath("$.iloscOsob").value(DEFAULT_ILOSC_OSOB.toString()))
            .andExpect(jsonPath("$.cena").value(DEFAULT_CENA))
            .andExpect(jsonPath("$.pktDoZdobycia").value(DEFAULT_PKT_DO_ZDOBYCIA))
            .andExpect(jsonPath("$.wymaganaIloscPkt").value(DEFAULT_WYMAGANA_ILOSC_PKT))
            .andExpect(jsonPath("$.poziomTrudnosci").value(DEFAULT_POZIOM_TRUDNOSCI.toString()))
            .andExpect(jsonPath("$.czasNaPrzejscie").value(DEFAULT_CZAS_NA_PRZEJSCIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEscapeRoom() throws Exception {
        // Get the escapeRoom
        restEscapeRoomMockMvc.perform(get("/api/escape-rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEscapeRoom() throws Exception {
        // Initialize the database
        escapeRoomRepository.saveAndFlush(escapeRoom);

        int databaseSizeBeforeUpdate = escapeRoomRepository.findAll().size();

        // Update the escapeRoom
        EscapeRoom updatedEscapeRoom = escapeRoomRepository.findById(escapeRoom.getId()).get();
        // Disconnect from session so that the updates on updatedEscapeRoom are not directly saved in db
        em.detach(updatedEscapeRoom);
        updatedEscapeRoom
            .zdjecie(UPDATED_ZDJECIE)
            .zdjecieContentType(UPDATED_ZDJECIE_CONTENT_TYPE)
            .ulica(UPDATED_ULICA)
            .miasto(UPDATED_MIASTO)
            .kodPocztowy(UPDATED_KOD_POCZTOWY)
            .email(UPDATED_EMAIL)
            .telefon(UPDATED_TELEFON)
            .stronaWWW(UPDATED_STRONA_WWW)
            .nazwa(UPDATED_NAZWA)
            .opis(UPDATED_OPIS)
            .iloscOsob(UPDATED_ILOSC_OSOB)
            .cena(UPDATED_CENA)
            .pktDoZdobycia(UPDATED_PKT_DO_ZDOBYCIA)
            .wymaganaIloscPkt(UPDATED_WYMAGANA_ILOSC_PKT)
            .poziomTrudnosci(UPDATED_POZIOM_TRUDNOSCI)
            .czasNaPrzejscie(UPDATED_CZAS_NA_PRZEJSCIE);

        restEscapeRoomMockMvc.perform(put("/api/escape-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEscapeRoom)))
            .andExpect(status().isOk());

        // Validate the EscapeRoom in the database
        List<EscapeRoom> escapeRoomList = escapeRoomRepository.findAll();
        assertThat(escapeRoomList).hasSize(databaseSizeBeforeUpdate);
        EscapeRoom testEscapeRoom = escapeRoomList.get(escapeRoomList.size() - 1);
        assertThat(testEscapeRoom.getZdjecie()).isEqualTo(UPDATED_ZDJECIE);
        assertThat(testEscapeRoom.getZdjecieContentType()).isEqualTo(UPDATED_ZDJECIE_CONTENT_TYPE);
        assertThat(testEscapeRoom.getUlica()).isEqualTo(UPDATED_ULICA);
        assertThat(testEscapeRoom.getMiasto()).isEqualTo(UPDATED_MIASTO);
        assertThat(testEscapeRoom.getKodPocztowy()).isEqualTo(UPDATED_KOD_POCZTOWY);
        assertThat(testEscapeRoom.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEscapeRoom.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testEscapeRoom.getStronaWWW()).isEqualTo(UPDATED_STRONA_WWW);
        assertThat(testEscapeRoom.getNazwa()).isEqualTo(UPDATED_NAZWA);
        assertThat(testEscapeRoom.getOpis()).isEqualTo(UPDATED_OPIS);
        assertThat(testEscapeRoom.getIloscOsob()).isEqualTo(UPDATED_ILOSC_OSOB);
        assertThat(testEscapeRoom.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testEscapeRoom.getPktDoZdobycia()).isEqualTo(UPDATED_PKT_DO_ZDOBYCIA);
        assertThat(testEscapeRoom.getWymaganaIloscPkt()).isEqualTo(UPDATED_WYMAGANA_ILOSC_PKT);
        assertThat(testEscapeRoom.getPoziomTrudnosci()).isEqualTo(UPDATED_POZIOM_TRUDNOSCI);
        assertThat(testEscapeRoom.getCzasNaPrzejscie()).isEqualTo(UPDATED_CZAS_NA_PRZEJSCIE);
    }

    @Test
    @Transactional
    public void updateNonExistingEscapeRoom() throws Exception {
        int databaseSizeBeforeUpdate = escapeRoomRepository.findAll().size();

        // Create the EscapeRoom

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEscapeRoomMockMvc.perform(put("/api/escape-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escapeRoom)))
            .andExpect(status().isBadRequest());

        // Validate the EscapeRoom in the database
        List<EscapeRoom> escapeRoomList = escapeRoomRepository.findAll();
        assertThat(escapeRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEscapeRoom() throws Exception {
        // Initialize the database
        escapeRoomRepository.saveAndFlush(escapeRoom);

        int databaseSizeBeforeDelete = escapeRoomRepository.findAll().size();

        // Delete the escapeRoom
        restEscapeRoomMockMvc.perform(delete("/api/escape-rooms/{id}", escapeRoom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<EscapeRoom> escapeRoomList = escapeRoomRepository.findAll();
        assertThat(escapeRoomList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EscapeRoom.class);
        EscapeRoom escapeRoom1 = new EscapeRoom();
        escapeRoom1.setId(1L);
        EscapeRoom escapeRoom2 = new EscapeRoom();
        escapeRoom2.setId(escapeRoom1.getId());
        assertThat(escapeRoom1).isEqualTo(escapeRoom2);
        escapeRoom2.setId(2L);
        assertThat(escapeRoom1).isNotEqualTo(escapeRoom2);
        escapeRoom1.setId(null);
        assertThat(escapeRoom1).isNotEqualTo(escapeRoom2);
    }
}
