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
//import br.gov.economia.maisbrasil.contratos.domain.TermoAditivo;
//import br.gov.economia.maisbrasil.contratos.repository.TermoAditivoRepository;
//import br.gov.economia.maisbrasil.contratos.web.rest.errors.ExceptionTranslator;
//
///**
// * Integration tests for the {@Link TermoAditivoResource} REST controller.
// */
//@Disabled
//@SpringBootTest(classes = MaisbrasilContratosBackendApp.class)
//public class TermoAditivoResourceIT {
//
//    private static final String DEFAULT_IN_TIPO_DE_ADITIVO = "AAA";
//    private static final String UPDATED_IN_TIPO_DE_ADITIVO = "BBB";
//
//    private static final Boolean DEFAULT_IN_ALTERACAO_VIGENCIA = false;
//    private static final Boolean UPDATED_IN_ALTERACAO_VIGENCIA = true;
//
//    private static final Boolean DEFAULT_IN_ALTERACAO_CLAUSULA = false;
//    private static final Boolean UPDATED_IN_ALTERACAO_CLAUSULA = true;
//
//    private static final Boolean DEFAULT_IN_ALTERACAO_OBJETO = false;
//    private static final Boolean UPDATED_IN_ALTERACAO_OBJETO = true;
//
//    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
//    private static final String UPDATED_NUMERO = "BBBBBBBBBB";
//
//    private static final LocalDate DEFAULT_DATA_ASSINATURA = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_DATA_ASSINATURA = LocalDate.now(ZoneId.systemDefault());
//
//    private static final LocalDate DEFAULT_DATA_PUBLICACAO = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_DATA_PUBLICACAO = LocalDate.now(ZoneId.systemDefault());
//
//    private static final BigDecimal DEFAULT_VALOR_ACRESCIMO = new BigDecimal(1);
//    private static final BigDecimal UPDATED_VALOR_ACRESCIMO = new BigDecimal(2);
//
//    private static final BigDecimal DEFAULT_VALOR_SUPRESSAO = new BigDecimal(1);
//    private static final BigDecimal UPDATED_VALOR_SUPRESSAO = new BigDecimal(2);
//
//    private static final LocalDate DEFAULT_NOVA_DATA_FIM_VIGENCIA = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_NOVA_DATA_FIM_VIGENCIA = LocalDate.now(ZoneId.systemDefault());
//
//    private static final String DEFAULT_JUSTIFICATIVA = "AAAAAAAAAA";
//    private static final String UPDATED_JUSTIFICATIVA = "BBBBBBBBBB";
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
//    private TermoAditivoRepository termoAditivoRepository;
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
//    private MockMvc restTermoAditivoMockMvc;
//
//    private TermoAditivo termoAditivo;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final TermoAditivoResource termoAditivoResource = new TermoAditivoResource(termoAditivoRepository);
//        this.restTermoAditivoMockMvc = MockMvcBuilders.standaloneSetup(termoAditivoResource)
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
//    public static TermoAditivo createEntity(EntityManager em) {
//        TermoAditivo termoAditivo = new TermoAditivo()
//            .inTipoDeAditivo(DEFAULT_IN_TIPO_DE_ADITIVO)
//            .inAlteracaoVigencia(DEFAULT_IN_ALTERACAO_VIGENCIA)
//            .inAlteracaoClausula(DEFAULT_IN_ALTERACAO_CLAUSULA)
//            .inAlteracaoObjeto(DEFAULT_IN_ALTERACAO_OBJETO)
//            .numero(DEFAULT_NUMERO)
//            .dataAssinatura(DEFAULT_DATA_ASSINATURA)
//            .dataPublicacao(DEFAULT_DATA_PUBLICACAO)
//            .valorAcrescimo(DEFAULT_VALOR_ACRESCIMO)
//            .valorSupressao(DEFAULT_VALOR_SUPRESSAO)
//            .novaDataFimVigencia(DEFAULT_NOVA_DATA_FIM_VIGENCIA)
//            .justificativa(DEFAULT_JUSTIFICATIVA)
//            .versao(DEFAULT_VERSAO)
//            .adtLogin(DEFAULT_ADT_LOGIN)
//            .adtDataHora(DEFAULT_ADT_DATA_HORA)
//            .adtOperacao(DEFAULT_ADT_OPERACAO);
//        return termoAditivo;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static TermoAditivo createUpdatedEntity(EntityManager em) {
//        TermoAditivo termoAditivo = new TermoAditivo()
//            .inTipoDeAditivo(UPDATED_IN_TIPO_DE_ADITIVO)
//            .inAlteracaoVigencia(UPDATED_IN_ALTERACAO_VIGENCIA)
//            .inAlteracaoClausula(UPDATED_IN_ALTERACAO_CLAUSULA)
//            .inAlteracaoObjeto(UPDATED_IN_ALTERACAO_OBJETO)
//            .numero(UPDATED_NUMERO)
//            .dataAssinatura(UPDATED_DATA_ASSINATURA)
//            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
//            .valorAcrescimo(UPDATED_VALOR_ACRESCIMO)
//            .valorSupressao(UPDATED_VALOR_SUPRESSAO)
//            .novaDataFimVigencia(UPDATED_NOVA_DATA_FIM_VIGENCIA)
//            .justificativa(UPDATED_JUSTIFICATIVA)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//        return termoAditivo;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        termoAditivo = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createTermoAditivo() throws Exception {
//        int databaseSizeBeforeCreate = termoAditivoRepository.findAll().size();
//
//        // Create the TermoAditivo
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isCreated());
//
//        // Validate the TermoAditivo in the database
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeCreate + 1);
//        TermoAditivo testTermoAditivo = termoAditivoList.get(termoAditivoList.size() - 1);
//        assertThat(testTermoAditivo.getInTipoDeAditivo()).isEqualTo(DEFAULT_IN_TIPO_DE_ADITIVO);
//        assertThat(testTermoAditivo.isInAlteracaoVigencia()).isEqualTo(DEFAULT_IN_ALTERACAO_VIGENCIA);
//        assertThat(testTermoAditivo.isInAlteracaoClausula()).isEqualTo(DEFAULT_IN_ALTERACAO_CLAUSULA);
//        assertThat(testTermoAditivo.isInAlteracaoObjeto()).isEqualTo(DEFAULT_IN_ALTERACAO_OBJETO);
//        assertThat(testTermoAditivo.getNumero()).isEqualTo(DEFAULT_NUMERO);
//        assertThat(testTermoAditivo.getDataAssinatura()).isEqualTo(DEFAULT_DATA_ASSINATURA);
//        assertThat(testTermoAditivo.getDataPublicacao()).isEqualTo(DEFAULT_DATA_PUBLICACAO);
//        assertThat(testTermoAditivo.getValorAcrescimo()).isEqualTo(DEFAULT_VALOR_ACRESCIMO);
//        assertThat(testTermoAditivo.getValorSupressao()).isEqualTo(DEFAULT_VALOR_SUPRESSAO);
//        assertThat(testTermoAditivo.getNovaDataFimVigencia()).isEqualTo(DEFAULT_NOVA_DATA_FIM_VIGENCIA);
//        assertThat(testTermoAditivo.getJustificativa()).isEqualTo(DEFAULT_JUSTIFICATIVA);
//        assertThat(testTermoAditivo.getVersao()).isEqualTo(DEFAULT_VERSAO);
//        assertThat(testTermoAditivo.getAdtLogin()).isEqualTo(DEFAULT_ADT_LOGIN);
//        assertThat(testTermoAditivo.getAdtDataHora()).isEqualTo(DEFAULT_ADT_DATA_HORA);
//        assertThat(testTermoAditivo.getAdtOperacao()).isEqualTo(DEFAULT_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void createTermoAditivoWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = termoAditivoRepository.findAll().size();
//
//        // Create the TermoAditivo with an existing ID
//        termoAditivo.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the TermoAditivo in the database
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkInTipoDeAditivoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setInTipoDeAditivo(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkInAlteracaoVigenciaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setInAlteracaoVigencia(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkInAlteracaoClausulaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setInAlteracaoClausula(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkInAlteracaoObjetoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setInAlteracaoObjeto(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkNumeroIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setNumero(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkDataAssinaturaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setDataAssinatura(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkDataPublicacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setDataPublicacao(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkValorAcrescimoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setValorAcrescimo(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkValorSupressaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setValorSupressao(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkNovaDataFimVigenciaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setNovaDataFimVigencia(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkJustificativaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setJustificativa(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVersaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setVersao(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtLoginIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setAdtLogin(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtDataHoraIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setAdtDataHora(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtOperacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = termoAditivoRepository.findAll().size();
//        // set the field null
//        termoAditivo.setAdtOperacao(null);
//
//        // Create the TermoAditivo, which fails.
//
//        restTermoAditivoMockMvc.perform(post("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTermoAditivos() throws Exception {
//        // Initialize the database
//        termoAditivoRepository.saveAndFlush(termoAditivo);
//
//        // Get all the termoAditivoList
//        restTermoAditivoMockMvc.perform(get("/api/termo-aditivos?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(termoAditivo.getId().intValue())))
//            .andExpect(jsonPath("$.[*].inTipoDeAditivo").value(hasItem(DEFAULT_IN_TIPO_DE_ADITIVO.toString())))
//            .andExpect(jsonPath("$.[*].inAlteracaoVigencia").value(hasItem(DEFAULT_IN_ALTERACAO_VIGENCIA.booleanValue())))
//            .andExpect(jsonPath("$.[*].inAlteracaoClausula").value(hasItem(DEFAULT_IN_ALTERACAO_CLAUSULA.booleanValue())))
//            .andExpect(jsonPath("$.[*].inAlteracaoObjeto").value(hasItem(DEFAULT_IN_ALTERACAO_OBJETO.booleanValue())))
//            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
//            .andExpect(jsonPath("$.[*].dataAssinatura").value(hasItem(DEFAULT_DATA_ASSINATURA.toString())))
//            .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO.toString())))
//            .andExpect(jsonPath("$.[*].valorAcrescimo").value(hasItem(DEFAULT_VALOR_ACRESCIMO.intValue())))
//            .andExpect(jsonPath("$.[*].valorSupressao").value(hasItem(DEFAULT_VALOR_SUPRESSAO.intValue())))
//            .andExpect(jsonPath("$.[*].novaDataFimVigencia").value(hasItem(DEFAULT_NOVA_DATA_FIM_VIGENCIA.toString())))
//            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA.toString())))
//            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
//            .andExpect(jsonPath("$.[*].adtLogin").value(hasItem(DEFAULT_ADT_LOGIN.toString())))
//            .andExpect(jsonPath("$.[*].adtDataHora").value(hasItem(DEFAULT_ADT_DATA_HORA.toString())))
//            .andExpect(jsonPath("$.[*].adtOperacao").value(hasItem(DEFAULT_ADT_OPERACAO.toString())));
//    }
//    
//    @Test
//    @Transactional
//    public void getTermoAditivo() throws Exception {
//        // Initialize the database
//        termoAditivoRepository.saveAndFlush(termoAditivo);
//
//        // Get the termoAditivo
//        restTermoAditivoMockMvc.perform(get("/api/termo-aditivos/{id}", termoAditivo.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(termoAditivo.getId().intValue()))
//            .andExpect(jsonPath("$.inTipoDeAditivo").value(DEFAULT_IN_TIPO_DE_ADITIVO.toString()))
//            .andExpect(jsonPath("$.inAlteracaoVigencia").value(DEFAULT_IN_ALTERACAO_VIGENCIA.booleanValue()))
//            .andExpect(jsonPath("$.inAlteracaoClausula").value(DEFAULT_IN_ALTERACAO_CLAUSULA.booleanValue()))
//            .andExpect(jsonPath("$.inAlteracaoObjeto").value(DEFAULT_IN_ALTERACAO_OBJETO.booleanValue()))
//            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
//            .andExpect(jsonPath("$.dataAssinatura").value(DEFAULT_DATA_ASSINATURA.toString()))
//            .andExpect(jsonPath("$.dataPublicacao").value(DEFAULT_DATA_PUBLICACAO.toString()))
//            .andExpect(jsonPath("$.valorAcrescimo").value(DEFAULT_VALOR_ACRESCIMO.intValue()))
//            .andExpect(jsonPath("$.valorSupressao").value(DEFAULT_VALOR_SUPRESSAO.intValue()))
//            .andExpect(jsonPath("$.novaDataFimVigencia").value(DEFAULT_NOVA_DATA_FIM_VIGENCIA.toString()))
//            .andExpect(jsonPath("$.justificativa").value(DEFAULT_JUSTIFICATIVA.toString()))
//            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
//            .andExpect(jsonPath("$.adtLogin").value(DEFAULT_ADT_LOGIN.toString()))
//            .andExpect(jsonPath("$.adtDataHora").value(DEFAULT_ADT_DATA_HORA.toString()))
//            .andExpect(jsonPath("$.adtOperacao").value(DEFAULT_ADT_OPERACAO.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingTermoAditivo() throws Exception {
//        // Get the termoAditivo
//        restTermoAditivoMockMvc.perform(get("/api/termo-aditivos/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateTermoAditivo() throws Exception {
//        // Initialize the database
//        termoAditivoRepository.saveAndFlush(termoAditivo);
//
//        int databaseSizeBeforeUpdate = termoAditivoRepository.findAll().size();
//
//        // Update the termoAditivo
//        TermoAditivo updatedTermoAditivo = termoAditivoRepository.findById(termoAditivo.getId()).get();
//        // Disconnect from session so that the updates on updatedTermoAditivo are not directly saved in db
//        em.detach(updatedTermoAditivo);
//        updatedTermoAditivo
//            .inTipoDeAditivo(UPDATED_IN_TIPO_DE_ADITIVO)
//            .inAlteracaoVigencia(UPDATED_IN_ALTERACAO_VIGENCIA)
//            .inAlteracaoClausula(UPDATED_IN_ALTERACAO_CLAUSULA)
//            .inAlteracaoObjeto(UPDATED_IN_ALTERACAO_OBJETO)
//            .numero(UPDATED_NUMERO)
//            .dataAssinatura(UPDATED_DATA_ASSINATURA)
//            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
//            .valorAcrescimo(UPDATED_VALOR_ACRESCIMO)
//            .valorSupressao(UPDATED_VALOR_SUPRESSAO)
//            .novaDataFimVigencia(UPDATED_NOVA_DATA_FIM_VIGENCIA)
//            .justificativa(UPDATED_JUSTIFICATIVA)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//
//        restTermoAditivoMockMvc.perform(put("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedTermoAditivo)))
//            .andExpect(status().isOk());
//
//        // Validate the TermoAditivo in the database
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeUpdate);
//        TermoAditivo testTermoAditivo = termoAditivoList.get(termoAditivoList.size() - 1);
//        assertThat(testTermoAditivo.getInTipoDeAditivo()).isEqualTo(UPDATED_IN_TIPO_DE_ADITIVO);
//        assertThat(testTermoAditivo.isInAlteracaoVigencia()).isEqualTo(UPDATED_IN_ALTERACAO_VIGENCIA);
//        assertThat(testTermoAditivo.isInAlteracaoClausula()).isEqualTo(UPDATED_IN_ALTERACAO_CLAUSULA);
//        assertThat(testTermoAditivo.isInAlteracaoObjeto()).isEqualTo(UPDATED_IN_ALTERACAO_OBJETO);
//        assertThat(testTermoAditivo.getNumero()).isEqualTo(UPDATED_NUMERO);
//        assertThat(testTermoAditivo.getDataAssinatura()).isEqualTo(UPDATED_DATA_ASSINATURA);
//        assertThat(testTermoAditivo.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
//        assertThat(testTermoAditivo.getValorAcrescimo()).isEqualTo(UPDATED_VALOR_ACRESCIMO);
//        assertThat(testTermoAditivo.getValorSupressao()).isEqualTo(UPDATED_VALOR_SUPRESSAO);
//        assertThat(testTermoAditivo.getNovaDataFimVigencia()).isEqualTo(UPDATED_NOVA_DATA_FIM_VIGENCIA);
//        assertThat(testTermoAditivo.getJustificativa()).isEqualTo(UPDATED_JUSTIFICATIVA);
//        assertThat(testTermoAditivo.getVersao()).isEqualTo(UPDATED_VERSAO);
//        assertThat(testTermoAditivo.getAdtLogin()).isEqualTo(UPDATED_ADT_LOGIN);
//        assertThat(testTermoAditivo.getAdtDataHora()).isEqualTo(UPDATED_ADT_DATA_HORA);
//        assertThat(testTermoAditivo.getAdtOperacao()).isEqualTo(UPDATED_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingTermoAditivo() throws Exception {
//        int databaseSizeBeforeUpdate = termoAditivoRepository.findAll().size();
//
//        // Create the TermoAditivo
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restTermoAditivoMockMvc.perform(put("/api/termo-aditivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(termoAditivo)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the TermoAditivo in the database
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteTermoAditivo() throws Exception {
//        // Initialize the database
//        termoAditivoRepository.saveAndFlush(termoAditivo);
//
//        int databaseSizeBeforeDelete = termoAditivoRepository.findAll().size();
//
//        // Delete the termoAditivo
//        restTermoAditivoMockMvc.perform(delete("/api/termo-aditivos/{id}", termoAditivo.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<TermoAditivo> termoAditivoList = termoAditivoRepository.findAll();
//        assertThat(termoAditivoList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(TermoAditivo.class);
//        TermoAditivo termoAditivo1 = new TermoAditivo();
//        termoAditivo1.setId(1L);
//        TermoAditivo termoAditivo2 = new TermoAditivo();
//        termoAditivo2.setId(termoAditivo1.getId());
//        assertThat(termoAditivo1).isEqualTo(termoAditivo2);
//        termoAditivo2.setId(2L);
//        assertThat(termoAditivo1).isNotEqualTo(termoAditivo2);
//        termoAditivo1.setId(null);
//        assertThat(termoAditivo1).isNotEqualTo(termoAditivo2);
//    }
//}
