package br.gov.economia.maisbrasil.contratos.web.rest;

import static br.gov.economia.maisbrasil.contratos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import br.gov.economia.maisbrasil.contratos.MaisbrasilContratosBackendApp;
import br.gov.economia.maisbrasil.contratos.bc.AnexoBC;
import br.gov.economia.maisbrasil.contratos.bc.ContratoBC;
import br.gov.economia.maisbrasil.contratos.domain.bd.AnexoBD;
import br.gov.economia.maisbrasil.contratos.web.rest.errors.ExceptionTranslator;

/**
 * Integration tests for the {@Link AnexoResource} REST controller.
 */
@Disabled
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = MaisbrasilContratosBackendApp.class)
public class AnexoResourceIT {

	private static final String DEFAULT_TX_DESCRICAO = "AAAAAAAAAA";
	private static final String UPDATED_TX_DESCRICAO = "BBBBBBBBBB";

	private static final String DEFAULT_IN_TIPO_ANEXO = "INSTRUMENTO_CONTRATUAL";
	private static final String UPDATED_IN_TIPO_ANEXO = "PUBLICACAO_EXTRATO";

	private static final LocalDate DEFAULT_DT_UPLOAD = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_DT_UPLOAD = LocalDate.now(ZoneId.systemDefault());

	private static final String DEFAULT_ID_CPF_ENVIADO_POR = "AAAAAAAAAA";
	private static final String UPDATED_ID_CPF_ENVIADO_POR = "BBBBBBBBBB";

	private static final String DEFAULT_NOME_ENVIADO_POR = "AAAAAAAAAA";
	private static final String UPDATED_NOME_ENVIADO_POR = "BBBBBBBBBB";

	private static final String DEFAULT_IN_PERFIL_USUARIO = "PROPONENTE";
	private static final String UPDATED_IN_PERFIL_USUARIO = "CONCEDENTE";

	private static final String DEFAULT_NM_ARQUIVO = "AAAAAAAAAA";
	private static final String UPDATED_NM_ARQUIVO = "BBBBBBBBBB";

	private static final String DEFAULT_CAMINHO = "AAAAAAAAAA";
	private static final String UPDATED_CAMINHO = "BBBBBBBBBB";

	private static final String DEFAULT_BUCKET = "AAAAAAAAAA";
	private static final String UPDATED_BUCKET = "BBBBBBBBBB";

	private static final Long DEFAULT_VERSAO_v1 = new Long(1);
	private static final Long UPDATED_VERSAO_v2 = new Long(2);
	private static final Long UPDATED_VERSAO_v3 = new Long(3);

	private static final String DEFAULT_ADT_LOGIN = "AAAAAAAAAA";
	private static final String UPDATED_ADT_LOGIN = "BBBBBBBBBB";

	private static final Instant DEFAULT_ADT_DATA_HORA = Instant.now();
	private static final Instant UPDATED_ADT_DATA_HORA = Instant.now();

	private static final String DEFAULT_ADT_OPERACAO = "INSERT";
	private static final String UPDATED_ADT_OPERACAO = "UPDATE";

	@Autowired
	private AnexoBC anexoBC;

	@Autowired
	private ContratoBC contratoBC;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private Validator validator;

	private MockMvc restAnexoMockMvc;

	private AnexoBD anexo;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		final AnexoResource anexoResource = new AnexoResource(anexoBC, contratoBC);
		this.restAnexoMockMvc = MockMvcBuilders.standaloneSetup(anexoResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
				.setConversionService(createFormattingConversionService()).setMessageConverters(jacksonMessageConverter)
				.setValidator(validator).build();
	}

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static AnexoBD createEntity() {
		AnexoBD anexo = new AnexoBD();

		anexo.setTxDescricao(DEFAULT_TX_DESCRICAO);
		anexo.setInTipoAnexo(DEFAULT_IN_TIPO_ANEXO);
		anexo.setDtUpload(DEFAULT_DT_UPLOAD);
		anexo.setIdCpfEnviadoPor(DEFAULT_ID_CPF_ENVIADO_POR);
		anexo.setNomeEnviadoPor(DEFAULT_NOME_ENVIADO_POR);
		anexo.setInPerfilUsuario(DEFAULT_IN_PERFIL_USUARIO);
		anexo.setNmArquivo(DEFAULT_NM_ARQUIVO);
		anexo.setCaminho(DEFAULT_CAMINHO);
		anexo.setBucket(DEFAULT_BUCKET);
		anexo.setVersao(DEFAULT_VERSAO_v1);
		anexo.setAdtLogin(DEFAULT_ADT_LOGIN);
		anexo.setAdtDataHora(DEFAULT_ADT_DATA_HORA);
		anexo.setAdtOperacao(DEFAULT_ADT_OPERACAO);

		return anexo;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static AnexoBD createUpdatedEntity() {
		AnexoBD anexo = new AnexoBD();

		anexo.setTxDescricao(UPDATED_TX_DESCRICAO);
		anexo.setInTipoAnexo(UPDATED_IN_TIPO_ANEXO);
		anexo.setDtUpload(UPDATED_DT_UPLOAD);
		anexo.setIdCpfEnviadoPor(UPDATED_ID_CPF_ENVIADO_POR);
		anexo.setNomeEnviadoPor(UPDATED_NOME_ENVIADO_POR);
		anexo.setInPerfilUsuario(UPDATED_IN_PERFIL_USUARIO);
		anexo.setNmArquivo(UPDATED_NM_ARQUIVO);
		anexo.setCaminho(UPDATED_CAMINHO);
		anexo.setBucket(UPDATED_BUCKET);
		anexo.setVersao(UPDATED_VERSAO_v2);
		anexo.setAdtLogin(UPDATED_ADT_LOGIN);
		anexo.setAdtDataHora(UPDATED_ADT_DATA_HORA);
		anexo.setAdtOperacao(UPDATED_ADT_OPERACAO);

		return anexo;
	}

	@BeforeEach
	public void initTest() {
		anexo = createEntity();
	}

	@Order(1)
	@Test
	@Transactional
	public void createAnexo() throws Exception {
		int databaseSizeBeforeCreate = anexoBC.recuperarTodosAnexos().size();

		// Create the Anexo
		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isCreated());

		// Validate the AnexoBD in the database
		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeCreate + 1);
		AnexoBD testAnexo = anexoList.get(anexoList.size() - 1);
		assertThat(testAnexo.getTxDescricao()).isEqualTo(DEFAULT_TX_DESCRICAO);
		assertThat(testAnexo.getInTipoAnexo()).isEqualTo(DEFAULT_IN_TIPO_ANEXO);
		assertThat(testAnexo.getDtUpload()).isEqualTo(DEFAULT_DT_UPLOAD);
		assertThat(testAnexo.getIdCpfEnviadoPor()).isEqualTo(DEFAULT_ID_CPF_ENVIADO_POR);
		assertThat(testAnexo.getNomeEnviadoPor()).isEqualTo(DEFAULT_NOME_ENVIADO_POR);
		assertThat(testAnexo.getInPerfilUsuario()).isEqualTo(DEFAULT_IN_PERFIL_USUARIO);
		assertThat(testAnexo.getNmArquivo()).isEqualTo(DEFAULT_NM_ARQUIVO);
		assertThat(testAnexo.getCaminho()).isEqualTo(DEFAULT_CAMINHO);
		assertThat(testAnexo.getBucket()).isEqualTo(DEFAULT_BUCKET);
		assertThat(testAnexo.getVersao()).isEqualTo(DEFAULT_VERSAO_v1);
		assertThat(testAnexo.getAdtLogin()).isEqualTo(DEFAULT_ADT_LOGIN);
		assertThat(testAnexo.getAdtDataHora()).isEqualTo(DEFAULT_ADT_DATA_HORA);
		assertThat(testAnexo.getAdtOperacao()).isEqualTo(DEFAULT_ADT_OPERACAO);
	}

	@Test
	@Transactional
	public void createAnexoWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = anexoBC.recuperarTodosAnexos().size();

		// Create the Anexo with an existing ID
		anexo.setId(1L);

		// An entity with an existing ID cannot be created, so this API call must fail
		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isBadRequest());

		// Validate the Anexo in the database
		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkTxDescricaoIsRequired() throws Exception {
		int databaseSizeBeforeTest = anexoBC.recuperarTodosAnexos().size();
		// set the field null
		anexo.setTxDescricao(null);

		// Create the Anexo, which fails.

		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isBadRequest());

		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkInTipoAnexoIsRequired() throws Exception {
		int databaseSizeBeforeTest = anexoBC.recuperarTodosAnexos().size();
		// set the field null
		anexo.setInTipoAnexo(null);

		// Create the Anexo, which fails.

		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isBadRequest());

		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkDtUploadIsRequired() throws Exception {
		int databaseSizeBeforeTest = anexoBC.recuperarTodosAnexos().size();
		// set the field null
		anexo.setDtUpload(null);

		// Create the Anexo, which fails.

		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isBadRequest());

		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkIdCpfEnviadoPorIsRequired() throws Exception {
		int databaseSizeBeforeTest = anexoBC.recuperarTodosAnexos().size();
		// set the field null
		anexo.setIdCpfEnviadoPor(null);

		// Create the Anexo, which fails.

		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isBadRequest());

		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkNomeEnviadoPorIsRequired() throws Exception {
		int databaseSizeBeforeTest = anexoBC.recuperarTodosAnexos().size();
		// set the field null
		anexo.setNomeEnviadoPor(null);

		// Create the Anexo, which fails.

		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isBadRequest());

		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkInPerfilUsuarioIsRequired() throws Exception {
		int databaseSizeBeforeTest = anexoBC.recuperarTodosAnexos().size();
		// set the field null
		anexo.setInPerfilUsuario(null);

		// Create the Anexo, which fails.

		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isBadRequest());

		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkNmArquivoIsRequired() throws Exception {
		int databaseSizeBeforeTest = anexoBC.recuperarTodosAnexos().size();
		// set the field null
		anexo.setNmArquivo(null);

		// Create the Anexo, which fails.

		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isBadRequest());

		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkCaminhoIsRequired() throws Exception {
		int databaseSizeBeforeTest = anexoBC.recuperarTodosAnexos().size();
		// set the field null
		anexo.setCaminho(null);

		// Create the Anexo, which fails.

		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isBadRequest());

		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkBucketIsRequired() throws Exception {
		int databaseSizeBeforeTest = anexoBC.recuperarTodosAnexos().size();
		// set the field null
		anexo.setBucket(null);

		// Create the Anexo, which fails.

		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isBadRequest());

		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkVersaoIsRequired() throws Exception {
		int databaseSizeBeforeTest = anexoBC.recuperarTodosAnexos().size();
		// set the field null
		anexo.setVersao(null);

		// Create the Anexo, which fails.

		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isBadRequest());

		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkAdtLoginIsRequired() throws Exception {
		int databaseSizeBeforeUpdate = anexoBC.recuperarTodosAnexos().size();

		// set the field null
		anexo.setAdtLogin(null);

		// Create the Anexo, which fails.

		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isCreated());

		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void checkAdtDataHoraIsRequired() throws Exception {

		int databaseSizeBeforeUpdate = anexoBC.recuperarTodosAnexos().size();

		// set the field null
		anexo.setAdtDataHora(null);

		// Create the Anexo, which fails.
		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isCreated());

		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void checkAdtOperacaoIsRequired() throws Exception {

		int databaseSizeBeforeUpdate = anexoBC.recuperarTodosAnexos().size();

		// set the field null
		anexo.setAdtOperacao(null);

		// Create the Anexo, which fails.

		restAnexoMockMvc.perform(post("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isCreated());

		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Order(4)
	@Test
	@Transactional
	public void getAllAnexos() throws Exception {
		// Initialize the database
		anexo = anexoBC.inserirAnexo(anexo);

		// Get all the anexoList
		restAnexoMockMvc.perform(get("/api/anexos?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(anexo.getId().intValue())))
				.andExpect(jsonPath("$.[*].txDescricao").value(hasItem(DEFAULT_TX_DESCRICAO.toString())))
				.andExpect(jsonPath("$.[*].inTipoAnexo").value(hasItem(DEFAULT_IN_TIPO_ANEXO.toString())))
				.andExpect(jsonPath("$.[*].dtUpload").value(hasItem(DEFAULT_DT_UPLOAD.toString())))
				.andExpect(jsonPath("$.[*].idCpfEnviadoPor").value(hasItem(DEFAULT_ID_CPF_ENVIADO_POR.toString())))
				.andExpect(jsonPath("$.[*].nomeEnviadoPor").value(hasItem(DEFAULT_NOME_ENVIADO_POR.toString())))
				.andExpect(jsonPath("$.[*].inPerfilUsuario").value(hasItem(DEFAULT_IN_PERFIL_USUARIO.toString())))
				.andExpect(jsonPath("$.[*].nmArquivo").value(hasItem(DEFAULT_NM_ARQUIVO.toString())))
				.andExpect(jsonPath("$.[*].caminho").value(hasItem(DEFAULT_CAMINHO.toString())))
				.andExpect(jsonPath("$.[*].bucket").value(hasItem(DEFAULT_BUCKET.toString())))
				.andExpect(jsonPath("$.[*].versao").value(hasItem(DEFAULT_VERSAO_v1.intValue())))
				.andExpect(jsonPath("$.[*].adtLogin").value(hasItem(DEFAULT_ADT_LOGIN.toString())))
				.andExpect(jsonPath("$.[*].adtDataHora").value(hasItem(DEFAULT_ADT_DATA_HORA.toString())))
				.andExpect(jsonPath("$.[*].adtOperacao").value(hasItem(DEFAULT_ADT_OPERACAO.toString())));
	}

	@Order(3)
	@Test
	@Transactional
	public void getAnexo() throws Exception {
		// Initialize the database
		anexo = anexoBC.inserirAnexo(anexo);

		// Get the anexo
		restAnexoMockMvc.perform(get("/api/anexos/{id}", anexo.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(anexo.getId().intValue()))
				.andExpect(jsonPath("$.txDescricao").value(DEFAULT_TX_DESCRICAO.toString()))
				.andExpect(jsonPath("$.inTipoAnexo").value(DEFAULT_IN_TIPO_ANEXO.toString()))
				.andExpect(jsonPath("$.dtUpload").value(DEFAULT_DT_UPLOAD.toString()))
				.andExpect(jsonPath("$.idCpfEnviadoPor").value(DEFAULT_ID_CPF_ENVIADO_POR.toString()))
				.andExpect(jsonPath("$.nomeEnviadoPor").value(DEFAULT_NOME_ENVIADO_POR.toString()))
				.andExpect(jsonPath("$.inPerfilUsuario").value(DEFAULT_IN_PERFIL_USUARIO.toString()))
				.andExpect(jsonPath("$.nmArquivo").value(DEFAULT_NM_ARQUIVO.toString()))
				.andExpect(jsonPath("$.caminho").value(DEFAULT_CAMINHO.toString()))
				.andExpect(jsonPath("$.bucket").value(DEFAULT_BUCKET.toString()))
				.andExpect(jsonPath("$.versao").value(DEFAULT_VERSAO_v1.intValue()))
				.andExpect(jsonPath("$.adtLogin").value(DEFAULT_ADT_LOGIN.toString()))
				.andExpect(jsonPath("$.adtDataHora").value(DEFAULT_ADT_DATA_HORA.toString()))
				.andExpect(jsonPath("$.adtOperacao").value(DEFAULT_ADT_OPERACAO.toString()));
	}

	@Test
	@Transactional
	public void getNonExistingAnexo() throws Exception {
		// Get the anexo
		restAnexoMockMvc.perform(get("/api/anexos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Order(2)
	@Test
	@Transactional
	public void updateAnexo() throws Exception {
		AnexoBD anexoIncluido = anexoBC.inserirAnexo(anexo);

		AnexoBD anexoAtualizadoComID = createUpdatedEntity();
		anexoAtualizadoComID.setId(anexoIncluido.getId());

		// Initialize the database
		AnexoBD anexoAtualizado = anexoBC.alterarAnexo(anexoAtualizadoComID);

		int databaseSizeBeforeUpdate = anexoBC.recuperarTodosAnexos().size();

		// Update the anexo
		AnexoBD updatedAnexo = anexoBC.recuperarAnexoPorId(anexoAtualizado.getId()).get();

		// Disconnect from session so that the updates on updatedAnexo are not directly
		// saved in db
		updatedAnexo.setTxDescricao(UPDATED_TX_DESCRICAO);
		updatedAnexo.setInTipoAnexo(UPDATED_IN_TIPO_ANEXO);
		updatedAnexo.setDtUpload(UPDATED_DT_UPLOAD);
		updatedAnexo.setIdCpfEnviadoPor(UPDATED_ID_CPF_ENVIADO_POR);
		updatedAnexo.setNomeEnviadoPor(UPDATED_NOME_ENVIADO_POR);
		updatedAnexo.setInPerfilUsuario(UPDATED_IN_PERFIL_USUARIO);
		updatedAnexo.setNmArquivo(UPDATED_NM_ARQUIVO);
		updatedAnexo.setCaminho(UPDATED_CAMINHO);
		updatedAnexo.setBucket(UPDATED_BUCKET);
		updatedAnexo.setVersao(UPDATED_VERSAO_v2);
		updatedAnexo.setAdtLogin(UPDATED_ADT_LOGIN);
		updatedAnexo.setAdtDataHora(UPDATED_ADT_DATA_HORA);
		updatedAnexo.setAdtOperacao(UPDATED_ADT_OPERACAO);

		restAnexoMockMvc.perform(put("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(updatedAnexo))).andExpect(status().isOk());

		// Validate the Anexo in the database
		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeUpdate);
		AnexoBD testAnexo = anexoList.get(anexoList.size() - 1);
		assertThat(testAnexo.getTxDescricao()).isEqualTo(UPDATED_TX_DESCRICAO);
		assertThat(testAnexo.getInTipoAnexo()).isEqualTo(UPDATED_IN_TIPO_ANEXO);
		assertThat(testAnexo.getDtUpload()).isEqualTo(UPDATED_DT_UPLOAD);
		assertThat(testAnexo.getIdCpfEnviadoPor()).isEqualTo(UPDATED_ID_CPF_ENVIADO_POR);
		assertThat(testAnexo.getNomeEnviadoPor()).isEqualTo(UPDATED_NOME_ENVIADO_POR);
		assertThat(testAnexo.getInPerfilUsuario()).isEqualTo(UPDATED_IN_PERFIL_USUARIO);
		assertThat(testAnexo.getNmArquivo()).isEqualTo(UPDATED_NM_ARQUIVO);
		assertThat(testAnexo.getCaminho()).isEqualTo(UPDATED_CAMINHO);
		assertThat(testAnexo.getBucket()).isEqualTo(UPDATED_BUCKET);
		assertThat(testAnexo.getVersao()).isEqualTo(UPDATED_VERSAO_v3);
		assertThat(testAnexo.getAdtLogin()).isEqualTo(DEFAULT_ADT_LOGIN);
		assertThat(testAnexo.getAdtDataHora()).isEqualTo(UPDATED_ADT_DATA_HORA);
		assertThat(testAnexo.getAdtOperacao()).isEqualTo(UPDATED_ADT_OPERACAO);
	}

	@Test
	@Transactional
	public void updateNonExistingAnexo() throws Exception {
		int databaseSizeBeforeUpdate = anexoBC.recuperarTodosAnexos().size();

		// Create the Anexo

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restAnexoMockMvc.perform(put("/api/anexos").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(anexo))).andExpect(status().isBadRequest());

		// Validate the Anexo in the database
		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeUpdate);
	}

	@Order(5)
	@Test
	@Transactional
	public void deleteAnexo() throws Exception {

		// Initialize the database
		AnexoBD anexoIncluido = anexoBC.inserirAnexo(anexo);

		int databaseSizeBeforeDelete = anexoBC.recuperarTodosAnexos().size();

		// Delete the anexo
		restAnexoMockMvc
				.perform(delete("/api/anexos/{id}", anexoIncluido.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<AnexoBD> anexoList = anexoBC.recuperarTodosAnexos();
		assertThat(anexoList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(AnexoBD.class);
		AnexoBD anexo1 = new AnexoBD();
		anexo1.setId(1L);
		AnexoBD anexo2 = new AnexoBD();
		anexo2.setId(anexo1.getId());
		assertThat(anexo1).isEqualTo(anexo2);
		anexo2.setId(2L);
		assertThat(anexo1).isNotEqualTo(anexo2);
		anexo1.setId(null);
		assertThat(anexo1).isNotEqualTo(anexo2);
	}
}
