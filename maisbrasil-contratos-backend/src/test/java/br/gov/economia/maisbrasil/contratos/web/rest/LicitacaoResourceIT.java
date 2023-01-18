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
//import br.gov.economia.maisbrasil.contratos.domain.Licitacao;
//import br.gov.economia.maisbrasil.contratos.repository.LicitacaoRepository;
//import br.gov.economia.maisbrasil.contratos.web.rest.errors.ExceptionTranslator;
//
///**
// * Integration tests for the {@Link LicitacaoResource} REST controller.
// */
//@Disabled
//@SpringBootTest(classes = MaisbrasilContratosBackendApp.class)
//public class LicitacaoResourceIT {
//
//    private static final String DEFAULT_NUMERO_ANO = "AAAAAAAAAA";
//    private static final String UPDATED_NUMERO_ANO = "BBBBBBBBBB";
//
//    private static final String DEFAULT_OBJETO = "AAAAAAAAAA";
//    private static final String UPDATED_OBJETO = "BBBBBBBBBB";
//
//    private static final BigDecimal DEFAULT_VALOR_PROCESSO = new BigDecimal(1);
//    private static final BigDecimal UPDATED_VALOR_PROCESSO = new BigDecimal(2);
//
//    private static final String DEFAULT_STATUS_PROCESSO = "AAAAAAAAAA";
//    private static final String UPDATED_STATUS_PROCESSO = "BBBBBBBBBB";
//
//    private static final Integer DEFAULT_ID_LICITACAO_FK = 1;
//    private static final Integer UPDATED_ID_LICITACAO_FK = 2;
//
//    private static final String DEFAULT_IN_SITUACAO = "AAA";
//    private static final String UPDATED_IN_SITUACAO = "BBB";
//
//    private static final String DEFAULT_MODALIDADE = "AAAAAAAAAA";
//    private static final String UPDATED_MODALIDADE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_REGIME_CONTRATACAO = "AAAAAAAAAA";
//    private static final String UPDATED_REGIME_CONTRATACAO = "BBBBBBBBBB";
//
//    private static final LocalDate DEFAULT_DATA_PUBLICACAO = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_DATA_PUBLICACAO = LocalDate.now(ZoneId.systemDefault());
//
//    private static final LocalDate DEFAULT_DATA_HOMOLOGACAO = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_DATA_HOMOLOGACAO = LocalDate.now(ZoneId.systemDefault());
//
//    private static final String DEFAULT_PROCESSO_DE_EXECUCAO = "AAAAAAAAAA";
//    private static final String UPDATED_PROCESSO_DE_EXECUCAO = "BBBBBBBBBB";
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
//    private LicitacaoRepository licitacaoRepository;
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
//    private MockMvc restLicitacaoMockMvc;
//
//    private Licitacao licitacao;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final LicitacaoResource licitacaoResource = new LicitacaoResource(licitacaoRepository);
//        this.restLicitacaoMockMvc = MockMvcBuilders.standaloneSetup(licitacaoResource)
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
//    public static Licitacao createEntity(EntityManager em) {
//        Licitacao licitacao = new Licitacao()
//            .numeroAno(DEFAULT_NUMERO_ANO)
//            .objeto(DEFAULT_OBJETO)
//            .valorProcesso(DEFAULT_VALOR_PROCESSO)
//            .statusProcesso(DEFAULT_STATUS_PROCESSO)
//            .idLicitacaoFk(DEFAULT_ID_LICITACAO_FK)
//            .inSituacao(DEFAULT_IN_SITUACAO)
//            .modalidade(DEFAULT_MODALIDADE)
//            .regimeContratacao(DEFAULT_REGIME_CONTRATACAO)
//            .dataPublicacao(DEFAULT_DATA_PUBLICACAO)
//            .dataHomologacao(DEFAULT_DATA_HOMOLOGACAO)
//            .processoDeExecucao(DEFAULT_PROCESSO_DE_EXECUCAO)
//            .versao(DEFAULT_VERSAO)
//            .adtLogin(DEFAULT_ADT_LOGIN)
//            .adtDataHora(DEFAULT_ADT_DATA_HORA)
//            .adtOperacao(DEFAULT_ADT_OPERACAO);
//        return licitacao;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Licitacao createUpdatedEntity(EntityManager em) {
//        Licitacao licitacao = new Licitacao()
//            .numeroAno(UPDATED_NUMERO_ANO)
//            .objeto(UPDATED_OBJETO)
//            .valorProcesso(UPDATED_VALOR_PROCESSO)
//            .statusProcesso(UPDATED_STATUS_PROCESSO)
//            .idLicitacaoFk(UPDATED_ID_LICITACAO_FK)
//            .inSituacao(UPDATED_IN_SITUACAO)
//            .modalidade(UPDATED_MODALIDADE)
//            .regimeContratacao(UPDATED_REGIME_CONTRATACAO)
//            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
//            .dataHomologacao(UPDATED_DATA_HOMOLOGACAO)
//            .processoDeExecucao(UPDATED_PROCESSO_DE_EXECUCAO)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//        return licitacao;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        licitacao = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createLicitacao() throws Exception {
//        int databaseSizeBeforeCreate = licitacaoRepository.findAll().size();
//
//        // Create the Licitacao
//        restLicitacaoMockMvc.perform(post("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(licitacao)))
//            .andExpect(status().isCreated());
//
//        // Validate the Licitacao in the database
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeCreate + 1);
//        Licitacao testLicitacao = licitacaoList.get(licitacaoList.size() - 1);
//        assertThat(testLicitacao.getNumeroAno()).isEqualTo(DEFAULT_NUMERO_ANO);
//        assertThat(testLicitacao.getObjeto()).isEqualTo(DEFAULT_OBJETO);
//        assertThat(testLicitacao.getValorProcesso()).isEqualTo(DEFAULT_VALOR_PROCESSO);
//        assertThat(testLicitacao.getStatusProcesso()).isEqualTo(DEFAULT_STATUS_PROCESSO);
//        assertThat(testLicitacao.getIdLicitacaoFk()).isEqualTo(DEFAULT_ID_LICITACAO_FK);
//        assertThat(testLicitacao.getInSituacao()).isEqualTo(DEFAULT_IN_SITUACAO);
//        assertThat(testLicitacao.getModalidade()).isEqualTo(DEFAULT_MODALIDADE);
//        assertThat(testLicitacao.getRegimeContratacao()).isEqualTo(DEFAULT_REGIME_CONTRATACAO);
//        assertThat(testLicitacao.getDataPublicacao()).isEqualTo(DEFAULT_DATA_PUBLICACAO);
//        assertThat(testLicitacao.getDataHomologacao()).isEqualTo(DEFAULT_DATA_HOMOLOGACAO);
//        assertThat(testLicitacao.getProcessoDeExecucao()).isEqualTo(DEFAULT_PROCESSO_DE_EXECUCAO);
//        assertThat(testLicitacao.getVersao()).isEqualTo(DEFAULT_VERSAO);
//        assertThat(testLicitacao.getAdtLogin()).isEqualTo(DEFAULT_ADT_LOGIN);
//        assertThat(testLicitacao.getAdtDataHora()).isEqualTo(DEFAULT_ADT_DATA_HORA);
//        assertThat(testLicitacao.getAdtOperacao()).isEqualTo(DEFAULT_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void createLicitacaoWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = licitacaoRepository.findAll().size();
//
//        // Create the Licitacao with an existing ID
//        licitacao.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restLicitacaoMockMvc.perform(post("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(licitacao)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Licitacao in the database
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkNumeroAnoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = licitacaoRepository.findAll().size();
//        // set the field null
//        licitacao.setNumeroAno(null);
//
//        // Create the Licitacao, which fails.
//
//        restLicitacaoMockMvc.perform(post("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(licitacao)))
//            .andExpect(status().isBadRequest());
//
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkObjetoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = licitacaoRepository.findAll().size();
//        // set the field null
//        licitacao.setObjeto(null);
//
//        // Create the Licitacao, which fails.
//
//        restLicitacaoMockMvc.perform(post("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(licitacao)))
//            .andExpect(status().isBadRequest());
//
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkValorProcessoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = licitacaoRepository.findAll().size();
//        // set the field null
//        licitacao.setValorProcesso(null);
//
//        // Create the Licitacao, which fails.
//
//        restLicitacaoMockMvc.perform(post("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(licitacao)))
//            .andExpect(status().isBadRequest());
//
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkStatusProcessoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = licitacaoRepository.findAll().size();
//        // set the field null
//        licitacao.setStatusProcesso(null);
//
//        // Create the Licitacao, which fails.
//
//        restLicitacaoMockMvc.perform(post("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(licitacao)))
//            .andExpect(status().isBadRequest());
//
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkIdLicitacaoFkIsRequired() throws Exception {
//        int databaseSizeBeforeTest = licitacaoRepository.findAll().size();
//        // set the field null
//        licitacao.setIdLicitacaoFk(null);
//
//        // Create the Licitacao, which fails.
//
//        restLicitacaoMockMvc.perform(post("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(licitacao)))
//            .andExpect(status().isBadRequest());
//
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkInSituacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = licitacaoRepository.findAll().size();
//        // set the field null
//        licitacao.setInSituacao(null);
//
//        // Create the Licitacao, which fails.
//
//        restLicitacaoMockMvc.perform(post("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(licitacao)))
//            .andExpect(status().isBadRequest());
//
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVersaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = licitacaoRepository.findAll().size();
//        // set the field null
//        licitacao.setVersao(null);
//
//        // Create the Licitacao, which fails.
//
//        restLicitacaoMockMvc.perform(post("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(licitacao)))
//            .andExpect(status().isBadRequest());
//
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtLoginIsRequired() throws Exception {
//        int databaseSizeBeforeTest = licitacaoRepository.findAll().size();
//        // set the field null
//        licitacao.setAdtLogin(null);
//
//        // Create the Licitacao, which fails.
//
//        restLicitacaoMockMvc.perform(post("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(licitacao)))
//            .andExpect(status().isBadRequest());
//
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtDataHoraIsRequired() throws Exception {
//        int databaseSizeBeforeTest = licitacaoRepository.findAll().size();
//        // set the field null
//        licitacao.setAdtDataHora(null);
//
//        // Create the Licitacao, which fails.
//
//        restLicitacaoMockMvc.perform(post("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(licitacao)))
//            .andExpect(status().isBadRequest());
//
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtOperacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = licitacaoRepository.findAll().size();
//        // set the field null
//        licitacao.setAdtOperacao(null);
//
//        // Create the Licitacao, which fails.
//
//        restLicitacaoMockMvc.perform(post("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(licitacao)))
//            .andExpect(status().isBadRequest());
//
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllLicitacaos() throws Exception {
//        // Initialize the database
//        licitacaoRepository.saveAndFlush(licitacao);
//
//        // Get all the licitacaoList
//        restLicitacaoMockMvc.perform(get("/api/licitacaos?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(licitacao.getId().intValue())))
//            .andExpect(jsonPath("$.[*].numeroAno").value(hasItem(DEFAULT_NUMERO_ANO.toString())))
//            .andExpect(jsonPath("$.[*].objeto").value(hasItem(DEFAULT_OBJETO.toString())))
//            .andExpect(jsonPath("$.[*].valorProcesso").value(hasItem(DEFAULT_VALOR_PROCESSO.intValue())))
//            .andExpect(jsonPath("$.[*].statusProcesso").value(hasItem(DEFAULT_STATUS_PROCESSO.toString())))
//            .andExpect(jsonPath("$.[*].idLicitacaoFk").value(hasItem(DEFAULT_ID_LICITACAO_FK)))
//            .andExpect(jsonPath("$.[*].inSituacao").value(hasItem(DEFAULT_IN_SITUACAO.toString())))
//            .andExpect(jsonPath("$.[*].modalidade").value(hasItem(DEFAULT_MODALIDADE.toString())))
//            .andExpect(jsonPath("$.[*].regimeContratacao").value(hasItem(DEFAULT_REGIME_CONTRATACAO.toString())))
//            .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO.toString())))
//            .andExpect(jsonPath("$.[*].dataHomologacao").value(hasItem(DEFAULT_DATA_HOMOLOGACAO.toString())))
//            .andExpect(jsonPath("$.[*].processoDeExecucao").value(hasItem(DEFAULT_PROCESSO_DE_EXECUCAO.toString())))
//            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
//            .andExpect(jsonPath("$.[*].adtLogin").value(hasItem(DEFAULT_ADT_LOGIN.toString())))
//            .andExpect(jsonPath("$.[*].adtDataHora").value(hasItem(DEFAULT_ADT_DATA_HORA.toString())))
//            .andExpect(jsonPath("$.[*].adtOperacao").value(hasItem(DEFAULT_ADT_OPERACAO.toString())));
//    }
//    
//    @Test
//    @Transactional
//    public void getLicitacao() throws Exception {
//        // Initialize the database
//        licitacaoRepository.saveAndFlush(licitacao);
//
//        // Get the licitacao
//        restLicitacaoMockMvc.perform(get("/api/licitacaos/{id}", licitacao.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(licitacao.getId().intValue()))
//            .andExpect(jsonPath("$.numeroAno").value(DEFAULT_NUMERO_ANO.toString()))
//            .andExpect(jsonPath("$.objeto").value(DEFAULT_OBJETO.toString()))
//            .andExpect(jsonPath("$.valorProcesso").value(DEFAULT_VALOR_PROCESSO.intValue()))
//            .andExpect(jsonPath("$.statusProcesso").value(DEFAULT_STATUS_PROCESSO.toString()))
//            .andExpect(jsonPath("$.idLicitacaoFk").value(DEFAULT_ID_LICITACAO_FK))
//            .andExpect(jsonPath("$.inSituacao").value(DEFAULT_IN_SITUACAO.toString()))
//            .andExpect(jsonPath("$.modalidade").value(DEFAULT_MODALIDADE.toString()))
//            .andExpect(jsonPath("$.regimeContratacao").value(DEFAULT_REGIME_CONTRATACAO.toString()))
//            .andExpect(jsonPath("$.dataPublicacao").value(DEFAULT_DATA_PUBLICACAO.toString()))
//            .andExpect(jsonPath("$.dataHomologacao").value(DEFAULT_DATA_HOMOLOGACAO.toString()))
//            .andExpect(jsonPath("$.processoDeExecucao").value(DEFAULT_PROCESSO_DE_EXECUCAO.toString()))
//            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
//            .andExpect(jsonPath("$.adtLogin").value(DEFAULT_ADT_LOGIN.toString()))
//            .andExpect(jsonPath("$.adtDataHora").value(DEFAULT_ADT_DATA_HORA.toString()))
//            .andExpect(jsonPath("$.adtOperacao").value(DEFAULT_ADT_OPERACAO.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingLicitacao() throws Exception {
//        // Get the licitacao
//        restLicitacaoMockMvc.perform(get("/api/licitacaos/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateLicitacao() throws Exception {
//        // Initialize the database
//        licitacaoRepository.saveAndFlush(licitacao);
//
//        int databaseSizeBeforeUpdate = licitacaoRepository.findAll().size();
//
//        // Update the licitacao
//        Licitacao updatedLicitacao = licitacaoRepository.findById(licitacao.getId()).get();
//        // Disconnect from session so that the updates on updatedLicitacao are not directly saved in db
//        em.detach(updatedLicitacao);
//        updatedLicitacao
//            .numeroAno(UPDATED_NUMERO_ANO)
//            .objeto(UPDATED_OBJETO)
//            .valorProcesso(UPDATED_VALOR_PROCESSO)
//            .statusProcesso(UPDATED_STATUS_PROCESSO)
//            .idLicitacaoFk(UPDATED_ID_LICITACAO_FK)
//            .inSituacao(UPDATED_IN_SITUACAO)
//            .modalidade(UPDATED_MODALIDADE)
//            .regimeContratacao(UPDATED_REGIME_CONTRATACAO)
//            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
//            .dataHomologacao(UPDATED_DATA_HOMOLOGACAO)
//            .processoDeExecucao(UPDATED_PROCESSO_DE_EXECUCAO)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//
//        restLicitacaoMockMvc.perform(put("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedLicitacao)))
//            .andExpect(status().isOk());
//
//        // Validate the Licitacao in the database
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeUpdate);
//        Licitacao testLicitacao = licitacaoList.get(licitacaoList.size() - 1);
//        assertThat(testLicitacao.getNumeroAno()).isEqualTo(UPDATED_NUMERO_ANO);
//        assertThat(testLicitacao.getObjeto()).isEqualTo(UPDATED_OBJETO);
//        assertThat(testLicitacao.getValorProcesso()).isEqualTo(UPDATED_VALOR_PROCESSO);
//        assertThat(testLicitacao.getStatusProcesso()).isEqualTo(UPDATED_STATUS_PROCESSO);
//        assertThat(testLicitacao.getIdLicitacaoFk()).isEqualTo(UPDATED_ID_LICITACAO_FK);
//        assertThat(testLicitacao.getInSituacao()).isEqualTo(UPDATED_IN_SITUACAO);
//        assertThat(testLicitacao.getModalidade()).isEqualTo(UPDATED_MODALIDADE);
//        assertThat(testLicitacao.getRegimeContratacao()).isEqualTo(UPDATED_REGIME_CONTRATACAO);
//        assertThat(testLicitacao.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
//        assertThat(testLicitacao.getDataHomologacao()).isEqualTo(UPDATED_DATA_HOMOLOGACAO);
//        assertThat(testLicitacao.getProcessoDeExecucao()).isEqualTo(UPDATED_PROCESSO_DE_EXECUCAO);
//        assertThat(testLicitacao.getVersao()).isEqualTo(UPDATED_VERSAO);
//        assertThat(testLicitacao.getAdtLogin()).isEqualTo(UPDATED_ADT_LOGIN);
//        assertThat(testLicitacao.getAdtDataHora()).isEqualTo(UPDATED_ADT_DATA_HORA);
//        assertThat(testLicitacao.getAdtOperacao()).isEqualTo(UPDATED_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingLicitacao() throws Exception {
//        int databaseSizeBeforeUpdate = licitacaoRepository.findAll().size();
//
//        // Create the Licitacao
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restLicitacaoMockMvc.perform(put("/api/licitacaos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(licitacao)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Licitacao in the database
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteLicitacao() throws Exception {
//        // Initialize the database
//        licitacaoRepository.saveAndFlush(licitacao);
//
//        int databaseSizeBeforeDelete = licitacaoRepository.findAll().size();
//
//        // Delete the licitacao
//        restLicitacaoMockMvc.perform(delete("/api/licitacaos/{id}", licitacao.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Licitacao> licitacaoList = licitacaoRepository.findAll();
//        assertThat(licitacaoList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Licitacao.class);
//        Licitacao licitacao1 = new Licitacao();
//        licitacao1.setId(1L);
//        Licitacao licitacao2 = new Licitacao();
//        licitacao2.setId(licitacao1.getId());
//        assertThat(licitacao1).isEqualTo(licitacao2);
//        licitacao2.setId(2L);
//        assertThat(licitacao1).isNotEqualTo(licitacao2);
//        licitacao1.setId(null);
//        assertThat(licitacao1).isNotEqualTo(licitacao2);
//    }
//}
