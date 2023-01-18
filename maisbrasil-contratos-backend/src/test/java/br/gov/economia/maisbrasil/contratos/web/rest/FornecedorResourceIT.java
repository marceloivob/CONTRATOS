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
//import br.gov.economia.maisbrasil.contratos.domain.Fornecedor;
//import br.gov.economia.maisbrasil.contratos.repository.FornecedorRepository;
//import br.gov.economia.maisbrasil.contratos.web.rest.errors.ExceptionTranslator;
//
///**
// * Integration tests for the {@Link FornecedorResource} REST controller.
// */
//@Disabled
//@SpringBootTest(classes = MaisbrasilContratosBackendApp.class)
//public class FornecedorResourceIT {
//
//    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
//    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";
//
//    private static final String DEFAULT_TIPO_IDENTIFICACAO = "AAAA";
//    private static final String UPDATED_TIPO_IDENTIFICACAO = "BBBB";
//
//    private static final String DEFAULT_IDENTIFICACAO = "AAAAAAAAAA";
//    private static final String UPDATED_IDENTIFICACAO = "BBBBBBBBBB";
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
//    private FornecedorRepository fornecedorRepository;
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
//    private MockMvc restFornecedorMockMvc;
//
//    private Fornecedor fornecedor;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final FornecedorResource fornecedorResource = new FornecedorResource(fornecedorRepository);
//        this.restFornecedorMockMvc = MockMvcBuilders.standaloneSetup(fornecedorResource)
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
//    public static Fornecedor createEntity(EntityManager em) {
//        Fornecedor fornecedor = new Fornecedor()
//            .razaoSocial(DEFAULT_RAZAO_SOCIAL)
//            .tipoIdentificacao(DEFAULT_TIPO_IDENTIFICACAO)
//            .identificacao(DEFAULT_IDENTIFICACAO)
//            .versao(DEFAULT_VERSAO)
//            .adtLogin(DEFAULT_ADT_LOGIN)
//            .adtDataHora(DEFAULT_ADT_DATA_HORA)
//            .adtOperacao(DEFAULT_ADT_OPERACAO);
//        return fornecedor;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Fornecedor createUpdatedEntity(EntityManager em) {
//        Fornecedor fornecedor = new Fornecedor()
//            .razaoSocial(UPDATED_RAZAO_SOCIAL)
//            .tipoIdentificacao(UPDATED_TIPO_IDENTIFICACAO)
//            .identificacao(UPDATED_IDENTIFICACAO)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//        return fornecedor;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        fornecedor = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createFornecedor() throws Exception {
//        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();
//
//        // Create the Fornecedor
//        restFornecedorMockMvc.perform(post("/api/fornecedors")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
//            .andExpect(status().isCreated());
//
//        // Validate the Fornecedor in the database
//        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
//        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate + 1);
//        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
//        assertThat(testFornecedor.getRazaoSocial()).isEqualTo(DEFAULT_RAZAO_SOCIAL);
//        assertThat(testFornecedor.getTipoIdentificacao()).isEqualTo(DEFAULT_TIPO_IDENTIFICACAO);
//        assertThat(testFornecedor.getIdentificacao()).isEqualTo(DEFAULT_IDENTIFICACAO);
//        assertThat(testFornecedor.getVersao()).isEqualTo(DEFAULT_VERSAO);
//        assertThat(testFornecedor.getAdtLogin()).isEqualTo(DEFAULT_ADT_LOGIN);
//        assertThat(testFornecedor.getAdtDataHora()).isEqualTo(DEFAULT_ADT_DATA_HORA);
//        assertThat(testFornecedor.getAdtOperacao()).isEqualTo(DEFAULT_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void createFornecedorWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();
//
//        // Create the Fornecedor with an existing ID
//        fornecedor.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restFornecedorMockMvc.perform(post("/api/fornecedors")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Fornecedor in the database
//        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
//        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkRazaoSocialIsRequired() throws Exception {
//        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
//        // set the field null
//        fornecedor.setRazaoSocial(null);
//
//        // Create the Fornecedor, which fails.
//
//        restFornecedorMockMvc.perform(post("/api/fornecedors")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
//            .andExpect(status().isBadRequest());
//
//        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
//        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkTipoIdentificacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
//        // set the field null
//        fornecedor.setTipoIdentificacao(null);
//
//        // Create the Fornecedor, which fails.
//
//        restFornecedorMockMvc.perform(post("/api/fornecedors")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
//            .andExpect(status().isBadRequest());
//
//        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
//        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkIdentificacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
//        // set the field null
//        fornecedor.setIdentificacao(null);
//
//        // Create the Fornecedor, which fails.
//
//        restFornecedorMockMvc.perform(post("/api/fornecedors")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
//            .andExpect(status().isBadRequest());
//
//        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
//        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVersaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
//        // set the field null
//        fornecedor.setVersao(null);
//
//        // Create the Fornecedor, which fails.
//
//        restFornecedorMockMvc.perform(post("/api/fornecedors")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
//            .andExpect(status().isBadRequest());
//
//        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
//        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtLoginIsRequired() throws Exception {
//        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
//        // set the field null
//        fornecedor.setAdtLogin(null);
//
//        // Create the Fornecedor, which fails.
//
//        restFornecedorMockMvc.perform(post("/api/fornecedors")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
//            .andExpect(status().isBadRequest());
//
//        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
//        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtDataHoraIsRequired() throws Exception {
//        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
//        // set the field null
//        fornecedor.setAdtDataHora(null);
//
//        // Create the Fornecedor, which fails.
//
//        restFornecedorMockMvc.perform(post("/api/fornecedors")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
//            .andExpect(status().isBadRequest());
//
//        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
//        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtOperacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
//        // set the field null
//        fornecedor.setAdtOperacao(null);
//
//        // Create the Fornecedor, which fails.
//
//        restFornecedorMockMvc.perform(post("/api/fornecedors")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
//            .andExpect(status().isBadRequest());
//
//        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
//        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllFornecedors() throws Exception {
//        // Initialize the database
//        fornecedorRepository.saveAndFlush(fornecedor);
//
//        // Get all the fornecedorList
//        restFornecedorMockMvc.perform(get("/api/fornecedors?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
//            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL.toString())))
//            .andExpect(jsonPath("$.[*].tipoIdentificacao").value(hasItem(DEFAULT_TIPO_IDENTIFICACAO.toString())))
//            .andExpect(jsonPath("$.[*].identificacao").value(hasItem(DEFAULT_IDENTIFICACAO.toString())))
//            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
//            .andExpect(jsonPath("$.[*].adtLogin").value(hasItem(DEFAULT_ADT_LOGIN.toString())))
//            .andExpect(jsonPath("$.[*].adtDataHora").value(hasItem(DEFAULT_ADT_DATA_HORA.toString())))
//            .andExpect(jsonPath("$.[*].adtOperacao").value(hasItem(DEFAULT_ADT_OPERACAO.toString())));
//    }
//    
//    @Test
//    @Transactional
//    public void getFornecedor() throws Exception {
//        // Initialize the database
//        fornecedorRepository.saveAndFlush(fornecedor);
//
//        // Get the fornecedor
//        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", fornecedor.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(fornecedor.getId().intValue()))
//            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL.toString()))
//            .andExpect(jsonPath("$.tipoIdentificacao").value(DEFAULT_TIPO_IDENTIFICACAO.toString()))
//            .andExpect(jsonPath("$.identificacao").value(DEFAULT_IDENTIFICACAO.toString()))
//            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
//            .andExpect(jsonPath("$.adtLogin").value(DEFAULT_ADT_LOGIN.toString()))
//            .andExpect(jsonPath("$.adtDataHora").value(DEFAULT_ADT_DATA_HORA.toString()))
//            .andExpect(jsonPath("$.adtOperacao").value(DEFAULT_ADT_OPERACAO.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingFornecedor() throws Exception {
//        // Get the fornecedor
//        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateFornecedor() throws Exception {
//        // Initialize the database
//        fornecedorRepository.saveAndFlush(fornecedor);
//
//        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
//
//        // Update the fornecedor
//        Fornecedor updatedFornecedor = fornecedorRepository.findById(fornecedor.getId()).get();
//        // Disconnect from session so that the updates on updatedFornecedor are not directly saved in db
//        em.detach(updatedFornecedor);
//        updatedFornecedor
//            .razaoSocial(UPDATED_RAZAO_SOCIAL)
//            .tipoIdentificacao(UPDATED_TIPO_IDENTIFICACAO)
//            .identificacao(UPDATED_IDENTIFICACAO)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//
//        restFornecedorMockMvc.perform(put("/api/fornecedors")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedFornecedor)))
//            .andExpect(status().isOk());
//
//        // Validate the Fornecedor in the database
//        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
//        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
//        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
//        assertThat(testFornecedor.getRazaoSocial()).isEqualTo(UPDATED_RAZAO_SOCIAL);
//        assertThat(testFornecedor.getTipoIdentificacao()).isEqualTo(UPDATED_TIPO_IDENTIFICACAO);
//        assertThat(testFornecedor.getIdentificacao()).isEqualTo(UPDATED_IDENTIFICACAO);
//        assertThat(testFornecedor.getVersao()).isEqualTo(UPDATED_VERSAO);
//        assertThat(testFornecedor.getAdtLogin()).isEqualTo(UPDATED_ADT_LOGIN);
//        assertThat(testFornecedor.getAdtDataHora()).isEqualTo(UPDATED_ADT_DATA_HORA);
//        assertThat(testFornecedor.getAdtOperacao()).isEqualTo(UPDATED_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingFornecedor() throws Exception {
//        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
//
//        // Create the Fornecedor
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restFornecedorMockMvc.perform(put("/api/fornecedors")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Fornecedor in the database
//        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
//        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteFornecedor() throws Exception {
//        // Initialize the database
//        fornecedorRepository.saveAndFlush(fornecedor);
//
//        int databaseSizeBeforeDelete = fornecedorRepository.findAll().size();
//
//        // Delete the fornecedor
//        restFornecedorMockMvc.perform(delete("/api/fornecedors/{id}", fornecedor.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
//        assertThat(fornecedorList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Fornecedor.class);
//        Fornecedor fornecedor1 = new Fornecedor();
//        fornecedor1.setId(1L);
//        Fornecedor fornecedor2 = new Fornecedor();
//        fornecedor2.setId(fornecedor1.getId());
//        assertThat(fornecedor1).isEqualTo(fornecedor2);
//        fornecedor2.setId(2L);
//        assertThat(fornecedor1).isNotEqualTo(fornecedor2);
//        fornecedor1.setId(null);
//        assertThat(fornecedor1).isNotEqualTo(fornecedor2);
//    }
//}
