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
//import br.gov.economia.maisbrasil.contratos.domain.SubmetaDocComplementar;
//import br.gov.economia.maisbrasil.contratos.repository.SubmetaDocComplementarRepository;
//import br.gov.economia.maisbrasil.contratos.web.rest.errors.ExceptionTranslator;
//
///**
// * Integration tests for the {@Link SubmetaDocComplementarResource} REST controller.
// */
//@Disabled
//@SpringBootTest(classes = MaisbrasilContratosBackendApp.class)
//public class SubmetaDocComplementarResourceIT {
//
//    private static final LocalDate DEFAULT_DATA_ASSOCIACAO = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_DATA_ASSOCIACAO = LocalDate.now(ZoneId.systemDefault());
//
//    @Autowired
//    private SubmetaDocComplementarRepository submetaDocComplementarRepository;
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
//    private MockMvc restSubmetaDocComplementarMockMvc;
//
//    private SubmetaDocComplementar submetaDocComplementar;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final SubmetaDocComplementarResource submetaDocComplementarResource = new SubmetaDocComplementarResource(submetaDocComplementarRepository);
//        this.restSubmetaDocComplementarMockMvc = MockMvcBuilders.standaloneSetup(submetaDocComplementarResource)
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
//    public static SubmetaDocComplementar createEntity(EntityManager em) {
//        SubmetaDocComplementar submetaDocComplementar = new SubmetaDocComplementar()
//            .dataAssociacao(DEFAULT_DATA_ASSOCIACAO);
//        return submetaDocComplementar;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static SubmetaDocComplementar createUpdatedEntity(EntityManager em) {
//        SubmetaDocComplementar submetaDocComplementar = new SubmetaDocComplementar()
//            .dataAssociacao(UPDATED_DATA_ASSOCIACAO);
//        return submetaDocComplementar;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        submetaDocComplementar = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createSubmetaDocComplementar() throws Exception {
//        int databaseSizeBeforeCreate = submetaDocComplementarRepository.findAll().size();
//
//        // Create the SubmetaDocComplementar
//        restSubmetaDocComplementarMockMvc.perform(post("/api/submeta-doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submetaDocComplementar)))
//            .andExpect(status().isCreated());
//
//        // Validate the SubmetaDocComplementar in the database
//        List<SubmetaDocComplementar> submetaDocComplementarList = submetaDocComplementarRepository.findAll();
//        assertThat(submetaDocComplementarList).hasSize(databaseSizeBeforeCreate + 1);
//        SubmetaDocComplementar testSubmetaDocComplementar = submetaDocComplementarList.get(submetaDocComplementarList.size() - 1);
//        assertThat(testSubmetaDocComplementar.getDataAssociacao()).isEqualTo(DEFAULT_DATA_ASSOCIACAO);
//    }
//
//    @Test
//    @Transactional
//    public void createSubmetaDocComplementarWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = submetaDocComplementarRepository.findAll().size();
//
//        // Create the SubmetaDocComplementar with an existing ID
//        submetaDocComplementar.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restSubmetaDocComplementarMockMvc.perform(post("/api/submeta-doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submetaDocComplementar)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the SubmetaDocComplementar in the database
//        List<SubmetaDocComplementar> submetaDocComplementarList = submetaDocComplementarRepository.findAll();
//        assertThat(submetaDocComplementarList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllSubmetaDocComplementars() throws Exception {
//        // Initialize the database
//        submetaDocComplementarRepository.saveAndFlush(submetaDocComplementar);
//
//        // Get all the submetaDocComplementarList
//        restSubmetaDocComplementarMockMvc.perform(get("/api/submeta-doc-complementars?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(submetaDocComplementar.getId().intValue())))
//            .andExpect(jsonPath("$.[*].dataAssociacao").value(hasItem(DEFAULT_DATA_ASSOCIACAO.toString())));
//    }
//    
//    @Test
//    @Transactional
//    public void getSubmetaDocComplementar() throws Exception {
//        // Initialize the database
//        submetaDocComplementarRepository.saveAndFlush(submetaDocComplementar);
//
//        // Get the submetaDocComplementar
//        restSubmetaDocComplementarMockMvc.perform(get("/api/submeta-doc-complementars/{id}", submetaDocComplementar.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(submetaDocComplementar.getId().intValue()))
//            .andExpect(jsonPath("$.dataAssociacao").value(DEFAULT_DATA_ASSOCIACAO.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingSubmetaDocComplementar() throws Exception {
//        // Get the submetaDocComplementar
//        restSubmetaDocComplementarMockMvc.perform(get("/api/submeta-doc-complementars/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateSubmetaDocComplementar() throws Exception {
//        // Initialize the database
//        submetaDocComplementarRepository.saveAndFlush(submetaDocComplementar);
//
//        int databaseSizeBeforeUpdate = submetaDocComplementarRepository.findAll().size();
//
//        // Update the submetaDocComplementar
//        SubmetaDocComplementar updatedSubmetaDocComplementar = submetaDocComplementarRepository.findById(submetaDocComplementar.getId()).get();
//        // Disconnect from session so that the updates on updatedSubmetaDocComplementar are not directly saved in db
//        em.detach(updatedSubmetaDocComplementar);
//        updatedSubmetaDocComplementar
//            .dataAssociacao(UPDATED_DATA_ASSOCIACAO);
//
//        restSubmetaDocComplementarMockMvc.perform(put("/api/submeta-doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedSubmetaDocComplementar)))
//            .andExpect(status().isOk());
//
//        // Validate the SubmetaDocComplementar in the database
//        List<SubmetaDocComplementar> submetaDocComplementarList = submetaDocComplementarRepository.findAll();
//        assertThat(submetaDocComplementarList).hasSize(databaseSizeBeforeUpdate);
//        SubmetaDocComplementar testSubmetaDocComplementar = submetaDocComplementarList.get(submetaDocComplementarList.size() - 1);
//        assertThat(testSubmetaDocComplementar.getDataAssociacao()).isEqualTo(UPDATED_DATA_ASSOCIACAO);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingSubmetaDocComplementar() throws Exception {
//        int databaseSizeBeforeUpdate = submetaDocComplementarRepository.findAll().size();
//
//        // Create the SubmetaDocComplementar
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restSubmetaDocComplementarMockMvc.perform(put("/api/submeta-doc-complementars")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(submetaDocComplementar)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the SubmetaDocComplementar in the database
//        List<SubmetaDocComplementar> submetaDocComplementarList = submetaDocComplementarRepository.findAll();
//        assertThat(submetaDocComplementarList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteSubmetaDocComplementar() throws Exception {
//        // Initialize the database
//        submetaDocComplementarRepository.saveAndFlush(submetaDocComplementar);
//
//        int databaseSizeBeforeDelete = submetaDocComplementarRepository.findAll().size();
//
//        // Delete the submetaDocComplementar
//        restSubmetaDocComplementarMockMvc.perform(delete("/api/submeta-doc-complementars/{id}", submetaDocComplementar.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<SubmetaDocComplementar> submetaDocComplementarList = submetaDocComplementarRepository.findAll();
//        assertThat(submetaDocComplementarList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(SubmetaDocComplementar.class);
//        SubmetaDocComplementar submetaDocComplementar1 = new SubmetaDocComplementar();
//        submetaDocComplementar1.setId(1L);
//        SubmetaDocComplementar submetaDocComplementar2 = new SubmetaDocComplementar();
//        submetaDocComplementar2.setId(submetaDocComplementar1.getId());
//        assertThat(submetaDocComplementar1).isEqualTo(submetaDocComplementar2);
//        submetaDocComplementar2.setId(2L);
//        assertThat(submetaDocComplementar1).isNotEqualTo(submetaDocComplementar2);
//        submetaDocComplementar1.setId(null);
//        assertThat(submetaDocComplementar1).isNotEqualTo(submetaDocComplementar2);
//    }
//}
