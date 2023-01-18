//package br.gov.economia.maisbrasil.contratos.web.rest;
//
//import static br.gov.economia.maisbrasil.contratos.web.rest.TestUtil.createFormattingConversionService;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.validation.Validator;
//
//import br.gov.economia.maisbrasil.contratos.MaisbrasilContratosBackendApp;
//import br.gov.economia.maisbrasil.contratos.domain.Lote;
//import br.gov.economia.maisbrasil.contratos.repository.LoteRepository;
//import br.gov.economia.maisbrasil.contratos.web.rest.errors.ExceptionTranslator;
//
///**
// * Integration tests for the {@Link LoteResource} REST controller.
// */
//@Disabled
//@SpringBootTest(classes = MaisbrasilContratosBackendApp.class)
//public class LoteResourceIT {
//
//    private static final Integer DEFAULT_NUMERO = 1;
//    private static final Integer UPDATED_NUMERO = 2;
//
//    private static final BigDecimal DEFAULT_VERSAO = new BigDecimal(1);
//    private static final BigDecimal UPDATED_VERSAO = new BigDecimal(2);
//
//    private static final String DEFAULT_ADT_LOGIN = "AAAAAAAAAA";
//    private static final String UPDATED_ADT_LOGIN = "BBBBBBBBBB";
//
//    private static final LocalDate DEFAULT_ADT_DATA_HORA = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_ADT_DATA_HORA = LocalDate.now(ZoneId.systemDefault());
//
//    private static final String DEFAULT_ADT_OPERACAO = "AAAAAA";
//    private static final String UPDATED_ADT_OPERACAO = "BBBBBB";
//
//    @Autowired
//    private LoteRepository loteRepository;
//
//    @Autowired
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    @Autowired
//    private ExceptionTranslator exceptionTranslator;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private Validator validator;
//
//    private MockMvc restLoteMockMvc;
//
//    private Lote lote;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final LoteResource loteResource = new LoteResource(loteRepository);
//        this.restLoteMockMvc = MockMvcBuilders.standaloneSetup(loteResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setConversionService(createFormattingConversionService())
//            .setMessageConverters(jacksonMessageConverter)
//            .setValidator(validator).build();
//    }
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Lote createEntity(EntityManager em) {
//        Lote lote = new Lote()
//            .numero(DEFAULT_NUMERO)
//            .versao(DEFAULT_VERSAO)
//            .adtLogin(DEFAULT_ADT_LOGIN)
//            .adtDataHora(DEFAULT_ADT_DATA_HORA)
//            .adtOperacao(DEFAULT_ADT_OPERACAO);
//        return lote;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Lote createUpdatedEntity(EntityManager em) {
//        Lote lote = new Lote()
//            .numero(UPDATED_NUMERO)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//        return lote;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        lote = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createLote() throws Exception {
//        int databaseSizeBeforeCreate = loteRepository.findAll().size();
//
//        // Create the Lote
//        restLoteMockMvc.perform(post("/api/lotes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(lote)))
//            .andExpect(status().isCreated());
//
//        // Validate the Lote in the database
//        List<Lote> loteList = loteRepository.findAll();
//        assertThat(loteList).hasSize(databaseSizeBeforeCreate + 1);
//        Lote testLote = loteList.get(loteList.size() - 1);
//        assertThat(testLote.getNumero()).isEqualTo(DEFAULT_NUMERO);
//        assertThat(testLote.getVersao()).isEqualTo(DEFAULT_VERSAO);
//        assertThat(testLote.getAdtLogin()).isEqualTo(DEFAULT_ADT_LOGIN);
//        assertThat(testLote.getAdtDataHora()).isEqualTo(DEFAULT_ADT_DATA_HORA);
//        assertThat(testLote.getAdtOperacao()).isEqualTo(DEFAULT_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void createLoteWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = loteRepository.findAll().size();
//
//        // Create the Lote with an existing ID
//        lote.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restLoteMockMvc.perform(post("/api/lotes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(lote)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Lote in the database
//        List<Lote> loteList = loteRepository.findAll();
//        assertThat(loteList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkNumeroIsRequired() throws Exception {
//        int databaseSizeBeforeTest = loteRepository.findAll().size();
//        // set the field null
//        lote.setNumero(null);
//
//        // Create the Lote, which fails.
//
//        restLoteMockMvc.perform(post("/api/lotes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(lote)))
//            .andExpect(status().isBadRequest());
//
//        List<Lote> loteList = loteRepository.findAll();
//        assertThat(loteList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVersaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = loteRepository.findAll().size();
//        // set the field null
//        lote.setVersao(null);
//
//        // Create the Lote, which fails.
//
//        restLoteMockMvc.perform(post("/api/lotes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(lote)))
//            .andExpect(status().isBadRequest());
//
//        List<Lote> loteList = loteRepository.findAll();
//        assertThat(loteList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtLoginIsRequired() throws Exception {
//        int databaseSizeBeforeTest = loteRepository.findAll().size();
//        // set the field null
//        lote.setAdtLogin(null);
//
//        // Create the Lote, which fails.
//
//        restLoteMockMvc.perform(post("/api/lotes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(lote)))
//            .andExpect(status().isBadRequest());
//
//        List<Lote> loteList = loteRepository.findAll();
//        assertThat(loteList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtDataHoraIsRequired() throws Exception {
//        int databaseSizeBeforeTest = loteRepository.findAll().size();
//        // set the field null
//        lote.setAdtDataHora(null);
//
//        // Create the Lote, which fails.
//
//        restLoteMockMvc.perform(post("/api/lotes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(lote)))
//            .andExpect(status().isBadRequest());
//
//        List<Lote> loteList = loteRepository.findAll();
//        assertThat(loteList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtOperacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = loteRepository.findAll().size();
//        // set the field null
//        lote.setAdtOperacao(null);
//
//        // Create the Lote, which fails.
//
//        restLoteMockMvc.perform(post("/api/lotes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(lote)))
//            .andExpect(status().isBadRequest());
//
//        List<Lote> loteList = loteRepository.findAll();
//        assertThat(loteList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllLotes() throws Exception {
//        // Initialize the database
//        loteRepository.saveAndFlush(lote);
//
//        // Get all the loteList
//        restLoteMockMvc.perform(get("/api/lotes?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(lote.getId().intValue())))
//            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
//            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
//            .andExpect(jsonPath("$.[*].adtLogin").value(hasItem(DEFAULT_ADT_LOGIN.toString())))
//            .andExpect(jsonPath("$.[*].adtDataHora").value(hasItem(DEFAULT_ADT_DATA_HORA.toString())))
//            .andExpect(jsonPath("$.[*].adtOperacao").value(hasItem(DEFAULT_ADT_OPERACAO.toString())));
//    }
//    
//    @Test
//    @Transactional
//    public void getLote() throws Exception {
//        // Initialize the database
//        loteRepository.saveAndFlush(lote);
//
//        // Get the lote
//        restLoteMockMvc.perform(get("/api/lotes/{id}", lote.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(lote.getId().intValue()))
//            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
//            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
//            .andExpect(jsonPath("$.adtLogin").value(DEFAULT_ADT_LOGIN.toString()))
//            .andExpect(jsonPath("$.adtDataHora").value(DEFAULT_ADT_DATA_HORA.toString()))
//            .andExpect(jsonPath("$.adtOperacao").value(DEFAULT_ADT_OPERACAO.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingLote() throws Exception {
//        // Get the lote
//        restLoteMockMvc.perform(get("/api/lotes/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateLote() throws Exception {
//        // Initialize the database
//        loteRepository.saveAndFlush(lote);
//
//        int databaseSizeBeforeUpdate = loteRepository.findAll().size();
//
//        // Update the lote
//        Lote updatedLote = loteRepository.findById(lote.getId()).get();
//        // Disconnect from session so that the updates on updatedLote are not directly saved in db
//        em.detach(updatedLote);
//        updatedLote
//            .numero(UPDATED_NUMERO)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//
//        restLoteMockMvc.perform(put("/api/lotes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedLote)))
//            .andExpect(status().isOk());
//
//        // Validate the Lote in the database
//        List<Lote> loteList = loteRepository.findAll();
//        assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
//        Lote testLote = loteList.get(loteList.size() - 1);
//        assertThat(testLote.getNumero()).isEqualTo(UPDATED_NUMERO);
//        assertThat(testLote.getVersao()).isEqualTo(UPDATED_VERSAO);
//        assertThat(testLote.getAdtLogin()).isEqualTo(UPDATED_ADT_LOGIN);
//        assertThat(testLote.getAdtDataHora()).isEqualTo(UPDATED_ADT_DATA_HORA);
//        assertThat(testLote.getAdtOperacao()).isEqualTo(UPDATED_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingLote() throws Exception {
//        int databaseSizeBeforeUpdate = loteRepository.findAll().size();
//
//        // Create the Lote
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restLoteMockMvc.perform(put("/api/lotes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(lote)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Lote in the database
//        List<Lote> loteList = loteRepository.findAll();
//        assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteLote() throws Exception {
//        // Initialize the database
//        loteRepository.saveAndFlush(lote);
//
//        int databaseSizeBeforeDelete = loteRepository.findAll().size();
//
//        // Delete the lote
//        restLoteMockMvc.perform(delete("/api/lotes/{id}", lote.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Lote> loteList = loteRepository.findAll();
//        assertThat(loteList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Lote.class);
//        Lote lote1 = new Lote();
//        lote1.setId(1L);
//        Lote lote2 = new Lote();
//        lote2.setId(lote1.getId());
//        assertThat(lote1).isEqualTo(lote2);
//        lote2.setId(2L);
//        assertThat(lote1).isNotEqualTo(lote2);
//        lote1.setId(null);
//        assertThat(lote1).isNotEqualTo(lote2);
//    }
//}
