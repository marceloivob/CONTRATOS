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
//import br.gov.economia.maisbrasil.contratos.domain.Proposta;
//import br.gov.economia.maisbrasil.contratos.repository.PropostaRepository;
//import br.gov.economia.maisbrasil.contratos.web.rest.errors.ExceptionTranslator;
//
///**
// * Integration tests for the {@Link PropostaResource} REST controller.
// */
//@Disabled
//@SpringBootTest(classes = MaisbrasilContratosBackendApp.class)
//public class PropostaResourceIT {
//
//    private static final Integer DEFAULT_ID_SICONV = 1;
//    private static final Integer UPDATED_ID_SICONV = 2;
//
//    private static final Integer DEFAULT_NUMERO_PROPOSTA = 1;
//    private static final Integer UPDATED_NUMERO_PROPOSTA = 2;
//
//    private static final Integer DEFAULT_ANO_PROPOSTA = 1;
//    private static final Integer UPDATED_ANO_PROPOSTA = 2;
//
//    private static final BigDecimal DEFAULT_VALOR_GLOBAL = new BigDecimal(1);
//    private static final BigDecimal UPDATED_VALOR_GLOBAL = new BigDecimal(2);
//
//    private static final BigDecimal DEFAULT_VALOR_REPASSE = new BigDecimal(1);
//    private static final BigDecimal UPDATED_VALOR_REPASSE = new BigDecimal(2);
//
//    private static final BigDecimal DEFAULT_VALOR_CONTRAPARTIDA = new BigDecimal(1);
//    private static final BigDecimal UPDATED_VALOR_CONTRAPARTIDA = new BigDecimal(2);
//
//    private static final Integer DEFAULT_MODALIDADE = 1;
//    private static final Integer UPDATED_MODALIDADE = 2;
//
//    private static final String DEFAULT_NOME_OBJETO = "AAAAAAAAAA";
//    private static final String UPDATED_NOME_OBJETO = "BBBBBBBBBB";
//
//    private static final Integer DEFAULT_NUMERO_CONVENIO = 1;
//    private static final Integer UPDATED_NUMERO_CONVENIO = 2;
//
//    private static final Integer DEFAULT_ANO_CONVENIO = 1;
//    private static final Integer UPDATED_ANO_CONVENIO = 2;
//
//    private static final LocalDate DEFAULT_DATA_ASSINATURA_CONVENIO = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_DATA_ASSINATURA_CONVENIO = LocalDate.now(ZoneId.systemDefault());
//
//    private static final String DEFAULT_CODIGO_PROGRAMA = "AAAAAAAAAA";
//    private static final String UPDATED_CODIGO_PROGRAMA = "BBBBBBBBBB";
//
//    private static final String DEFAULT_NOME_PROGRAMA = "AAAAAAAAAA";
//    private static final String UPDATED_NOME_PROGRAMA = "BBBBBBBBBB";
//
//    private static final String DEFAULT_IDENTIFICACAO_PROPONENTE = "AAAAAAAAAA";
//    private static final String UPDATED_IDENTIFICACAO_PROPONENTE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_NOME_PROPONENTE = "AAAAAAAAAA";
//    private static final String UPDATED_NOME_PROPONENTE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_UF = "AA";
//    private static final String UPDATED_UF = "BB";
//
//    private static final BigDecimal DEFAULT_PC_MIN_CONTRAPARTIDA = new BigDecimal(1);
//    private static final BigDecimal UPDATED_PC_MIN_CONTRAPARTIDA = new BigDecimal(2);
//
//    private static final String DEFAULT_NOME_MANDATARIA = "AAAAAAAAAA";
//    private static final String UPDATED_NOME_MANDATARIA = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
//    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";
//
//    private static final String DEFAULT_NIVEL_CONTRATO = "AAAAAAAAAA";
//    private static final String UPDATED_NIVEL_CONTRATO = "BBBBBBBBBB";
//
//    private static final Boolean DEFAULT_SPA_HOMOLOGADO = false;
//    private static final Boolean UPDATED_SPA_HOMOLOGADO = true;
//
//    private static final String DEFAULT_APELIDO_EMPREENDIMENTO = "AAAAAAAAAA";
//    private static final String UPDATED_APELIDO_EMPREENDIMENTO = "BBBBBBBBBB";
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
//    private PropostaRepository propostaRepository;
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
//    private MockMvc restPropostaMockMvc;
//
//    private Proposta proposta;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final PropostaResource propostaResource = new PropostaResource(propostaRepository);
//        this.restPropostaMockMvc = MockMvcBuilders.standaloneSetup(propostaResource)
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
//    public static Proposta createEntity(EntityManager em) {
//        Proposta proposta = new Proposta()
//            .idSiconv(DEFAULT_ID_SICONV)
//            .numeroProposta(DEFAULT_NUMERO_PROPOSTA)
//            .anoProposta(DEFAULT_ANO_PROPOSTA)
//            .valorGlobal(DEFAULT_VALOR_GLOBAL)
//            .valorRepasse(DEFAULT_VALOR_REPASSE)
//            .valorContrapartida(DEFAULT_VALOR_CONTRAPARTIDA)
//            .modalidade(DEFAULT_MODALIDADE)
//            .nomeObjeto(DEFAULT_NOME_OBJETO)
//            .numeroConvenio(DEFAULT_NUMERO_CONVENIO)
//            .anoConvenio(DEFAULT_ANO_CONVENIO)
//            .dataAssinaturaConvenio(DEFAULT_DATA_ASSINATURA_CONVENIO)
//            .codigoPrograma(DEFAULT_CODIGO_PROGRAMA)
//            .nomePrograma(DEFAULT_NOME_PROGRAMA)
//            .identificacaoProponente(DEFAULT_IDENTIFICACAO_PROPONENTE)
//            .nomeProponente(DEFAULT_NOME_PROPONENTE)
//            .uf(DEFAULT_UF)
//            .pcMinContrapartida(DEFAULT_PC_MIN_CONTRAPARTIDA)
//            .nomeMandataria(DEFAULT_NOME_MANDATARIA)
//            .categoria(DEFAULT_CATEGORIA)
//            .nivelContrato(DEFAULT_NIVEL_CONTRATO)
//            .spaHomologado(DEFAULT_SPA_HOMOLOGADO)
//            .apelidoEmpreendimento(DEFAULT_APELIDO_EMPREENDIMENTO)
//            .versao(DEFAULT_VERSAO)
//            .adtLogin(DEFAULT_ADT_LOGIN)
//            .adtDataHora(DEFAULT_ADT_DATA_HORA)
//            .adtOperacao(DEFAULT_ADT_OPERACAO);
//        return proposta;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Proposta createUpdatedEntity(EntityManager em) {
//        Proposta proposta = new Proposta()
//            .idSiconv(UPDATED_ID_SICONV)
//            .numeroProposta(UPDATED_NUMERO_PROPOSTA)
//            .anoProposta(UPDATED_ANO_PROPOSTA)
//            .valorGlobal(UPDATED_VALOR_GLOBAL)
//            .valorRepasse(UPDATED_VALOR_REPASSE)
//            .valorContrapartida(UPDATED_VALOR_CONTRAPARTIDA)
//            .modalidade(UPDATED_MODALIDADE)
//            .nomeObjeto(UPDATED_NOME_OBJETO)
//            .numeroConvenio(UPDATED_NUMERO_CONVENIO)
//            .anoConvenio(UPDATED_ANO_CONVENIO)
//            .dataAssinaturaConvenio(UPDATED_DATA_ASSINATURA_CONVENIO)
//            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
//            .nomePrograma(UPDATED_NOME_PROGRAMA)
//            .identificacaoProponente(UPDATED_IDENTIFICACAO_PROPONENTE)
//            .nomeProponente(UPDATED_NOME_PROPONENTE)
//            .uf(UPDATED_UF)
//            .pcMinContrapartida(UPDATED_PC_MIN_CONTRAPARTIDA)
//            .nomeMandataria(UPDATED_NOME_MANDATARIA)
//            .categoria(UPDATED_CATEGORIA)
//            .nivelContrato(UPDATED_NIVEL_CONTRATO)
//            .spaHomologado(UPDATED_SPA_HOMOLOGADO)
//            .apelidoEmpreendimento(UPDATED_APELIDO_EMPREENDIMENTO)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//        return proposta;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        proposta = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createProposta() throws Exception {
//        int databaseSizeBeforeCreate = propostaRepository.findAll().size();
//
//        // Create the Proposta
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isCreated());
//
//        // Validate the Proposta in the database
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeCreate + 1);
//        Proposta testProposta = propostaList.get(propostaList.size() - 1);
//        assertThat(testProposta.getIdSiconv()).isEqualTo(DEFAULT_ID_SICONV);
//        assertThat(testProposta.getNumeroProposta()).isEqualTo(DEFAULT_NUMERO_PROPOSTA);
//        assertThat(testProposta.getAnoProposta()).isEqualTo(DEFAULT_ANO_PROPOSTA);
//        assertThat(testProposta.getValorGlobal()).isEqualTo(DEFAULT_VALOR_GLOBAL);
//        assertThat(testProposta.getValorRepasse()).isEqualTo(DEFAULT_VALOR_REPASSE);
//        assertThat(testProposta.getValorContrapartida()).isEqualTo(DEFAULT_VALOR_CONTRAPARTIDA);
//        assertThat(testProposta.getModalidade()).isEqualTo(DEFAULT_MODALIDADE);
//        assertThat(testProposta.getNomeObjeto()).isEqualTo(DEFAULT_NOME_OBJETO);
//        assertThat(testProposta.getNumeroConvenio()).isEqualTo(DEFAULT_NUMERO_CONVENIO);
//        assertThat(testProposta.getAnoConvenio()).isEqualTo(DEFAULT_ANO_CONVENIO);
//        assertThat(testProposta.getDataAssinaturaConvenio()).isEqualTo(DEFAULT_DATA_ASSINATURA_CONVENIO);
//        assertThat(testProposta.getCodigoPrograma()).isEqualTo(DEFAULT_CODIGO_PROGRAMA);
//        assertThat(testProposta.getNomePrograma()).isEqualTo(DEFAULT_NOME_PROGRAMA);
//        assertThat(testProposta.getIdentificacaoProponente()).isEqualTo(DEFAULT_IDENTIFICACAO_PROPONENTE);
//        assertThat(testProposta.getNomeProponente()).isEqualTo(DEFAULT_NOME_PROPONENTE);
//        assertThat(testProposta.getUf()).isEqualTo(DEFAULT_UF);
//        assertThat(testProposta.getPcMinContrapartida()).isEqualTo(DEFAULT_PC_MIN_CONTRAPARTIDA);
//        assertThat(testProposta.getNomeMandataria()).isEqualTo(DEFAULT_NOME_MANDATARIA);
//        assertThat(testProposta.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
//        assertThat(testProposta.getNivelContrato()).isEqualTo(DEFAULT_NIVEL_CONTRATO);
//        assertThat(testProposta.isSpaHomologado()).isEqualTo(DEFAULT_SPA_HOMOLOGADO);
//        assertThat(testProposta.getApelidoEmpreendimento()).isEqualTo(DEFAULT_APELIDO_EMPREENDIMENTO);
//        assertThat(testProposta.getVersao()).isEqualTo(DEFAULT_VERSAO);
//        assertThat(testProposta.getAdtLogin()).isEqualTo(DEFAULT_ADT_LOGIN);
//        assertThat(testProposta.getAdtDataHora()).isEqualTo(DEFAULT_ADT_DATA_HORA);
//        assertThat(testProposta.getAdtOperacao()).isEqualTo(DEFAULT_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void createPropostaWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = propostaRepository.findAll().size();
//
//        // Create the Proposta with an existing ID
//        proposta.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Proposta in the database
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkIdSiconvIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setIdSiconv(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkNumeroPropostaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setNumeroProposta(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAnoPropostaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setAnoProposta(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkValorGlobalIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setValorGlobal(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkValorRepasseIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setValorRepasse(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkValorContrapartidaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setValorContrapartida(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkModalidadeIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setModalidade(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkCodigoProgramaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setCodigoPrograma(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkNomeProgramaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setNomePrograma(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkUfIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setUf(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkCategoriaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setCategoria(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkSpaHomologadoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setSpaHomologado(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkApelidoEmpreendimentoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setApelidoEmpreendimento(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVersaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setVersao(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtLoginIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setAdtLogin(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtDataHoraIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setAdtDataHora(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtOperacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = propostaRepository.findAll().size();
//        // set the field null
//        proposta.setAdtOperacao(null);
//
//        // Create the Proposta, which fails.
//
//        restPropostaMockMvc.perform(post("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllPropostas() throws Exception {
//        // Initialize the database
//        propostaRepository.saveAndFlush(proposta);
//
//        // Get all the propostaList
//        restPropostaMockMvc.perform(get("/api/propostas?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(proposta.getId().intValue())))
//            .andExpect(jsonPath("$.[*].idSiconv").value(hasItem(DEFAULT_ID_SICONV)))
//            .andExpect(jsonPath("$.[*].numeroProposta").value(hasItem(DEFAULT_NUMERO_PROPOSTA)))
//            .andExpect(jsonPath("$.[*].anoProposta").value(hasItem(DEFAULT_ANO_PROPOSTA)))
//            .andExpect(jsonPath("$.[*].valorGlobal").value(hasItem(DEFAULT_VALOR_GLOBAL.intValue())))
//            .andExpect(jsonPath("$.[*].valorRepasse").value(hasItem(DEFAULT_VALOR_REPASSE.intValue())))
//            .andExpect(jsonPath("$.[*].valorContrapartida").value(hasItem(DEFAULT_VALOR_CONTRAPARTIDA.intValue())))
//            .andExpect(jsonPath("$.[*].modalidade").value(hasItem(DEFAULT_MODALIDADE)))
//            .andExpect(jsonPath("$.[*].nomeObjeto").value(hasItem(DEFAULT_NOME_OBJETO.toString())))
//            .andExpect(jsonPath("$.[*].numeroConvenio").value(hasItem(DEFAULT_NUMERO_CONVENIO)))
//            .andExpect(jsonPath("$.[*].anoConvenio").value(hasItem(DEFAULT_ANO_CONVENIO)))
//            .andExpect(jsonPath("$.[*].dataAssinaturaConvenio").value(hasItem(DEFAULT_DATA_ASSINATURA_CONVENIO.toString())))
//            .andExpect(jsonPath("$.[*].codigoPrograma").value(hasItem(DEFAULT_CODIGO_PROGRAMA.toString())))
//            .andExpect(jsonPath("$.[*].nomePrograma").value(hasItem(DEFAULT_NOME_PROGRAMA.toString())))
//            .andExpect(jsonPath("$.[*].identificacaoProponente").value(hasItem(DEFAULT_IDENTIFICACAO_PROPONENTE.toString())))
//            .andExpect(jsonPath("$.[*].nomeProponente").value(hasItem(DEFAULT_NOME_PROPONENTE.toString())))
//            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF.toString())))
//            .andExpect(jsonPath("$.[*].pcMinContrapartida").value(hasItem(DEFAULT_PC_MIN_CONTRAPARTIDA.intValue())))
//            .andExpect(jsonPath("$.[*].nomeMandataria").value(hasItem(DEFAULT_NOME_MANDATARIA.toString())))
//            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
//            .andExpect(jsonPath("$.[*].nivelContrato").value(hasItem(DEFAULT_NIVEL_CONTRATO.toString())))
//            .andExpect(jsonPath("$.[*].spaHomologado").value(hasItem(DEFAULT_SPA_HOMOLOGADO.booleanValue())))
//            .andExpect(jsonPath("$.[*].apelidoEmpreendimento").value(hasItem(DEFAULT_APELIDO_EMPREENDIMENTO.toString())))
//            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
//            .andExpect(jsonPath("$.[*].adtLogin").value(hasItem(DEFAULT_ADT_LOGIN.toString())))
//            .andExpect(jsonPath("$.[*].adtDataHora").value(hasItem(DEFAULT_ADT_DATA_HORA.toString())))
//            .andExpect(jsonPath("$.[*].adtOperacao").value(hasItem(DEFAULT_ADT_OPERACAO.toString())));
//    }
//    
//    @Test
//    @Transactional
//    public void getProposta() throws Exception {
//        // Initialize the database
//        propostaRepository.saveAndFlush(proposta);
//
//        // Get the proposta
//        restPropostaMockMvc.perform(get("/api/propostas/{id}", proposta.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(proposta.getId().intValue()))
//            .andExpect(jsonPath("$.idSiconv").value(DEFAULT_ID_SICONV))
//            .andExpect(jsonPath("$.numeroProposta").value(DEFAULT_NUMERO_PROPOSTA))
//            .andExpect(jsonPath("$.anoProposta").value(DEFAULT_ANO_PROPOSTA))
//            .andExpect(jsonPath("$.valorGlobal").value(DEFAULT_VALOR_GLOBAL.intValue()))
//            .andExpect(jsonPath("$.valorRepasse").value(DEFAULT_VALOR_REPASSE.intValue()))
//            .andExpect(jsonPath("$.valorContrapartida").value(DEFAULT_VALOR_CONTRAPARTIDA.intValue()))
//            .andExpect(jsonPath("$.modalidade").value(DEFAULT_MODALIDADE))
//            .andExpect(jsonPath("$.nomeObjeto").value(DEFAULT_NOME_OBJETO.toString()))
//            .andExpect(jsonPath("$.numeroConvenio").value(DEFAULT_NUMERO_CONVENIO))
//            .andExpect(jsonPath("$.anoConvenio").value(DEFAULT_ANO_CONVENIO))
//            .andExpect(jsonPath("$.dataAssinaturaConvenio").value(DEFAULT_DATA_ASSINATURA_CONVENIO.toString()))
//            .andExpect(jsonPath("$.codigoPrograma").value(DEFAULT_CODIGO_PROGRAMA.toString()))
//            .andExpect(jsonPath("$.nomePrograma").value(DEFAULT_NOME_PROGRAMA.toString()))
//            .andExpect(jsonPath("$.identificacaoProponente").value(DEFAULT_IDENTIFICACAO_PROPONENTE.toString()))
//            .andExpect(jsonPath("$.nomeProponente").value(DEFAULT_NOME_PROPONENTE.toString()))
//            .andExpect(jsonPath("$.uf").value(DEFAULT_UF.toString()))
//            .andExpect(jsonPath("$.pcMinContrapartida").value(DEFAULT_PC_MIN_CONTRAPARTIDA.intValue()))
//            .andExpect(jsonPath("$.nomeMandataria").value(DEFAULT_NOME_MANDATARIA.toString()))
//            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()))
//            .andExpect(jsonPath("$.nivelContrato").value(DEFAULT_NIVEL_CONTRATO.toString()))
//            .andExpect(jsonPath("$.spaHomologado").value(DEFAULT_SPA_HOMOLOGADO.booleanValue()))
//            .andExpect(jsonPath("$.apelidoEmpreendimento").value(DEFAULT_APELIDO_EMPREENDIMENTO.toString()))
//            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
//            .andExpect(jsonPath("$.adtLogin").value(DEFAULT_ADT_LOGIN.toString()))
//            .andExpect(jsonPath("$.adtDataHora").value(DEFAULT_ADT_DATA_HORA.toString()))
//            .andExpect(jsonPath("$.adtOperacao").value(DEFAULT_ADT_OPERACAO.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingProposta() throws Exception {
//        // Get the proposta
//        restPropostaMockMvc.perform(get("/api/propostas/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateProposta() throws Exception {
//        // Initialize the database
//        propostaRepository.saveAndFlush(proposta);
//
//        int databaseSizeBeforeUpdate = propostaRepository.findAll().size();
//
//        // Update the proposta
//        Proposta updatedProposta = propostaRepository.findById(proposta.getId()).get();
//        // Disconnect from session so that the updates on updatedProposta are not directly saved in db
//        em.detach(updatedProposta);
//        updatedProposta
//            .idSiconv(UPDATED_ID_SICONV)
//            .numeroProposta(UPDATED_NUMERO_PROPOSTA)
//            .anoProposta(UPDATED_ANO_PROPOSTA)
//            .valorGlobal(UPDATED_VALOR_GLOBAL)
//            .valorRepasse(UPDATED_VALOR_REPASSE)
//            .valorContrapartida(UPDATED_VALOR_CONTRAPARTIDA)
//            .modalidade(UPDATED_MODALIDADE)
//            .nomeObjeto(UPDATED_NOME_OBJETO)
//            .numeroConvenio(UPDATED_NUMERO_CONVENIO)
//            .anoConvenio(UPDATED_ANO_CONVENIO)
//            .dataAssinaturaConvenio(UPDATED_DATA_ASSINATURA_CONVENIO)
//            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
//            .nomePrograma(UPDATED_NOME_PROGRAMA)
//            .identificacaoProponente(UPDATED_IDENTIFICACAO_PROPONENTE)
//            .nomeProponente(UPDATED_NOME_PROPONENTE)
//            .uf(UPDATED_UF)
//            .pcMinContrapartida(UPDATED_PC_MIN_CONTRAPARTIDA)
//            .nomeMandataria(UPDATED_NOME_MANDATARIA)
//            .categoria(UPDATED_CATEGORIA)
//            .nivelContrato(UPDATED_NIVEL_CONTRATO)
//            .spaHomologado(UPDATED_SPA_HOMOLOGADO)
//            .apelidoEmpreendimento(UPDATED_APELIDO_EMPREENDIMENTO)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//
//        restPropostaMockMvc.perform(put("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedProposta)))
//            .andExpect(status().isOk());
//
//        // Validate the Proposta in the database
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeUpdate);
//        Proposta testProposta = propostaList.get(propostaList.size() - 1);
//        assertThat(testProposta.getIdSiconv()).isEqualTo(UPDATED_ID_SICONV);
//        assertThat(testProposta.getNumeroProposta()).isEqualTo(UPDATED_NUMERO_PROPOSTA);
//        assertThat(testProposta.getAnoProposta()).isEqualTo(UPDATED_ANO_PROPOSTA);
//        assertThat(testProposta.getValorGlobal()).isEqualTo(UPDATED_VALOR_GLOBAL);
//        assertThat(testProposta.getValorRepasse()).isEqualTo(UPDATED_VALOR_REPASSE);
//        assertThat(testProposta.getValorContrapartida()).isEqualTo(UPDATED_VALOR_CONTRAPARTIDA);
//        assertThat(testProposta.getModalidade()).isEqualTo(UPDATED_MODALIDADE);
//        assertThat(testProposta.getNomeObjeto()).isEqualTo(UPDATED_NOME_OBJETO);
//        assertThat(testProposta.getNumeroConvenio()).isEqualTo(UPDATED_NUMERO_CONVENIO);
//        assertThat(testProposta.getAnoConvenio()).isEqualTo(UPDATED_ANO_CONVENIO);
//        assertThat(testProposta.getDataAssinaturaConvenio()).isEqualTo(UPDATED_DATA_ASSINATURA_CONVENIO);
//        assertThat(testProposta.getCodigoPrograma()).isEqualTo(UPDATED_CODIGO_PROGRAMA);
//        assertThat(testProposta.getNomePrograma()).isEqualTo(UPDATED_NOME_PROGRAMA);
//        assertThat(testProposta.getIdentificacaoProponente()).isEqualTo(UPDATED_IDENTIFICACAO_PROPONENTE);
//        assertThat(testProposta.getNomeProponente()).isEqualTo(UPDATED_NOME_PROPONENTE);
//        assertThat(testProposta.getUf()).isEqualTo(UPDATED_UF);
//        assertThat(testProposta.getPcMinContrapartida()).isEqualTo(UPDATED_PC_MIN_CONTRAPARTIDA);
//        assertThat(testProposta.getNomeMandataria()).isEqualTo(UPDATED_NOME_MANDATARIA);
//        assertThat(testProposta.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
//        assertThat(testProposta.getNivelContrato()).isEqualTo(UPDATED_NIVEL_CONTRATO);
//        assertThat(testProposta.isSpaHomologado()).isEqualTo(UPDATED_SPA_HOMOLOGADO);
//        assertThat(testProposta.getApelidoEmpreendimento()).isEqualTo(UPDATED_APELIDO_EMPREENDIMENTO);
//        assertThat(testProposta.getVersao()).isEqualTo(UPDATED_VERSAO);
//        assertThat(testProposta.getAdtLogin()).isEqualTo(UPDATED_ADT_LOGIN);
//        assertThat(testProposta.getAdtDataHora()).isEqualTo(UPDATED_ADT_DATA_HORA);
//        assertThat(testProposta.getAdtOperacao()).isEqualTo(UPDATED_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingProposta() throws Exception {
//        int databaseSizeBeforeUpdate = propostaRepository.findAll().size();
//
//        // Create the Proposta
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restPropostaMockMvc.perform(put("/api/propostas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(proposta)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Proposta in the database
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteProposta() throws Exception {
//        // Initialize the database
//        propostaRepository.saveAndFlush(proposta);
//
//        int databaseSizeBeforeDelete = propostaRepository.findAll().size();
//
//        // Delete the proposta
//        restPropostaMockMvc.perform(delete("/api/propostas/{id}", proposta.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Proposta> propostaList = propostaRepository.findAll();
//        assertThat(propostaList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Proposta.class);
//        Proposta proposta1 = new Proposta();
//        proposta1.setId(1L);
//        Proposta proposta2 = new Proposta();
//        proposta2.setId(proposta1.getId());
//        assertThat(proposta1).isEqualTo(proposta2);
//        proposta2.setId(2L);
//        assertThat(proposta1).isNotEqualTo(proposta2);
//        proposta1.setId(null);
//        assertThat(proposta1).isNotEqualTo(proposta2);
//    }
//}
