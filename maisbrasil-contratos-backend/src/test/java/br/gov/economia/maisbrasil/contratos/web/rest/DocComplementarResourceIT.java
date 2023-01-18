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
//import br.gov.economia.maisbrasil.contratos.domain.DocComplementar;
//import br.gov.economia.maisbrasil.contratos.repository.DocComplementarRepository;
//import br.gov.economia.maisbrasil.contratos.web.rest.errors.ExceptionTranslator;
//
///**
// * Integration tests for the {@Link DocComplementarResource} REST controller.
// */
//@Disabled
//@SpringBootTest(classes = MaisbrasilContratosBackendApp.class)
//public class DocComplementarResourceIT {
//
//    private static final String DEFAULT_NUMERO_DOCUMENTO = "AAAAAAAAAA";
//    private static final String UPDATED_NUMERO_DOCUMENTO = "BBBBBBBBBB";
//
//    private static final String DEFAULT_ORGAO_EMISSOR = "AAAAAAAAAA";
//    private static final String UPDATED_ORGAO_EMISSOR = "BBBBBBBBBB";
//
//    private static final LocalDate DEFAULT_DATA_EMISSAO = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_DATA_EMISSAO = LocalDate.now(ZoneId.systemDefault());
//
//    private static final LocalDate DEFAULT_DATA_VALIDADE = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_DATA_VALIDADE = LocalDate.now(ZoneId.systemDefault());
//
//    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
//    private static final String UPDATED_TIPO = "BBBBBBBBBB";
//
//    private static final String DEFAULT_TIPO_MANIFESTO_AMBIENTAL = "AAAAAAAAAA";
//    private static final String UPDATED_TIPO_MANIFESTO_AMBIENTAL = "BBBBBBBBBB";
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
//    private DocComplementarRepository docComplementarRepository;
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
//    private MockMvc restDocComplementarMockMvc;
//
//    private DocComplementar docComplementar;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final DocComplementarResource docComplementarResource = new DocComplementarResource(docComplementarRepository);
//        this.restDocComplementarMockMvc = MockMvcBuilders.standaloneSetup(docComplementarResource)
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
//    public static DocComplementar createEntity(EntityManager em) {
//        DocComplementar docComplementar = new DocComplementar()
//            .numeroDocumento(DEFAULT_NUMERO_DOCUMENTO)
//            .orgaoEmissor(DEFAULT_ORGAO_EMISSOR)
//            .dataEmissao(DEFAULT_DATA_EMISSAO)
//            .dataValidade(DEFAULT_DATA_VALIDADE)
//            .tipo(DEFAULT_TIPO)
//            .tipoManifestoAmbiental(DEFAULT_TIPO_MANIFESTO_AMBIENTAL)
//            .versao(DEFAULT_VERSAO)
//            .adtLogin(DEFAULT_ADT_LOGIN)
//            .adtDataHora(DEFAULT_ADT_DATA_HORA)
//            .adtOperacao(DEFAULT_ADT_OPERACAO);
//        return docComplementar;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static DocComplementar createUpdatedEntity(EntityManager em) {
//        DocComplementar docComplementar = new DocComplementar()
//            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
//            .orgaoEmissor(UPDATED_ORGAO_EMISSOR)
//            .dataEmissao(UPDATED_DATA_EMISSAO)
//            .dataValidade(UPDATED_DATA_VALIDADE)
//            .tipo(UPDATED_TIPO)
//            .tipoManifestoAmbiental(UPDATED_TIPO_MANIFESTO_AMBIENTAL)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//        return docComplementar;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        docComplementar = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createDocComplementar() throws Exception {
//        int databaseSizeBeforeCreate = docComplementarRepository.findAll().size();
//
//        // Create the DocComplementar
//        restDocComplementarMockMvc.perform(post("/api/doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(docComplementar)))
//            .andExpect(status().isCreated());
//
//        // Validate the DocComplementar in the database
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeCreate + 1);
//        DocComplementar testDocComplementar = docComplementarList.get(docComplementarList.size() - 1);
//        assertThat(testDocComplementar.getNumeroDocumento()).isEqualTo(DEFAULT_NUMERO_DOCUMENTO);
//        assertThat(testDocComplementar.getOrgaoEmissor()).isEqualTo(DEFAULT_ORGAO_EMISSOR);
//        assertThat(testDocComplementar.getDataEmissao()).isEqualTo(DEFAULT_DATA_EMISSAO);
//        assertThat(testDocComplementar.getDataValidade()).isEqualTo(DEFAULT_DATA_VALIDADE);
//        assertThat(testDocComplementar.getTipo()).isEqualTo(DEFAULT_TIPO);
//        assertThat(testDocComplementar.getTipoManifestoAmbiental()).isEqualTo(DEFAULT_TIPO_MANIFESTO_AMBIENTAL);
//        assertThat(testDocComplementar.getVersao()).isEqualTo(DEFAULT_VERSAO);
//        assertThat(testDocComplementar.getAdtLogin()).isEqualTo(DEFAULT_ADT_LOGIN);
//        assertThat(testDocComplementar.getAdtDataHora()).isEqualTo(DEFAULT_ADT_DATA_HORA);
//        assertThat(testDocComplementar.getAdtOperacao()).isEqualTo(DEFAULT_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void createDocComplementarWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = docComplementarRepository.findAll().size();
//
//        // Create the DocComplementar with an existing ID
//        docComplementar.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restDocComplementarMockMvc.perform(post("/api/doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(docComplementar)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the DocComplementar in the database
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkNumeroDocumentoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = docComplementarRepository.findAll().size();
//        // set the field null
//        docComplementar.setNumeroDocumento(null);
//
//        // Create the DocComplementar, which fails.
//
//        restDocComplementarMockMvc.perform(post("/api/doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(docComplementar)))
//            .andExpect(status().isBadRequest());
//
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkOrgaoEmissorIsRequired() throws Exception {
//        int databaseSizeBeforeTest = docComplementarRepository.findAll().size();
//        // set the field null
//        docComplementar.setOrgaoEmissor(null);
//
//        // Create the DocComplementar, which fails.
//
//        restDocComplementarMockMvc.perform(post("/api/doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(docComplementar)))
//            .andExpect(status().isBadRequest());
//
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkDataEmissaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = docComplementarRepository.findAll().size();
//        // set the field null
//        docComplementar.setDataEmissao(null);
//
//        // Create the DocComplementar, which fails.
//
//        restDocComplementarMockMvc.perform(post("/api/doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(docComplementar)))
//            .andExpect(status().isBadRequest());
//
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkDataValidadeIsRequired() throws Exception {
//        int databaseSizeBeforeTest = docComplementarRepository.findAll().size();
//        // set the field null
//        docComplementar.setDataValidade(null);
//
//        // Create the DocComplementar, which fails.
//
//        restDocComplementarMockMvc.perform(post("/api/doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(docComplementar)))
//            .andExpect(status().isBadRequest());
//
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkTipoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = docComplementarRepository.findAll().size();
//        // set the field null
//        docComplementar.setTipo(null);
//
//        // Create the DocComplementar, which fails.
//
//        restDocComplementarMockMvc.perform(post("/api/doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(docComplementar)))
//            .andExpect(status().isBadRequest());
//
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVersaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = docComplementarRepository.findAll().size();
//        // set the field null
//        docComplementar.setVersao(null);
//
//        // Create the DocComplementar, which fails.
//
//        restDocComplementarMockMvc.perform(post("/api/doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(docComplementar)))
//            .andExpect(status().isBadRequest());
//
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtLoginIsRequired() throws Exception {
//        int databaseSizeBeforeTest = docComplementarRepository.findAll().size();
//        // set the field null
//        docComplementar.setAdtLogin(null);
//
//        // Create the DocComplementar, which fails.
//
//        restDocComplementarMockMvc.perform(post("/api/doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(docComplementar)))
//            .andExpect(status().isBadRequest());
//
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtDataHoraIsRequired() throws Exception {
//        int databaseSizeBeforeTest = docComplementarRepository.findAll().size();
//        // set the field null
//        docComplementar.setAdtDataHora(null);
//
//        // Create the DocComplementar, which fails.
//
//        restDocComplementarMockMvc.perform(post("/api/doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(docComplementar)))
//            .andExpect(status().isBadRequest());
//
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtOperacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = docComplementarRepository.findAll().size();
//        // set the field null
//        docComplementar.setAdtOperacao(null);
//
//        // Create the DocComplementar, which fails.
//
//        restDocComplementarMockMvc.perform(post("/api/doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(docComplementar)))
//            .andExpect(status().isBadRequest());
//
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllDocComplementars() throws Exception {
//        // Initialize the database
//        docComplementarRepository.saveAndFlush(docComplementar);
//
//        // Get all the docComplementarList
//        restDocComplementarMockMvc.perform(get("/api/doc-complementars?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(docComplementar.getId().intValue())))
//            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO.toString())))
//            .andExpect(jsonPath("$.[*].orgaoEmissor").value(hasItem(DEFAULT_ORGAO_EMISSOR.toString())))
//            .andExpect(jsonPath("$.[*].dataEmissao").value(hasItem(DEFAULT_DATA_EMISSAO.toString())))
//            .andExpect(jsonPath("$.[*].dataValidade").value(hasItem(DEFAULT_DATA_VALIDADE.toString())))
//            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
//            .andExpect(jsonPath("$.[*].tipoManifestoAmbiental").value(hasItem(DEFAULT_TIPO_MANIFESTO_AMBIENTAL.toString())))
//            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
//            .andExpect(jsonPath("$.[*].adtLogin").value(hasItem(DEFAULT_ADT_LOGIN.toString())))
//            .andExpect(jsonPath("$.[*].adtDataHora").value(hasItem(DEFAULT_ADT_DATA_HORA.toString())))
//            .andExpect(jsonPath("$.[*].adtOperacao").value(hasItem(DEFAULT_ADT_OPERACAO.toString())));
//    }
//    
//    @Test
//    @Transactional
//    public void getDocComplementar() throws Exception {
//        // Initialize the database
//        docComplementarRepository.saveAndFlush(docComplementar);
//
//        // Get the docComplementar
//        restDocComplementarMockMvc.perform(get("/api/doc-complementars/{id}", docComplementar.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(docComplementar.getId().intValue()))
//            .andExpect(jsonPath("$.numeroDocumento").value(DEFAULT_NUMERO_DOCUMENTO.toString()))
//            .andExpect(jsonPath("$.orgaoEmissor").value(DEFAULT_ORGAO_EMISSOR.toString()))
//            .andExpect(jsonPath("$.dataEmissao").value(DEFAULT_DATA_EMISSAO.toString()))
//            .andExpect(jsonPath("$.dataValidade").value(DEFAULT_DATA_VALIDADE.toString()))
//            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
//            .andExpect(jsonPath("$.tipoManifestoAmbiental").value(DEFAULT_TIPO_MANIFESTO_AMBIENTAL.toString()))
//            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
//            .andExpect(jsonPath("$.adtLogin").value(DEFAULT_ADT_LOGIN.toString()))
//            .andExpect(jsonPath("$.adtDataHora").value(DEFAULT_ADT_DATA_HORA.toString()))
//            .andExpect(jsonPath("$.adtOperacao").value(DEFAULT_ADT_OPERACAO.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingDocComplementar() throws Exception {
//        // Get the docComplementar
//        restDocComplementarMockMvc.perform(get("/api/doc-complementars/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateDocComplementar() throws Exception {
//        // Initialize the database
//        docComplementarRepository.saveAndFlush(docComplementar);
//
//        int databaseSizeBeforeUpdate = docComplementarRepository.findAll().size();
//
//        // Update the docComplementar
//        DocComplementar updatedDocComplementar = docComplementarRepository.findById(docComplementar.getId()).get();
//        // Disconnect from session so that the updates on updatedDocComplementar are not directly saved in db
//        em.detach(updatedDocComplementar);
//        updatedDocComplementar
//            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
//            .orgaoEmissor(UPDATED_ORGAO_EMISSOR)
//            .dataEmissao(UPDATED_DATA_EMISSAO)
//            .dataValidade(UPDATED_DATA_VALIDADE)
//            .tipo(UPDATED_TIPO)
//            .tipoManifestoAmbiental(UPDATED_TIPO_MANIFESTO_AMBIENTAL)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//
//        restDocComplementarMockMvc.perform(put("/api/doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedDocComplementar)))
//            .andExpect(status().isOk());
//
//        // Validate the DocComplementar in the database
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeUpdate);
//        DocComplementar testDocComplementar = docComplementarList.get(docComplementarList.size() - 1);
//        assertThat(testDocComplementar.getNumeroDocumento()).isEqualTo(UPDATED_NUMERO_DOCUMENTO);
//        assertThat(testDocComplementar.getOrgaoEmissor()).isEqualTo(UPDATED_ORGAO_EMISSOR);
//        assertThat(testDocComplementar.getDataEmissao()).isEqualTo(UPDATED_DATA_EMISSAO);
//        assertThat(testDocComplementar.getDataValidade()).isEqualTo(UPDATED_DATA_VALIDADE);
//        assertThat(testDocComplementar.getTipo()).isEqualTo(UPDATED_TIPO);
//        assertThat(testDocComplementar.getTipoManifestoAmbiental()).isEqualTo(UPDATED_TIPO_MANIFESTO_AMBIENTAL);
//        assertThat(testDocComplementar.getVersao()).isEqualTo(UPDATED_VERSAO);
//        assertThat(testDocComplementar.getAdtLogin()).isEqualTo(UPDATED_ADT_LOGIN);
//        assertThat(testDocComplementar.getAdtDataHora()).isEqualTo(UPDATED_ADT_DATA_HORA);
//        assertThat(testDocComplementar.getAdtOperacao()).isEqualTo(UPDATED_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingDocComplementar() throws Exception {
//        int databaseSizeBeforeUpdate = docComplementarRepository.findAll().size();
//
//        // Create the DocComplementar
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restDocComplementarMockMvc.perform(put("/api/doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(docComplementar)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the DocComplementar in the database
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteDocComplementar() throws Exception {
//        // Initialize the database
//        docComplementarRepository.saveAndFlush(docComplementar);
//
//        int databaseSizeBeforeDelete = docComplementarRepository.findAll().size();
//
//        // Delete the docComplementar
//        restDocComplementarMockMvc.perform(delete("/api/doc-complementars/{id}", docComplementar.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<DocComplementar> docComplementarList = docComplementarRepository.findAll();
//        assertThat(docComplementarList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(DocComplementar.class);
//        DocComplementar docComplementar1 = new DocComplementar();
//        docComplementar1.setId(1L);
//        DocComplementar docComplementar2 = new DocComplementar();
//        docComplementar2.setId(docComplementar1.getId());
//        assertThat(docComplementar1).isEqualTo(docComplementar2);
//        docComplementar2.setId(2L);
//        assertThat(docComplementar1).isNotEqualTo(docComplementar2);
//        docComplementar1.setId(null);
//        assertThat(docComplementar1).isNotEqualTo(docComplementar2);
//    }
//}
