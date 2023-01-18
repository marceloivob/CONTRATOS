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
//import br.gov.economia.maisbrasil.contratos.domain.Submeta;
//import br.gov.economia.maisbrasil.contratos.repository.SubmetaRepository;
//import br.gov.economia.maisbrasil.contratos.web.rest.errors.ExceptionTranslator;
//
///**
// * Integration tests for the {@Link SubmetaResource} REST controller.
// */
//@Disabled
//@SpringBootTest(classes = MaisbrasilContratosBackendApp.class)
//public class SubmetaResourceIT {
//
//    private static final Integer DEFAULT_NUMERO = 1;
//    private static final Integer UPDATED_NUMERO = 2;
//
//    private static final BigDecimal DEFAULT_VL_REPASSE = new BigDecimal(1);
//    private static final BigDecimal UPDATED_VL_REPASSE = new BigDecimal(2);
//
//    private static final BigDecimal DEFAULT_VL_OUTROS = new BigDecimal(1);
//    private static final BigDecimal UPDATED_VL_OUTROS = new BigDecimal(2);
//
//    private static final String DEFAULT_IN_REGIME_EXECUCAO = "AAA";
//    private static final String UPDATED_IN_REGIME_EXECUCAO = "BBB";
//
//    private static final BigDecimal DEFAULT_VL_CONTRAPARTIDA = new BigDecimal(1);
//    private static final BigDecimal UPDATED_VL_CONTRAPARTIDA = new BigDecimal(2);
//
//    private static final BigDecimal DEFAULT_VL_TOTAL_LICITADO = new BigDecimal(1);
//    private static final BigDecimal UPDATED_VL_TOTAL_LICITADO = new BigDecimal(2);
//
//    private static final String DEFAULT_IN_SITUACAO = "AAA";
//    private static final String UPDATED_IN_SITUACAO = "BBB";
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
//    private SubmetaRepository submetaRepository;
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
//    private MockMvc restSubmetaMockMvc;
//
//    private Submeta submeta;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final SubmetaResource submetaResource = new SubmetaResource(submetaRepository);
//        this.restSubmetaMockMvc = MockMvcBuilders.standaloneSetup(submetaResource)
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
//    public static Submeta createEntity(EntityManager em) {
//        Submeta submeta = new Submeta()
//            .numero(DEFAULT_NUMERO)
//            .vlRepasse(DEFAULT_VL_REPASSE)
//            .vlOutros(DEFAULT_VL_OUTROS)
//            .inRegimeExecucao(DEFAULT_IN_REGIME_EXECUCAO)
//            .vlContrapartida(DEFAULT_VL_CONTRAPARTIDA)
//            .vlTotalLicitado(DEFAULT_VL_TOTAL_LICITADO)
//            .inSituacao(DEFAULT_IN_SITUACAO)
//            .versao(DEFAULT_VERSAO)
//            .adtLogin(DEFAULT_ADT_LOGIN)
//            .adtDataHora(DEFAULT_ADT_DATA_HORA)
//            .adtOperacao(DEFAULT_ADT_OPERACAO);
//        return submeta;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Submeta createUpdatedEntity(EntityManager em) {
//        Submeta submeta = new Submeta()
//            .numero(UPDATED_NUMERO)
//            .vlRepasse(UPDATED_VL_REPASSE)
//            .vlOutros(UPDATED_VL_OUTROS)
//            .inRegimeExecucao(UPDATED_IN_REGIME_EXECUCAO)
//            .vlContrapartida(UPDATED_VL_CONTRAPARTIDA)
//            .vlTotalLicitado(UPDATED_VL_TOTAL_LICITADO)
//            .inSituacao(UPDATED_IN_SITUACAO)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//        return submeta;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        submeta = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createSubmeta() throws Exception {
//        int databaseSizeBeforeCreate = submetaRepository.findAll().size();
//
//        // Create the Submeta
//        restSubmetaMockMvc.perform(post("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isCreated());
//
//        // Validate the Submeta in the database
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeCreate + 1);
//        Submeta testSubmeta = submetaList.get(submetaList.size() - 1);
//        assertThat(testSubmeta.getNumero()).isEqualTo(DEFAULT_NUMERO);
//        assertThat(testSubmeta.getVlRepasse()).isEqualTo(DEFAULT_VL_REPASSE);
//        assertThat(testSubmeta.getVlOutros()).isEqualTo(DEFAULT_VL_OUTROS);
//        assertThat(testSubmeta.getInRegimeExecucao()).isEqualTo(DEFAULT_IN_REGIME_EXECUCAO);
//        assertThat(testSubmeta.getVlContrapartida()).isEqualTo(DEFAULT_VL_CONTRAPARTIDA);
//        assertThat(testSubmeta.getVlTotalLicitado()).isEqualTo(DEFAULT_VL_TOTAL_LICITADO);
//        assertThat(testSubmeta.getInSituacao()).isEqualTo(DEFAULT_IN_SITUACAO);
//        assertThat(testSubmeta.getVersao()).isEqualTo(DEFAULT_VERSAO);
//        assertThat(testSubmeta.getAdtLogin()).isEqualTo(DEFAULT_ADT_LOGIN);
//        assertThat(testSubmeta.getAdtDataHora()).isEqualTo(DEFAULT_ADT_DATA_HORA);
//        assertThat(testSubmeta.getAdtOperacao()).isEqualTo(DEFAULT_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void createSubmetaWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = submetaRepository.findAll().size();
//
//        // Create the Submeta with an existing ID
//        submeta.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restSubmetaMockMvc.perform(post("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Submeta in the database
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkNumeroIsRequired() throws Exception {
//        int databaseSizeBeforeTest = submetaRepository.findAll().size();
//        // set the field null
//        submeta.setNumero(null);
//
//        // Create the Submeta, which fails.
//
//        restSubmetaMockMvc.perform(post("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isBadRequest());
//
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVlRepasseIsRequired() throws Exception {
//        int databaseSizeBeforeTest = submetaRepository.findAll().size();
//        // set the field null
//        submeta.setVlRepasse(null);
//
//        // Create the Submeta, which fails.
//
//        restSubmetaMockMvc.perform(post("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isBadRequest());
//
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVlOutrosIsRequired() throws Exception {
//        int databaseSizeBeforeTest = submetaRepository.findAll().size();
//        // set the field null
//        submeta.setVlOutros(null);
//
//        // Create the Submeta, which fails.
//
//        restSubmetaMockMvc.perform(post("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isBadRequest());
//
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkInRegimeExecucaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = submetaRepository.findAll().size();
//        // set the field null
//        submeta.setInRegimeExecucao(null);
//
//        // Create the Submeta, which fails.
//
//        restSubmetaMockMvc.perform(post("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isBadRequest());
//
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVlContrapartidaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = submetaRepository.findAll().size();
//        // set the field null
//        submeta.setVlContrapartida(null);
//
//        // Create the Submeta, which fails.
//
//        restSubmetaMockMvc.perform(post("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isBadRequest());
//
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVlTotalLicitadoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = submetaRepository.findAll().size();
//        // set the field null
//        submeta.setVlTotalLicitado(null);
//
//        // Create the Submeta, which fails.
//
//        restSubmetaMockMvc.perform(post("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isBadRequest());
//
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkInSituacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = submetaRepository.findAll().size();
//        // set the field null
//        submeta.setInSituacao(null);
//
//        // Create the Submeta, which fails.
//
//        restSubmetaMockMvc.perform(post("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isBadRequest());
//
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVersaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = submetaRepository.findAll().size();
//        // set the field null
//        submeta.setVersao(null);
//
//        // Create the Submeta, which fails.
//
//        restSubmetaMockMvc.perform(post("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isBadRequest());
//
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtLoginIsRequired() throws Exception {
//        int databaseSizeBeforeTest = submetaRepository.findAll().size();
//        // set the field null
//        submeta.setAdtLogin(null);
//
//        // Create the Submeta, which fails.
//
//        restSubmetaMockMvc.perform(post("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isBadRequest());
//
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtDataHoraIsRequired() throws Exception {
//        int databaseSizeBeforeTest = submetaRepository.findAll().size();
//        // set the field null
//        submeta.setAdtDataHora(null);
//
//        // Create the Submeta, which fails.
//
//        restSubmetaMockMvc.perform(post("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isBadRequest());
//
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtOperacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = submetaRepository.findAll().size();
//        // set the field null
//        submeta.setAdtOperacao(null);
//
//        // Create the Submeta, which fails.
//
//        restSubmetaMockMvc.perform(post("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isBadRequest());
//
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllSubmetas() throws Exception {
//        // Initialize the database
//        submetaRepository.saveAndFlush(submeta);
//
//        // Get all the submetaList
//        restSubmetaMockMvc.perform(get("/api/submetas?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(submeta.getId().intValue())))
//            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
//            .andExpect(jsonPath("$.[*].vlRepasse").value(hasItem(DEFAULT_VL_REPASSE.intValue())))
//            .andExpect(jsonPath("$.[*].vlOutros").value(hasItem(DEFAULT_VL_OUTROS.intValue())))
//            .andExpect(jsonPath("$.[*].inRegimeExecucao").value(hasItem(DEFAULT_IN_REGIME_EXECUCAO.toString())))
//            .andExpect(jsonPath("$.[*].vlContrapartida").value(hasItem(DEFAULT_VL_CONTRAPARTIDA.intValue())))
//            .andExpect(jsonPath("$.[*].vlTotalLicitado").value(hasItem(DEFAULT_VL_TOTAL_LICITADO.intValue())))
//            .andExpect(jsonPath("$.[*].inSituacao").value(hasItem(DEFAULT_IN_SITUACAO.toString())))
//            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
//            .andExpect(jsonPath("$.[*].adtLogin").value(hasItem(DEFAULT_ADT_LOGIN.toString())))
//            .andExpect(jsonPath("$.[*].adtDataHora").value(hasItem(DEFAULT_ADT_DATA_HORA.toString())))
//            .andExpect(jsonPath("$.[*].adtOperacao").value(hasItem(DEFAULT_ADT_OPERACAO.toString())));
//    }
//    
//    @Test
//    @Transactional
//    public void getSubmeta() throws Exception {
//        // Initialize the database
//        submetaRepository.saveAndFlush(submeta);
//
//        // Get the submeta
//        restSubmetaMockMvc.perform(get("/api/submetas/{id}", submeta.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(submeta.getId().intValue()))
//            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
//            .andExpect(jsonPath("$.vlRepasse").value(DEFAULT_VL_REPASSE.intValue()))
//            .andExpect(jsonPath("$.vlOutros").value(DEFAULT_VL_OUTROS.intValue()))
//            .andExpect(jsonPath("$.inRegimeExecucao").value(DEFAULT_IN_REGIME_EXECUCAO.toString()))
//            .andExpect(jsonPath("$.vlContrapartida").value(DEFAULT_VL_CONTRAPARTIDA.intValue()))
//            .andExpect(jsonPath("$.vlTotalLicitado").value(DEFAULT_VL_TOTAL_LICITADO.intValue()))
//            .andExpect(jsonPath("$.inSituacao").value(DEFAULT_IN_SITUACAO.toString()))
//            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
//            .andExpect(jsonPath("$.adtLogin").value(DEFAULT_ADT_LOGIN.toString()))
//            .andExpect(jsonPath("$.adtDataHora").value(DEFAULT_ADT_DATA_HORA.toString()))
//            .andExpect(jsonPath("$.adtOperacao").value(DEFAULT_ADT_OPERACAO.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingSubmeta() throws Exception {
//        // Get the submeta
//        restSubmetaMockMvc.perform(get("/api/submetas/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateSubmeta() throws Exception {
//        // Initialize the database
//        submetaRepository.saveAndFlush(submeta);
//
//        int databaseSizeBeforeUpdate = submetaRepository.findAll().size();
//
//        // Update the submeta
//        Submeta updatedSubmeta = submetaRepository.findById(submeta.getId()).get();
//        // Disconnect from session so that the updates on updatedSubmeta are not directly saved in db
//        em.detach(updatedSubmeta);
//        updatedSubmeta
//            .numero(UPDATED_NUMERO)
//            .vlRepasse(UPDATED_VL_REPASSE)
//            .vlOutros(UPDATED_VL_OUTROS)
//            .inRegimeExecucao(UPDATED_IN_REGIME_EXECUCAO)
//            .vlContrapartida(UPDATED_VL_CONTRAPARTIDA)
//            .vlTotalLicitado(UPDATED_VL_TOTAL_LICITADO)
//            .inSituacao(UPDATED_IN_SITUACAO)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//
//        restSubmetaMockMvc.perform(put("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedSubmeta)))
//            .andExpect(status().isOk());
//
//        // Validate the Submeta in the database
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeUpdate);
//        Submeta testSubmeta = submetaList.get(submetaList.size() - 1);
//        assertThat(testSubmeta.getNumero()).isEqualTo(UPDATED_NUMERO);
//        assertThat(testSubmeta.getVlRepasse()).isEqualTo(UPDATED_VL_REPASSE);
//        assertThat(testSubmeta.getVlOutros()).isEqualTo(UPDATED_VL_OUTROS);
//        assertThat(testSubmeta.getInRegimeExecucao()).isEqualTo(UPDATED_IN_REGIME_EXECUCAO);
//        assertThat(testSubmeta.getVlContrapartida()).isEqualTo(UPDATED_VL_CONTRAPARTIDA);
//        assertThat(testSubmeta.getVlTotalLicitado()).isEqualTo(UPDATED_VL_TOTAL_LICITADO);
//        assertThat(testSubmeta.getInSituacao()).isEqualTo(UPDATED_IN_SITUACAO);
//        assertThat(testSubmeta.getVersao()).isEqualTo(UPDATED_VERSAO);
//        assertThat(testSubmeta.getAdtLogin()).isEqualTo(UPDATED_ADT_LOGIN);
//        assertThat(testSubmeta.getAdtDataHora()).isEqualTo(UPDATED_ADT_DATA_HORA);
//        assertThat(testSubmeta.getAdtOperacao()).isEqualTo(UPDATED_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingSubmeta() throws Exception {
//        int databaseSizeBeforeUpdate = submetaRepository.findAll().size();
//
//        // Create the Submeta
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restSubmetaMockMvc.perform(put("/api/submetas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submeta)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Submeta in the database
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteSubmeta() throws Exception {
//        // Initialize the database
//        submetaRepository.saveAndFlush(submeta);
//
//        int databaseSizeBeforeDelete = submetaRepository.findAll().size();
//
//        // Delete the submeta
//        restSubmetaMockMvc.perform(delete("/api/submetas/{id}", submeta.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Submeta> submetaList = submetaRepository.findAll();
//        assertThat(submetaList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Submeta.class);
//        Submeta submeta1 = new Submeta();
//        submeta1.setId(1L);
//        Submeta submeta2 = new Submeta();
//        submeta2.setId(submeta1.getId());
//        assertThat(submeta1).isEqualTo(submeta2);
//        submeta2.setId(2L);
//        assertThat(submeta1).isNotEqualTo(submeta2);
//        submeta1.setId(null);
//        assertThat(submeta1).isNotEqualTo(submeta2);
//    }
//}
