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
//import br.gov.economia.maisbrasil.contratos.domain.Historico;
//import br.gov.economia.maisbrasil.contratos.repository.HistoricoRepository;
//import br.gov.economia.maisbrasil.contratos.web.rest.errors.ExceptionTranslator;
//
///**
// * Integration tests for the {@Link HistoricoResource} REST controller.
// */
//@Disabled
//@SpringBootTest(classes = MaisbrasilContratosBackendApp.class)
//public class HistoricoResourceIT {
//
//    private static final String DEFAULT_SITUACAO_CONTRATO = "AAA";
//    private static final String UPDATED_SITUACAO_CONTRATO = "BBB";
//
//    private static final String DEFAULT_EVENTO_GERADOR = "AAA";
//    private static final String UPDATED_EVENTO_GERADOR = "BBB";
//
//    private static final String DEFAULT_NOME_RESPONSAVEL = "AAAAAAAAAA";
//    private static final String UPDATED_NOME_RESPONSAVEL = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CPF_RESPONSAVEL = "AAAAAAAAAA";
//    private static final String UPDATED_CPF_RESPONSAVEL = "BBBBBBBBBB";
//
//    private static final LocalDate DEFAULT_DATA_REGISTRO = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_DATA_REGISTRO = LocalDate.now(ZoneId.systemDefault());
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
//    private HistoricoRepository historicoRepository;
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
//    private MockMvc restHistoricoMockMvc;
//
//    private Historico historico;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final HistoricoResource historicoResource = new HistoricoResource(historicoRepository);
//        this.restHistoricoMockMvc = MockMvcBuilders.standaloneSetup(historicoResource)
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
//    public static Historico createEntity(EntityManager em) {
//        Historico historico = new Historico()
//            .situacaoContrato(DEFAULT_SITUACAO_CONTRATO)
//            .eventoGerador(DEFAULT_EVENTO_GERADOR)
//            .nomeResponsavel(DEFAULT_NOME_RESPONSAVEL)
//            .cpfResponsavel(DEFAULT_CPF_RESPONSAVEL)
//            .dataRegistro(DEFAULT_DATA_REGISTRO)
//            .versao(DEFAULT_VERSAO)
//            .adtLogin(DEFAULT_ADT_LOGIN)
//            .adtDataHora(DEFAULT_ADT_DATA_HORA)
//            .adtOperacao(DEFAULT_ADT_OPERACAO);
//        return historico;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Historico createUpdatedEntity(EntityManager em) {
//        Historico historico = new Historico()
//            .situacaoContrato(UPDATED_SITUACAO_CONTRATO)
//            .eventoGerador(UPDATED_EVENTO_GERADOR)
//            .nomeResponsavel(UPDATED_NOME_RESPONSAVEL)
//            .cpfResponsavel(UPDATED_CPF_RESPONSAVEL)
//            .dataRegistro(UPDATED_DATA_REGISTRO)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//        return historico;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        historico = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createHistorico() throws Exception {
//        int databaseSizeBeforeCreate = historicoRepository.findAll().size();
//
//        // Create the Historico
//        restHistoricoMockMvc.perform(post("/api/historicos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(historico)))
//            .andExpect(status().isCreated());
//
//        // Validate the Historico in the database
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeCreate + 1);
//        Historico testHistorico = historicoList.get(historicoList.size() - 1);
//        assertThat(testHistorico.getSituacaoContrato()).isEqualTo(DEFAULT_SITUACAO_CONTRATO);
//        assertThat(testHistorico.getEventoGerador()).isEqualTo(DEFAULT_EVENTO_GERADOR);
//        assertThat(testHistorico.getNomeResponsavel()).isEqualTo(DEFAULT_NOME_RESPONSAVEL);
//        assertThat(testHistorico.getCpfResponsavel()).isEqualTo(DEFAULT_CPF_RESPONSAVEL);
//        assertThat(testHistorico.getDataRegistro()).isEqualTo(DEFAULT_DATA_REGISTRO);
//        assertThat(testHistorico.getVersao()).isEqualTo(DEFAULT_VERSAO);
//        assertThat(testHistorico.getAdtLogin()).isEqualTo(DEFAULT_ADT_LOGIN);
//        assertThat(testHistorico.getAdtDataHora()).isEqualTo(DEFAULT_ADT_DATA_HORA);
//        assertThat(testHistorico.getAdtOperacao()).isEqualTo(DEFAULT_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void createHistoricoWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = historicoRepository.findAll().size();
//
//        // Create the Historico with an existing ID
//        historico.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restHistoricoMockMvc.perform(post("/api/historicos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(historico)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Historico in the database
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkSituacaoContratoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = historicoRepository.findAll().size();
//        // set the field null
//        historico.setSituacaoContrato(null);
//
//        // Create the Historico, which fails.
//
//        restHistoricoMockMvc.perform(post("/api/historicos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(historico)))
//            .andExpect(status().isBadRequest());
//
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkEventoGeradorIsRequired() throws Exception {
//        int databaseSizeBeforeTest = historicoRepository.findAll().size();
//        // set the field null
//        historico.setEventoGerador(null);
//
//        // Create the Historico, which fails.
//
//        restHistoricoMockMvc.perform(post("/api/historicos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(historico)))
//            .andExpect(status().isBadRequest());
//
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkNomeResponsavelIsRequired() throws Exception {
//        int databaseSizeBeforeTest = historicoRepository.findAll().size();
//        // set the field null
//        historico.setNomeResponsavel(null);
//
//        // Create the Historico, which fails.
//
//        restHistoricoMockMvc.perform(post("/api/historicos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(historico)))
//            .andExpect(status().isBadRequest());
//
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkCpfResponsavelIsRequired() throws Exception {
//        int databaseSizeBeforeTest = historicoRepository.findAll().size();
//        // set the field null
//        historico.setCpfResponsavel(null);
//
//        // Create the Historico, which fails.
//
//        restHistoricoMockMvc.perform(post("/api/historicos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(historico)))
//            .andExpect(status().isBadRequest());
//
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkDataRegistroIsRequired() throws Exception {
//        int databaseSizeBeforeTest = historicoRepository.findAll().size();
//        // set the field null
//        historico.setDataRegistro(null);
//
//        // Create the Historico, which fails.
//
//        restHistoricoMockMvc.perform(post("/api/historicos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(historico)))
//            .andExpect(status().isBadRequest());
//
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVersaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = historicoRepository.findAll().size();
//        // set the field null
//        historico.setVersao(null);
//
//        // Create the Historico, which fails.
//
//        restHistoricoMockMvc.perform(post("/api/historicos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(historico)))
//            .andExpect(status().isBadRequest());
//
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtLoginIsRequired() throws Exception {
//        int databaseSizeBeforeTest = historicoRepository.findAll().size();
//        // set the field null
//        historico.setAdtLogin(null);
//
//        // Create the Historico, which fails.
//
//        restHistoricoMockMvc.perform(post("/api/historicos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(historico)))
//            .andExpect(status().isBadRequest());
//
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtDataHoraIsRequired() throws Exception {
//        int databaseSizeBeforeTest = historicoRepository.findAll().size();
//        // set the field null
//        historico.setAdtDataHora(null);
//
//        // Create the Historico, which fails.
//
//        restHistoricoMockMvc.perform(post("/api/historicos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(historico)))
//            .andExpect(status().isBadRequest());
//
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtOperacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = historicoRepository.findAll().size();
//        // set the field null
//        historico.setAdtOperacao(null);
//
//        // Create the Historico, which fails.
//
//        restHistoricoMockMvc.perform(post("/api/historicos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(historico)))
//            .andExpect(status().isBadRequest());
//
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllHistoricos() throws Exception {
//        // Initialize the database
//        historicoRepository.saveAndFlush(historico);
//
//        // Get all the historicoList
//        restHistoricoMockMvc.perform(get("/api/historicos?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(historico.getId().intValue())))
//            .andExpect(jsonPath("$.[*].situacaoContrato").value(hasItem(DEFAULT_SITUACAO_CONTRATO.toString())))
//            .andExpect(jsonPath("$.[*].eventoGerador").value(hasItem(DEFAULT_EVENTO_GERADOR.toString())))
//            .andExpect(jsonPath("$.[*].nomeResponsavel").value(hasItem(DEFAULT_NOME_RESPONSAVEL.toString())))
//            .andExpect(jsonPath("$.[*].cpfResponsavel").value(hasItem(DEFAULT_CPF_RESPONSAVEL.toString())))
//            .andExpect(jsonPath("$.[*].dataRegistro").value(hasItem(DEFAULT_DATA_REGISTRO.toString())))
//            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
//            .andExpect(jsonPath("$.[*].adtLogin").value(hasItem(DEFAULT_ADT_LOGIN.toString())))
//            .andExpect(jsonPath("$.[*].adtDataHora").value(hasItem(DEFAULT_ADT_DATA_HORA.toString())))
//            .andExpect(jsonPath("$.[*].adtOperacao").value(hasItem(DEFAULT_ADT_OPERACAO.toString())));
//    }
//    
//    @Test
//    @Transactional
//    public void getHistorico() throws Exception {
//        // Initialize the database
//        historicoRepository.saveAndFlush(historico);
//
//        // Get the historico
//        restHistoricoMockMvc.perform(get("/api/historicos/{id}", historico.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(historico.getId().intValue()))
//            .andExpect(jsonPath("$.situacaoContrato").value(DEFAULT_SITUACAO_CONTRATO.toString()))
//            .andExpect(jsonPath("$.eventoGerador").value(DEFAULT_EVENTO_GERADOR.toString()))
//            .andExpect(jsonPath("$.nomeResponsavel").value(DEFAULT_NOME_RESPONSAVEL.toString()))
//            .andExpect(jsonPath("$.cpfResponsavel").value(DEFAULT_CPF_RESPONSAVEL.toString()))
//            .andExpect(jsonPath("$.dataRegistro").value(DEFAULT_DATA_REGISTRO.toString()))
//            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
//            .andExpect(jsonPath("$.adtLogin").value(DEFAULT_ADT_LOGIN.toString()))
//            .andExpect(jsonPath("$.adtDataHora").value(DEFAULT_ADT_DATA_HORA.toString()))
//            .andExpect(jsonPath("$.adtOperacao").value(DEFAULT_ADT_OPERACAO.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingHistorico() throws Exception {
//        // Get the historico
//        restHistoricoMockMvc.perform(get("/api/historicos/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateHistorico() throws Exception {
//        // Initialize the database
//        historicoRepository.saveAndFlush(historico);
//
//        int databaseSizeBeforeUpdate = historicoRepository.findAll().size();
//
//        // Update the historico
//        Historico updatedHistorico = historicoRepository.findById(historico.getId()).get();
//        // Disconnect from session so that the updates on updatedHistorico are not directly saved in db
//        em.detach(updatedHistorico);
//        updatedHistorico
//            .situacaoContrato(UPDATED_SITUACAO_CONTRATO)
//            .eventoGerador(UPDATED_EVENTO_GERADOR)
//            .nomeResponsavel(UPDATED_NOME_RESPONSAVEL)
//            .cpfResponsavel(UPDATED_CPF_RESPONSAVEL)
//            .dataRegistro(UPDATED_DATA_REGISTRO)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//
//        restHistoricoMockMvc.perform(put("/api/historicos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedHistorico)))
//            .andExpect(status().isOk());
//
//        // Validate the Historico in the database
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeUpdate);
//        Historico testHistorico = historicoList.get(historicoList.size() - 1);
//        assertThat(testHistorico.getSituacaoContrato()).isEqualTo(UPDATED_SITUACAO_CONTRATO);
//        assertThat(testHistorico.getEventoGerador()).isEqualTo(UPDATED_EVENTO_GERADOR);
//        assertThat(testHistorico.getNomeResponsavel()).isEqualTo(UPDATED_NOME_RESPONSAVEL);
//        assertThat(testHistorico.getCpfResponsavel()).isEqualTo(UPDATED_CPF_RESPONSAVEL);
//        assertThat(testHistorico.getDataRegistro()).isEqualTo(UPDATED_DATA_REGISTRO);
//        assertThat(testHistorico.getVersao()).isEqualTo(UPDATED_VERSAO);
//        assertThat(testHistorico.getAdtLogin()).isEqualTo(UPDATED_ADT_LOGIN);
//        assertThat(testHistorico.getAdtDataHora()).isEqualTo(UPDATED_ADT_DATA_HORA);
//        assertThat(testHistorico.getAdtOperacao()).isEqualTo(UPDATED_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingHistorico() throws Exception {
//        int databaseSizeBeforeUpdate = historicoRepository.findAll().size();
//
//        // Create the Historico
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restHistoricoMockMvc.perform(put("/api/historicos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(historico)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Historico in the database
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteHistorico() throws Exception {
//        // Initialize the database
//        historicoRepository.saveAndFlush(historico);
//
//        int databaseSizeBeforeDelete = historicoRepository.findAll().size();
//
//        // Delete the historico
//        restHistoricoMockMvc.perform(delete("/api/historicos/{id}", historico.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Historico> historicoList = historicoRepository.findAll();
//        assertThat(historicoList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Historico.class);
//        Historico historico1 = new Historico();
//        historico1.setId(1L);
//        Historico historico2 = new Historico();
//        historico2.setId(historico1.getId());
//        assertThat(historico1).isEqualTo(historico2);
//        historico2.setId(2L);
//        assertThat(historico1).isNotEqualTo(historico2);
//        historico1.setId(null);
//        assertThat(historico1).isNotEqualTo(historico2);
//    }
//}
