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
//import org.junit.jupiter.api.BeforeEach;
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
//import br.gov.economia.maisbrasil.contratos.bc.ContratoBC;
//import br.gov.economia.maisbrasil.contratos.domain.Contrato;
//import br.gov.economia.maisbrasil.contratos.domain.bd.ContratoBD;
//import br.gov.economia.maisbrasil.contratos.domain.dto.ContratoDTO;
//import br.gov.economia.maisbrasil.contratos.repository.ContratoRepository;
//import br.gov.economia.maisbrasil.contratos.web.rest.errors.ExceptionTranslator;
//
///**
// * Integration tests for the {@Link ContratoResource} REST controller.
// */
//
//@SpringBootTest(classes = MaisbrasilContratosBackendApp.class)
//public class ContratoResourceIT {
//
//    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
//    private static final String UPDATED_NUMERO = "BBBBBBBBBB";
//
//    private static final String DEFAULT_OBJETO = "AAAAAAAAAA";
//    private static final String UPDATED_OBJETO = "BBBBBBBBBB";
//
//    private static final LocalDate DEFAULT_DATA_ASSINATURA = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_DATA_ASSINATURA = LocalDate.now(ZoneId.systemDefault());
//
//    private static final LocalDate DEFAULT_DATA_PUBLICACAO = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_DATA_PUBLICACAO = LocalDate.now(ZoneId.systemDefault());
//
//    private static final LocalDate DEFAULT_INICIO_VIGENCIA = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_INICIO_VIGENCIA = LocalDate.now(ZoneId.systemDefault());
//
//    private static final LocalDate DEFAULT_FIM_VIGENCIA = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_FIM_VIGENCIA = LocalDate.now(ZoneId.systemDefault());
//
//    private static final BigDecimal DEFAULT_VALOR_TOTAL = new BigDecimal(1);
//    private static final BigDecimal UPDATED_VALOR_TOTAL = new BigDecimal(2);
//
//    private static final String DEFAULT_IN_SITUACAO = "AAAAAAAAAA";
//    private static final String UPDATED_IN_SITUACAO = "BBBBBBBBBB";
//
//    private static final Boolean DEFAULT_APTO_A_INICIAR = false;
//    private static final Boolean UPDATED_APTO_A_INICIAR = true;
//
//    private static final Long DEFAULT_VERSAO = 1L;
//    private static final Long UPDATED_VERSAO = 2L;
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
//    private ContratoBC contratoBC;
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
//    private Validator validator;
//
//    private MockMvc restContratoMockMvc;
//
//    private ContratoBD contrato;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final ContratoResource contratoResource = new ContratoResource(contratoBC);
//        this.restContratoMockMvc = MockMvcBuilders.standaloneSetup(contratoResource)
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
//    public static ContratoBD createEntity() {
//        ContratoBD contrato = new ContratoBD();
//        contrato.setNumero(DEFAULT_NUMERO);
//        contrato.setObjeto(DEFAULT_OBJETO);
//        contrato.setDataAssinatura(DEFAULT_DATA_ASSINATURA);
//        contrato.setDataPublicacao(DEFAULT_DATA_PUBLICACAO);
//        contrato.setInicioVigencia(DEFAULT_INICIO_VIGENCIA);
//        contrato.setFimVigencia(DEFAULT_FIM_VIGENCIA);
//        contrato.setValorTotal(DEFAULT_VALOR_TOTAL);
//        contrato.setInSituacao(DEFAULT_IN_SITUACAO);
//        contrato.setAptoAIniciar(DEFAULT_APTO_A_INICIAR);
//        contrato.setVersao(DEFAULT_VERSAO);
//        contrato.setAdtLogin(DEFAULT_ADT_LOGIN);
//        contrato.setAdtDataHora(DEFAULT_ADT_DATA_HORA);
//        contrato.setAdtOperacao(DEFAULT_ADT_OPERACAO);
//        return contrato;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static ContratoBD createUpdatedEntity() {
//    	ContratoBD contrato = new ContratoBD();
//    	
//        contrato.setNumero(UPDATED_NUMERO);
//        contrato.setObjeto(UPDATED_OBJETO);
//        contrato.setDataAssinatura(UPDATED_DATA_ASSINATURA);
//        contrato.setDataPublicacao(UPDATED_DATA_PUBLICACAO);
//        contrato.setInicioVigencia(UPDATED_INICIO_VIGENCIA);
//        contrato.setFimVigencia(UPDATED_FIM_VIGENCIA);
//        contrato.setValorTotal(UPDATED_VALOR_TOTAL);
//        contrato.setInSituacao(UPDATED_IN_SITUACAO);
//        contrato.setAptoAIniciar(UPDATED_APTO_A_INICIAR);
//        contrato.setVersao(UPDATED_VERSAO);
//        contrato.setAdtLogin(UPDATED_ADT_LOGIN);
//        contrato.setAdtDataHora(UPDATED_ADT_DATA_HORA);
//        contrato.setAdtOperacao(UPDATED_ADT_OPERACAO);
//
//        return contrato;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        contrato = createEntity();
//    }
//
//    @Test
//    @Transactional
//    public void createContrato() throws Exception {
//        int databaseSizeBeforeCreate = contratoBC.recuperarTodosContrato().size();
//
//        // Create the Contrato
//        restContratoMockMvc.perform(post("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(contrato)))
//            .andExpect(status().isCreated());
//
//        // Validate the Contrato in the database
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeCreate + 1);
//        ContratoDTO testContrato = contratoList.get(contratoList.size() - 1);
//        assertThat(testContrato.getNumero()).isEqualTo(DEFAULT_NUMERO);
//        assertThat(testContrato.getObjeto()).isEqualTo(DEFAULT_OBJETO);
//        assertThat(testContrato.getDataAssinatura()).isEqualTo(DEFAULT_DATA_ASSINATURA);
//        assertThat(testContrato.getDataPublicacao()).isEqualTo(DEFAULT_DATA_PUBLICACAO);
//        assertThat(testContrato.getInicioVigencia()).isEqualTo(DEFAULT_INICIO_VIGENCIA);
//        assertThat(testContrato.getFimVigencia()).isEqualTo(DEFAULT_FIM_VIGENCIA);
//        assertThat(testContrato.getValorTotal()).isEqualTo(DEFAULT_VALOR_TOTAL);
//        assertThat(testContrato.getInSituacao()).isEqualTo(DEFAULT_IN_SITUACAO);
//        assertThat(testContrato.getAptoAIniciar()).isEqualTo(DEFAULT_APTO_A_INICIAR);
//        assertThat(testContrato.getVersao()).isEqualTo(DEFAULT_VERSAO);
//    }
//
//    @Test
//    @Transactional
//    public void createContratoWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = contratoBC.recuperarTodosContrato().size();
//
//        // Create the Contrato with an existing ID
//        contrato.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restContratoMockMvc.perform(post("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(contrato)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Contrato in the database
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkNumeroIsRequired() throws Exception {
//        int databaseSizeBeforeTest = contratoBC.recuperarTodosContrato().size();
//        // set the field null
//        contrato.setNumero(null);
//
//        // Create the Contrato, which fails.
//
//        restContratoMockMvc.perform(post("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(contrato)))
//            .andExpect(status().isBadRequest());
//
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkObjetoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = contratoBC.recuperarTodosContrato().size();
//        // set the field null
//        contrato.setObjeto(null);
//
//        // Create the Contrato, which fails.
//
//        restContratoMockMvc.perform(post("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(contrato)))
//            .andExpect(status().isBadRequest());
//
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkDataAssinaturaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = contratoBC.recuperarTodosContrato().size();
//        // set the field null
//        contrato.setDataAssinatura(null);
//
//        // Create the Contrato, which fails.
//
//        restContratoMockMvc.perform(post("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(contrato)))
//            .andExpect(status().isBadRequest());
//
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkDataPublicacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = contratoBC.recuperarTodosContrato().size();
//        // set the field null
//        contrato.setDataPublicacao(null);
//
//        // Create the Contrato, which fails.
//
//        restContratoMockMvc.perform(post("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(contrato)))
//            .andExpect(status().isBadRequest());
//
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkInicioVigenciaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = contratoBC.recuperarTodosContrato().size();
//        // set the field null
//        contrato.setInicioVigencia(null);
//
//        // Create the Contrato, which fails.
//
//        restContratoMockMvc.perform(post("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(contrato)))
//            .andExpect(status().isBadRequest());
//
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkFimVigenciaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = contratoBC.recuperarTodosContrato().size();
//        // set the field null
//        contrato.setFimVigencia(null);
//
//        // Create the Contrato, which fails.
//
//        restContratoMockMvc.perform(post("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(contrato)))
//            .andExpect(status().isBadRequest());
//
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkValorTotalIsRequired() throws Exception {
//        int databaseSizeBeforeTest = contratoBC.recuperarTodosContrato().size();
//        // set the field null
//        contrato.setValorTotal(null);
//
//        // Create the Contrato, which fails.
//
//        restContratoMockMvc.perform(post("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(contrato)))
//            .andExpect(status().isBadRequest());
//
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkInSituacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = contratoBC.recuperarTodosContrato().size();
//        // set the field null
//        contrato.setInSituacao(null);
//
//        // Create the Contrato, which fails.
//
//        restContratoMockMvc.perform(post("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(contrato)))
//            .andExpect(status().isBadRequest());
//
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAptoAIniciarIsRequired() throws Exception {
//        int databaseSizeBeforeTest = contratoBC.recuperarTodosContrato().size();
//        // set the field null
//        contrato.setAptoAIniciar(null);
//
//        // Create the Contrato, which fails.
//
//        restContratoMockMvc.perform(post("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(contrato)))
//            .andExpect(status().isBadRequest());
//
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVersaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = contratoBC.recuperarTodosContrato().size();
//        // set the field null
//        contrato.setVersao(null);
//
//        // Create the Contrato, which fails.
//
//        restContratoMockMvc.perform(post("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(contrato)))
//            .andExpect(status().isBadRequest());
//
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
//    }
//
////    @Test
////    @Transactional
////    public void checkAdtLoginIsRequired() throws Exception {
////        int databaseSizeBeforeTest = contratoBC.recuperarTodosContrato().size();
////        // set the field null
////        contrato.setAdtLogin(null);
////
////        // Create the Contrato, which fails.
////
////        restContratoMockMvc.perform(post("/api/contratos")
////            .contentType(TestUtil.APPLICATION_JSON_UTF8)
////            .content(TestUtil.convertObjectToJsonBytes(contrato)))
////            .andExpect(status().isBadRequest());
////
////        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
////        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
////    }
////
////    @Test
////    @Transactional
////    public void checkAdtDataHoraIsRequired() throws Exception {
////        int databaseSizeBeforeTest = contratoBC.recuperarTodosContrato().size();
////        // set the field null
////        contrato.setAdtDataHora(null);
////
////        // Create the Contrato, which fails.
////
////        restContratoMockMvc.perform(post("/api/contratos")
////            .contentType(TestUtil.APPLICATION_JSON_UTF8)
////            .content(TestUtil.convertObjectToJsonBytes(contrato)))
////            .andExpect(status().isBadRequest());
////
////        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
////        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
////    }
////
////    @Test
////    @Transactional
////    public void checkAdtOperacaoIsRequired() throws Exception {
////        int databaseSizeBeforeTest = contratoBC.recuperarTodosContrato().size();
////        // set the field null
////        contrato.setAdtOperacao(null);
////
////        // Create the Contrato, which fails.
////
////        restContratoMockMvc.perform(post("/api/contratos")
////            .contentType(TestUtil.APPLICATION_JSON_UTF8)
////            .content(TestUtil.convertObjectToJsonBytes(contrato)))
////            .andExpect(status().isBadRequest());
////
////        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
////        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
////    }
//
//    @Test
//    @Transactional
//    public void getAllContratos() throws Exception {
//        // Initialize the database
//        contratoBC.inserir(contrato.converterParaDTO());
//
//        // Get all the contratoList
//        restContratoMockMvc.perform(get("/api/contratos?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(contrato.getId().intValue())))
//            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
//            .andExpect(jsonPath("$.[*].objeto").value(hasItem(DEFAULT_OBJETO.toString())))
//            .andExpect(jsonPath("$.[*].dataAssinatura").value(hasItem(DEFAULT_DATA_ASSINATURA.toString())))
//            .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO.toString())))
//            .andExpect(jsonPath("$.[*].inicioVigencia").value(hasItem(DEFAULT_INICIO_VIGENCIA.toString())))
//            .andExpect(jsonPath("$.[*].fimVigencia").value(hasItem(DEFAULT_FIM_VIGENCIA.toString())))
//            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.intValue())))
//            .andExpect(jsonPath("$.[*].inSituacao").value(hasItem(DEFAULT_IN_SITUACAO.toString())))
//            .andExpect(jsonPath("$.[*].aptoAIniciar").value(hasItem(DEFAULT_APTO_A_INICIAR.booleanValue())))
//            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
//            .andExpect(jsonPath("$.[*].adtLogin").value(hasItem(DEFAULT_ADT_LOGIN.toString())))
//            .andExpect(jsonPath("$.[*].adtDataHora").value(hasItem(DEFAULT_ADT_DATA_HORA.toString())))
//            .andExpect(jsonPath("$.[*].adtOperacao").value(hasItem(DEFAULT_ADT_OPERACAO.toString())));
//    }
//    
//    @Test
//    @Transactional
//    public void getContrato() throws Exception {
//        // Initialize the database
//        contratoBC.inserir(contrato.converterParaDTO());
//
//        // Get the contrato
//        restContratoMockMvc.perform(get("/api/contratos/{id}", contrato.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(contrato.getId().intValue()))
//            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
//            .andExpect(jsonPath("$.objeto").value(DEFAULT_OBJETO.toString()))
//            .andExpect(jsonPath("$.dataAssinatura").value(DEFAULT_DATA_ASSINATURA.toString()))
//            .andExpect(jsonPath("$.dataPublicacao").value(DEFAULT_DATA_PUBLICACAO.toString()))
//            .andExpect(jsonPath("$.inicioVigencia").value(DEFAULT_INICIO_VIGENCIA.toString()))
//            .andExpect(jsonPath("$.fimVigencia").value(DEFAULT_FIM_VIGENCIA.toString()))
//            .andExpect(jsonPath("$.valorTotal").value(DEFAULT_VALOR_TOTAL.intValue()))
//            .andExpect(jsonPath("$.inSituacao").value(DEFAULT_IN_SITUACAO.toString()))
//            .andExpect(jsonPath("$.aptoAIniciar").value(DEFAULT_APTO_A_INICIAR.booleanValue()))
//            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
//            .andExpect(jsonPath("$.adtLogin").value(DEFAULT_ADT_LOGIN.toString()))
//            .andExpect(jsonPath("$.adtDataHora").value(DEFAULT_ADT_DATA_HORA.toString()))
//            .andExpect(jsonPath("$.adtOperacao").value(DEFAULT_ADT_OPERACAO.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingContrato() throws Exception {
//        // Get the contrato
//        restContratoMockMvc.perform(get("/api/contratos/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateContrato() throws Exception {
//        // Initialize the database
//        contratoBC.inserir(contrato.converterParaDTO());
//
//        int databaseSizeBeforeUpdate = contratoBC.recuperarTodosContrato().size();
//
//        // Update the contrato
//        ContratoDTO updatedContrato = contratoBC.recuperarContratoPorId(contrato.getId());
//        // Disconnect from session so that the updates on updatedContrato are not directly saved in db
//        
//        updatedContrato.setNumero(UPDATED_NUMERO);
//        updatedContrato.setObjeto(UPDATED_OBJETO);
//        updatedContrato.setDataAssinatura(UPDATED_DATA_ASSINATURA);
//        updatedContrato.setDataPublicacao(UPDATED_DATA_PUBLICACAO);
//        updatedContrato.setInicioVigencia(UPDATED_INICIO_VIGENCIA);
//        updatedContrato.setFimVigencia(UPDATED_FIM_VIGENCIA);
//        updatedContrato.setValorTotal(UPDATED_VALOR_TOTAL);
//        updatedContrato.setInSituacao(UPDATED_IN_SITUACAO);
//        updatedContrato.setAptoAIniciar(UPDATED_APTO_A_INICIAR);
//        updatedContrato.setVersao(UPDATED_VERSAO);
//        
//           restContratoMockMvc.perform(put("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedContrato)))
//            .andExpect(status().isOk());
//
//        // Validate the Contrato in the database
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
//        ContratoDTO testContrato = contratoList.get(contratoList.size() - 1);
//        assertThat(testContrato.getNumero()).isEqualTo(UPDATED_NUMERO);
//        assertThat(testContrato.getObjeto()).isEqualTo(UPDATED_OBJETO);
//        assertThat(testContrato.getDataAssinatura()).isEqualTo(UPDATED_DATA_ASSINATURA);
//        assertThat(testContrato.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
//        assertThat(testContrato.getInicioVigencia()).isEqualTo(UPDATED_INICIO_VIGENCIA);
//        assertThat(testContrato.getFimVigencia()).isEqualTo(UPDATED_FIM_VIGENCIA);
//        assertThat(testContrato.getValorTotal()).isEqualTo(UPDATED_VALOR_TOTAL);
//        assertThat(testContrato.getInSituacao()).isEqualTo(UPDATED_IN_SITUACAO);
//        assertThat(testContrato.getAptoAIniciar()).isEqualTo(UPDATED_APTO_A_INICIAR);
//        assertThat(testContrato.getVersao()).isEqualTo(UPDATED_VERSAO);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingContrato() throws Exception {
//        int databaseSizeBeforeUpdate = contratoBC.recuperarTodosContrato().size();
//
//        // Create the Contrato
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restContratoMockMvc.perform(put("/api/contratos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(contrato)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Contrato in the database
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteContrato() throws Exception {
//        // Initialize the database
//        contratoBC.inserir(contrato.converterParaDTO());
//
//        int databaseSizeBeforeDelete = contratoBC.recuperarTodosContrato().size();
//
//        // Delete the contrato
//        restContratoMockMvc.perform(delete("/api/contratos/{id}", contrato.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<ContratoDTO> contratoList = contratoBC.recuperarTodosContrato();
//        assertThat(contratoList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Contrato.class);
//        ContratoBD contrato1 = new ContratoBD();
//        contrato1.setId(1L);
//        ContratoBD contrato2 = new ContratoBD();
//        contrato2.setId(contrato1.getId());
//        assertThat(contrato1).isEqualTo(contrato2);
//        contrato2.setId(2L);
//        assertThat(contrato1).isNotEqualTo(contrato2);
//        contrato1.setId(null);
//        assertThat(contrato1).isNotEqualTo(contrato2);
//    }
//}
