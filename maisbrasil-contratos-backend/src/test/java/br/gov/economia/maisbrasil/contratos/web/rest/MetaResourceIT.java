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
//import br.gov.economia.maisbrasil.contratos.domain.Meta;
//import br.gov.economia.maisbrasil.contratos.repository.MetaRepository;
//import br.gov.economia.maisbrasil.contratos.web.rest.errors.ExceptionTranslator;
//
///**
// * Integration tests for the {@Link MetaResource} REST controller.
// */
//@Disabled
//@SpringBootTest(classes = MaisbrasilContratosBackendApp.class)
//public class MetaResourceIT {
//
//    private static final Integer DEFAULT_ID_META_VRPL = 1;
//    private static final Integer UPDATED_ID_META_VRPL = 2;
//
//    private static final String DEFAULT_TX_DESCRICAO = "AAAAAAAAAA";
//    private static final String UPDATED_TX_DESCRICAO = "BBBBBBBBBB";
//
//    private static final Integer DEFAULT_NUMERO = 1;
//    private static final Integer UPDATED_NUMERO = 2;
//
//    private static final BigDecimal DEFAULT_QT_ITENS = new BigDecimal(1);
//    private static final BigDecimal UPDATED_QT_ITENS = new BigDecimal(2);
//
//    private static final Boolean DEFAULT_IN_SOCIAL = false;
//    private static final Boolean UPDATED_IN_SOCIAL = true;
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
//    private MetaRepository metaRepository;
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
//    private MockMvc restMetaMockMvc;
//
//    private Meta meta;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final MetaResource metaResource = new MetaResource(metaRepository);
//        this.restMetaMockMvc = MockMvcBuilders.standaloneSetup(metaResource)
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
//    public static Meta createEntity(EntityManager em) {
//        Meta meta = new Meta()
//            .idMetaVRPL(DEFAULT_ID_META_VRPL)
//            .txDescricao(DEFAULT_TX_DESCRICAO)
//            .numero(DEFAULT_NUMERO)
//            .qtItens(DEFAULT_QT_ITENS)
//            .inSocial(DEFAULT_IN_SOCIAL)
//            .versao(DEFAULT_VERSAO)
//            .adtLogin(DEFAULT_ADT_LOGIN)
//            .adtDataHora(DEFAULT_ADT_DATA_HORA)
//            .adtOperacao(DEFAULT_ADT_OPERACAO);
//        return meta;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Meta createUpdatedEntity(EntityManager em) {
//        Meta meta = new Meta()
//            .idMetaVRPL(UPDATED_ID_META_VRPL)
//            .txDescricao(UPDATED_TX_DESCRICAO)
//            .numero(UPDATED_NUMERO)
//            .qtItens(UPDATED_QT_ITENS)
//            .inSocial(UPDATED_IN_SOCIAL)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//        return meta;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        meta = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createMeta() throws Exception {
//        int databaseSizeBeforeCreate = metaRepository.findAll().size();
//
//        // Create the Meta
//        restMetaMockMvc.perform(post("/api/metas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(meta)))
//            .andExpect(status().isCreated());
//
//        // Validate the Meta in the database
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeCreate + 1);
//        Meta testMeta = metaList.get(metaList.size() - 1);
//        assertThat(testMeta.getIdMetaVRPL()).isEqualTo(DEFAULT_ID_META_VRPL);
//        assertThat(testMeta.getTxDescricao()).isEqualTo(DEFAULT_TX_DESCRICAO);
//        assertThat(testMeta.getNumero()).isEqualTo(DEFAULT_NUMERO);
//        assertThat(testMeta.getQtItens()).isEqualTo(DEFAULT_QT_ITENS);
//        assertThat(testMeta.isInSocial()).isEqualTo(DEFAULT_IN_SOCIAL);
//        assertThat(testMeta.getVersao()).isEqualTo(DEFAULT_VERSAO);
//        assertThat(testMeta.getAdtLogin()).isEqualTo(DEFAULT_ADT_LOGIN);
//        assertThat(testMeta.getAdtDataHora()).isEqualTo(DEFAULT_ADT_DATA_HORA);
//        assertThat(testMeta.getAdtOperacao()).isEqualTo(DEFAULT_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void createMetaWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = metaRepository.findAll().size();
//
//        // Create the Meta with an existing ID
//        meta.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restMetaMockMvc.perform(post("/api/metas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(meta)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Meta in the database
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkIdMetaVRPLIsRequired() throws Exception {
//        int databaseSizeBeforeTest = metaRepository.findAll().size();
//        // set the field null
//        meta.setIdMetaVRPL(null);
//
//        // Create the Meta, which fails.
//
//        restMetaMockMvc.perform(post("/api/metas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(meta)))
//            .andExpect(status().isBadRequest());
//
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkTxDescricaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = metaRepository.findAll().size();
//        // set the field null
//        meta.setTxDescricao(null);
//
//        // Create the Meta, which fails.
//
//        restMetaMockMvc.perform(post("/api/metas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(meta)))
//            .andExpect(status().isBadRequest());
//
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkNumeroIsRequired() throws Exception {
//        int databaseSizeBeforeTest = metaRepository.findAll().size();
//        // set the field null
//        meta.setNumero(null);
//
//        // Create the Meta, which fails.
//
//        restMetaMockMvc.perform(post("/api/metas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(meta)))
//            .andExpect(status().isBadRequest());
//
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkQtItensIsRequired() throws Exception {
//        int databaseSizeBeforeTest = metaRepository.findAll().size();
//        // set the field null
//        meta.setQtItens(null);
//
//        // Create the Meta, which fails.
//
//        restMetaMockMvc.perform(post("/api/metas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(meta)))
//            .andExpect(status().isBadRequest());
//
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkInSocialIsRequired() throws Exception {
//        int databaseSizeBeforeTest = metaRepository.findAll().size();
//        // set the field null
//        meta.setInSocial(null);
//
//        // Create the Meta, which fails.
//
//        restMetaMockMvc.perform(post("/api/metas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(meta)))
//            .andExpect(status().isBadRequest());
//
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkVersaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = metaRepository.findAll().size();
//        // set the field null
//        meta.setVersao(null);
//
//        // Create the Meta, which fails.
//
//        restMetaMockMvc.perform(post("/api/metas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(meta)))
//            .andExpect(status().isBadRequest());
//
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtLoginIsRequired() throws Exception {
//        int databaseSizeBeforeTest = metaRepository.findAll().size();
//        // set the field null
//        meta.setAdtLogin(null);
//
//        // Create the Meta, which fails.
//
//        restMetaMockMvc.perform(post("/api/metas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(meta)))
//            .andExpect(status().isBadRequest());
//
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtDataHoraIsRequired() throws Exception {
//        int databaseSizeBeforeTest = metaRepository.findAll().size();
//        // set the field null
//        meta.setAdtDataHora(null);
//
//        // Create the Meta, which fails.
//
//        restMetaMockMvc.perform(post("/api/metas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(meta)))
//            .andExpect(status().isBadRequest());
//
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkAdtOperacaoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = metaRepository.findAll().size();
//        // set the field null
//        meta.setAdtOperacao(null);
//
//        // Create the Meta, which fails.
//
//        restMetaMockMvc.perform(post("/api/metas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(meta)))
//            .andExpect(status().isBadRequest());
//
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllMetas() throws Exception {
//        // Initialize the database
//        metaRepository.saveAndFlush(meta);
//
//        // Get all the metaList
//        restMetaMockMvc.perform(get("/api/metas?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(meta.getId().intValue())))
//            .andExpect(jsonPath("$.[*].idMetaVRPL").value(hasItem(DEFAULT_ID_META_VRPL)))
//            .andExpect(jsonPath("$.[*].txDescricao").value(hasItem(DEFAULT_TX_DESCRICAO.toString())))
//            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
//            .andExpect(jsonPath("$.[*].qtItens").value(hasItem(DEFAULT_QT_ITENS.intValue())))
//            .andExpect(jsonPath("$.[*].inSocial").value(hasItem(DEFAULT_IN_SOCIAL.booleanValue())))
//            .andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO.intValue())))
//            .andExpect(jsonPath("$.[*].adtLogin").value(hasItem(DEFAULT_ADT_LOGIN.toString())))
//            .andExpect(jsonPath("$.[*].adtDataHora").value(hasItem(DEFAULT_ADT_DATA_HORA.toString())))
//            .andExpect(jsonPath("$.[*].adtOperacao").value(hasItem(DEFAULT_ADT_OPERACAO.toString())));
//    }
//    
//    @Test
//    @Transactional
//    public void getMeta() throws Exception {
//        // Initialize the database
//        metaRepository.saveAndFlush(meta);
//
//        // Get the meta
//        restMetaMockMvc.perform(get("/api/metas/{id}", meta.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(meta.getId().intValue()))
//            .andExpect(jsonPath("$.idMetaVRPL").value(DEFAULT_ID_META_VRPL))
//            .andExpect(jsonPath("$.txDescricao").value(DEFAULT_TX_DESCRICAO.toString()))
//            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
//            .andExpect(jsonPath("$.qtItens").value(DEFAULT_QT_ITENS.intValue()))
//            .andExpect(jsonPath("$.inSocial").value(DEFAULT_IN_SOCIAL.booleanValue()))
//            .andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO.intValue()))
//            .andExpect(jsonPath("$.adtLogin").value(DEFAULT_ADT_LOGIN.toString()))
//            .andExpect(jsonPath("$.adtDataHora").value(DEFAULT_ADT_DATA_HORA.toString()))
//            .andExpect(jsonPath("$.adtOperacao").value(DEFAULT_ADT_OPERACAO.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingMeta() throws Exception {
//        // Get the meta
//        restMetaMockMvc.perform(get("/api/metas/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateMeta() throws Exception {
//        // Initialize the database
//        metaRepository.saveAndFlush(meta);
//
//        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
//
//        // Update the meta
//        Meta updatedMeta = metaRepository.findById(meta.getId()).get();
//        // Disconnect from session so that the updates on updatedMeta are not directly saved in db
//        em.detach(updatedMeta);
//        updatedMeta
//            .idMetaVRPL(UPDATED_ID_META_VRPL)
//            .txDescricao(UPDATED_TX_DESCRICAO)
//            .numero(UPDATED_NUMERO)
//            .qtItens(UPDATED_QT_ITENS)
//            .inSocial(UPDATED_IN_SOCIAL)
//            .versao(UPDATED_VERSAO)
//            .adtLogin(UPDATED_ADT_LOGIN)
//            .adtDataHora(UPDATED_ADT_DATA_HORA)
//            .adtOperacao(UPDATED_ADT_OPERACAO);
//
//        restMetaMockMvc.perform(put("/api/metas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedMeta)))
//            .andExpect(status().isOk());
//
//        // Validate the Meta in the database
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
//        Meta testMeta = metaList.get(metaList.size() - 1);
//        assertThat(testMeta.getIdMetaVRPL()).isEqualTo(UPDATED_ID_META_VRPL);
//        assertThat(testMeta.getTxDescricao()).isEqualTo(UPDATED_TX_DESCRICAO);
//        assertThat(testMeta.getNumero()).isEqualTo(UPDATED_NUMERO);
//        assertThat(testMeta.getQtItens()).isEqualTo(UPDATED_QT_ITENS);
//        assertThat(testMeta.isInSocial()).isEqualTo(UPDATED_IN_SOCIAL);
//        assertThat(testMeta.getVersao()).isEqualTo(UPDATED_VERSAO);
//        assertThat(testMeta.getAdtLogin()).isEqualTo(UPDATED_ADT_LOGIN);
//        assertThat(testMeta.getAdtDataHora()).isEqualTo(UPDATED_ADT_DATA_HORA);
//        assertThat(testMeta.getAdtOperacao()).isEqualTo(UPDATED_ADT_OPERACAO);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingMeta() throws Exception {
//        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
//
//        // Create the Meta
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restMetaMockMvc.perform(put("/api/metas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(meta)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Meta in the database
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteMeta() throws Exception {
//        // Initialize the database
//        metaRepository.saveAndFlush(meta);
//
//        int databaseSizeBeforeDelete = metaRepository.findAll().size();
//
//        // Delete the meta
//        restMetaMockMvc.perform(delete("/api/metas/{id}", meta.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Meta> metaList = metaRepository.findAll();
//        assertThat(metaList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Meta.class);
//        Meta meta1 = new Meta();
//        meta1.setId(1L);
//        Meta meta2 = new Meta();
//        meta2.setId(meta1.getId());
//        assertThat(meta1).isEqualTo(meta2);
//        meta2.setId(2L);
//        assertThat(meta1).isNotEqualTo(meta2);
//        meta1.setId(null);
//        assertThat(meta1).isNotEqualTo(meta2);
//    }
//}
