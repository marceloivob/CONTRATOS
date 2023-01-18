package br.gov.economia.maisbrasil.contratos.bc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.cadastro.grpc.EmpresaResponse;
import br.gov.economia.maisbrasil.cadastro.grpc.MaisBrasilCadastroGRPCClient;
import br.gov.economia.maisbrasil.contratos.bc.exception.ErroIntegracaoCPSException;
import br.gov.economia.maisbrasil.contratos.domain.CategoriaPropostaEnum;
import br.gov.economia.maisbrasil.contratos.domain.ModalidadeInstrumentoEnum;
import br.gov.economia.maisbrasil.contratos.domain.SituacaoContratoEnum;
import br.gov.economia.maisbrasil.contratos.domain.StatusCheckEnum;
import br.gov.economia.maisbrasil.contratos.domain.bd.AioBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.AnexoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.ContratoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.FornecedorBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.LoteBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.PoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.PropostaBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.SubmetaBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.CheckDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.ContratoDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.ItemDeVerificacaoParaEnum;
import br.gov.economia.maisbrasil.contratos.domain.dto.LoteDTO;
import br.gov.economia.maisbrasil.contratos.domain.integracao.DocComplementarIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.HistoricoAcffoIntegracao;
import br.gov.economia.maisbrasil.contratos.integracao.client.CpsClientGRPCProducer;
import br.gov.economia.maisbrasil.contratos.integracao.client.SiconvClientGRPCProducer;
import br.gov.economia.maisbrasil.contratos.repository.AcffoRepository;
import br.gov.economia.maisbrasil.contratos.repository.AioRepository;
import br.gov.economia.maisbrasil.contratos.repository.AnexoRepository;
import br.gov.economia.maisbrasil.contratos.repository.ContratoRepository;
import br.gov.economia.maisbrasil.contratos.repository.FornecedorRepository;
import br.gov.economia.maisbrasil.contratos.repository.LoteRepository;
import br.gov.economia.maisbrasil.contratos.repository.PoRepository;
import br.gov.economia.maisbrasil.contratos.repository.PropostaRepository;
import br.gov.economia.maisbrasil.contratos.repository.SubmetaRepository;
import br.gov.planejamento.siconv.grpc.CpsGRPCClient;
import br.gov.planejamento.siconv.grpc.DadosPropostaResponse;
import br.gov.planejamento.siconv.grpc.DataDaPropostaResponse;
import br.gov.planejamento.siconv.grpc.ReferenciaPrecoResponse;
import br.gov.planejamento.siconv.grpc.SiconvGRPCClient;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChecklistBC {

	public static final String LABEL_RECEBIMENTO_RECURSOS = "Recebimento dos recursos após o aceite da fase de análise do Projeto Básico.";
	public static final String LABEL_CLAUSULA_SUSPENSIVA = "Inexistência de cláusula suspensiva.";
	public static final String LABEL_LICENCA_AMBIENTAL = "Documentação da licença ambiental de instalação (Existe e está vigente) ou dispensa (Existe).";
	public static final String LABEL_ANEXO_INSTRUMENTO_CONTRATUAL = "Anexo do Instrumento Contratual.";
	public static final String LABEL_ANEXO_PUBLICACAO_EXTRATO = "Anexo da Publicação do Extrato do Instrumento Contratual.";
	public static final String LABEL_PRAZO_EXECUCAO = "O prazo de execução do objeto do Instrumento Contratual está dentro do prazo de vigência do Instrumento Contratual.";
	public static final String LABEL_INSTRUMENTO_CONTRATUAL_VIGENTE = "Instrumento Contratual ou Termo Aditivo está vigente.";
	public static final String LABEL_SEM_PENDENCIAS = "Não há pendências da AIO no módulo de pendências.";
	public static final String LABEL_EMPRESA_CADASTRADA = "A empresa vencedora do processo de compra está cadastrada na Plataforma +Brasil.";

	private static final Long MANIFESTO_LICENCA_INSTALACAO = 3L;
	private static final Long MANIFESTO_DISPENSA = 1L;

	private final AnexoRepository anexoRepository;
	private final ContratoRepository contratoRepository;
	private final LoteRepository loteRepository;
	private final SubmetaRepository submetaRepository;
	private final PoRepository poRepository;
	private final PropostaRepository propostaRepository;
	private final FornecedorRepository fornecedorRepository;
	private final AcffoRepository acffoRepository;
	private final AioRepository aioRepository;

	@Value("${maisbrasil.cadastro.url}")
	private String enderecoServicoCadastro;

	@Value("${maisbrasil.cadastro.porta}")
	private Integer portaServicoCadastro;

	@Autowired
	public ChecklistBC(AnexoRepository anexoRepository, //
			ContratoRepository contratoRepository, //
			LoteRepository loteRepository, //
			SubmetaRepository submetaRepository, //
			PoRepository poRepository, //
			PropostaRepository propostaRepository, //
			FornecedorRepository fornecedorRepository, //
			AcffoRepository acffoRepository, //
			AioRepository aioRepository) {

		this.anexoRepository = anexoRepository;
		this.contratoRepository = contratoRepository;
		this.loteRepository = loteRepository;
		this.submetaRepository = submetaRepository;
		this.poRepository = poRepository;
		this.propostaRepository = propostaRepository;
		this.fornecedorRepository = fornecedorRepository;
		this.acffoRepository = acffoRepository;
		this.aioRepository = aioRepository;
	}

	public List<CheckDTO> recuperarChecklistDoContrato(Long idContrato, boolean concluirContrato, boolean atualizarContrato) {
		Objects.requireNonNull(idContrato);

		ContratoBD contrato = contratoRepository.recuperarContratoPorId(idContrato).get();

		return this.recuperarChecklistDoContratoBD(contrato, concluirContrato, atualizarContrato);
	}

	public List<CheckDTO> recuperarChecklistDoContratoBD(ContratoBD contrato, boolean concluirContrato, boolean atualizarContrato) {
		Objects.requireNonNull(contrato);
		boolean aioEmitido = isAioEmitido(contrato.getPropostaId());
		boolean contratoConcluido = isContratoConcluido(contrato);
		PropostaBD proposta = this.propostaRepository.recuperarPropostaPorId(contrato.getPropostaId()).get();

		List<CheckDTO> checklist = new LinkedList<>();

		if (aioEmitido) {
			checklist.addAll(geraChecklistOkAIO(proposta));

		} else if(!concluirContrato) {
			checklist.add(item01(proposta));
			checklist.add(item02(proposta));
			checklist.add(item03(proposta));
		}

		if(contratoConcluido && !atualizarContrato) {
			checklist.addAll(geraChecklistOkContrato());

		} else {
			checklist.add(item04(contrato.getId()));
			checklist.add(item05(contrato.getId()));
			checklist.add(item06(contrato));
			checklist.add(item07(contrato));
			checklist.add(item09(contrato.getId()));
		}

		return checklist;
	}

	public List<CheckDTO> recuperarChecklistDoContratoAlterar(ContratoDTO contrato) {
		Objects.requireNonNull(contrato);
		ContratoBD contratoBD = contrato.converterParaBD();

		List<CheckDTO> checklist = new LinkedList<>();
		checklist.add(item04(contrato.getId()));
		checklist.add(item05(contrato.getId()));
		checklist.add(item06(contratoBD));
		checklist.add(item07(contratoBD));
		checklist.add(item09Alterar(contrato));

		return checklist;
	}

	public List<CheckDTO> recuperarChecklistDaProposta(Long idProposta) {
		Objects.requireNonNull(idProposta);

		PropostaBD proposta = this.propostaRepository.recuperarPropostaPorId(idProposta).get();

		// Verifica se AIO está emitido. Caso positivo, retorna checklist ok.
		if(isAioEmitido(idProposta)) {
			return geraChecklistOkAIO(proposta);
		}

		List<CheckDTO> checklist = new LinkedList<>();

		checklist.add(item01(proposta));
		checklist.add(item02(proposta));
		checklist.add(item03(proposta));

		return checklist;
	}

	public List<CheckDTO> recuperarPreChecklistDoContrato(ContratoBD contrato) {
		Objects.requireNonNull(contrato);
		List<CheckDTO> checklist = new LinkedList<>();

		// verifica se o Contrato está concluído. Caso positivo, retorna checklist ok.
		if(isContratoConcluido(contrato)) {
			checklist.add(item09ok());
			return checklist;
		}

		checklist.add(item09(contrato.getId()));

		return checklist;
	}

	public Boolean tudoOk(Long idContrato, boolean concluirContrato, boolean atualizarContrato) {
		List<CheckDTO> checklist = this.recuperarChecklistDoContrato(idContrato, concluirContrato, atualizarContrato);
		return this.resolveChecklist(checklist);
	}

	public Boolean tudoOkBD(ContratoBD contrato, boolean concluirContrato, boolean atualizarContrato) {
		List<CheckDTO> checklist = this.recuperarChecklistDoContratoBD(contrato, concluirContrato, atualizarContrato);
		return this.resolveChecklist(checklist);
	}

	public Boolean resolveChecklist(List<CheckDTO> checklist) {
		for (CheckDTO checkDTO : checklist) {
			if (checkDTO.isPendente()) {
				return false;
			}
		}
		return true;
	}

	private CheckDTO item01(PropostaBD proposta) {
		CheckDTO c = new CheckDTO(ItemDeVerificacaoParaEnum.AIO);
		c.setIndice(1L);
		c.setLabel(LABEL_RECEBIMENTO_RECURSOS);

		c.setStatus(StatusCheckEnum.NOK.toString());
		c = verificaNaoSeAplicaItem01(proposta, c);

		if(StatusCheckEnum.NA.toString().equals(c.getStatus())) {
			return c;
		}

		try {
			if (this.consultaRecebimentoDosRecursos(proposta)) {
				c.setStatus(StatusCheckEnum.OK.toString());
			}
		} catch (Exception e) {
			log.error("Erro ao tentar consultar o recebimento dos recursos após o aceite da fase de análise do Projeto Básico.", e);
			c.setStatus(StatusCheckEnum.INDISPONIVEL.toString());
			c.setMensagemErro("Erro ao tentar consultar o recebimento dos recursos após o aceite da fase de análise do Projeto Básico.");
		}

		return c;
	}

	private String consultaNivelPropostaCPS(PropostaBD proposta) {
		String resultado = null;

		Double valorRepasse = proposta.getValorRepasse().doubleValue();
		Integer categoria = CategoriaPropostaEnum.fromDescricao(proposta.getCategoria()).getReferencia();

		String dataDaProposta = recuperaDataDaProposta(proposta);

		try {
			CpsGRPCClient cpsGRPCClient = CpsClientGRPCProducer.create();
			ReferenciaPrecoResponse response = cpsGRPCClient.consultarNivelPorData(valorRepasse, categoria, dataDaProposta);
			if (response != null) {
				resultado = response.getDescricao();
			}
			CpsClientGRPCProducer.shutdown();
			cpsGRPCClient = null;

		} catch (StatusRuntimeException sre) {
			if (naoTemEnquadramentoEmNenhumNivelDeContrato(sre)) {
				return "Não se enquadra em nenhum nível!";
			} else {
				throw new ErroIntegracaoCPSException(sre);
			}
		} catch (Exception e) {
			throw new ErroIntegracaoCPSException(e);
		}

		return resultado;
	}

	private boolean naoTemEnquadramentoEmNenhumNivelDeContrato(StatusRuntimeException sre) {
		// Serviço CPS-GRPC consultarNivelPorData retorna exceção se a proposta não se
		// enquadrar em nenhum nível
		return sre.getStatus().getCode() == Status.NOT_FOUND.getCode();
	}

	private boolean consultaRecebimentoDosRecursos(PropostaBD proposta) throws Exception {

		Optional<HistoricoAcffoIntegracao> hist = acffoRepository.recuperarHistoricoDeAceite(proposta.getIdSiconv());
		if (!hist.isPresent()) {
			// TODO É possível chegar aqui sem ter o projeto básico aceite?
			return false;
		}

		try {
			SiconvGRPCClient siconvGRPCClient = SiconvClientGRPCProducer.create();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			DadosPropostaResponse dadosProposta = siconvGRPCClient
					.consultarDataPrimeiraOBaposAnalise(proposta.getIdSiconv(), df.format(hist.get().getDt_registro()));

			SiconvClientGRPCProducer.shutdown();
			siconvGRPCClient = null;

			if (dadosProposta == null || //
				dadosProposta.getDataPrimeiraOBaposAnalise() == null || //
				dadosProposta.getDataPrimeiraOBaposAnalise().isEmpty()) {
				// Não recebeu recursos
				return false;
			}

		} catch (Exception e) {
			log.error("Erro ao tentar acessar o serviço GRPC do Siconv para consulta do Recebimento dos Recursos.", e);
			throw new RuntimeException("Erro ao tentar acessar o serviço GRPC do Siconv para consulta do Recebimento dos Recursos.");
		}

		return true;
	}

	private CheckDTO item02(PropostaBD proposta) {
		CheckDTO c = new CheckDTO(ItemDeVerificacaoParaEnum.AIO);
		c.setIndice(2L);
		c.setLabel(LABEL_CLAUSULA_SUSPENSIVA);

		String status = StatusCheckEnum.NOK.toString();

		try {
			if (this.propostaNaoPossuiClausulaSuspensiva(proposta)) {
				status = StatusCheckEnum.OK.toString();
			}
		} catch (Exception e) {
			log.error("Erro ao tentar consultar a existência de cláusula suspensiva.", e);
			status = StatusCheckEnum.INDISPONIVEL.toString();
			c.setMensagemErro("Erro ao tentar consultar a existência de cláusula suspensiva.");
		}

		c.setStatus(status);

		return c;
	}

	private boolean propostaNaoPossuiClausulaSuspensiva(PropostaBD proposta) throws Exception {
		SiconvGRPCClient siconvGRPCClient = SiconvClientGRPCProducer.create();

		Boolean possuiClausulaSuspensiva = siconvGRPCClient.isConvenioSuspensiva(proposta.getIdSiconv());

		SiconvClientGRPCProducer.shutdown();
		siconvGRPCClient = null;

		if (possuiClausulaSuspensiva == null) {
			throw new RuntimeException("Erro ao tentar consultar a existência de cláusula suspensiva.");
		}

		return !possuiClausulaSuspensiva;
	}

	private CheckDTO item03(PropostaBD proposta) {
		CheckDTO c = new CheckDTO(ItemDeVerificacaoParaEnum.AIO);
		c.setIndice(3L);
		c.setLabel(LABEL_LICENCA_AMBIENTAL);

		c.setStatus(StatusCheckEnum.NOK.toString());

		List<DocComplementarIntegracao> docs = acffoRepository
				.recuperarDocComplementarAmbiental(proposta.getIdSiconv());

		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date hoje = formatter.parse(formatter.format(new Date()));

			for (DocComplementarIntegracao dci : docs) {
				if ( (MANIFESTO_LICENCA_INSTALACAO.equals(dci.getTpManifAmbientalFk())
						&& !(hoje.after(dci.getDtValidaAte())) )
					  || MANIFESTO_DISPENSA.equals(dci.getTpManifAmbientalFk())     ) {

					c.setStatus(StatusCheckEnum.OK.toString());
				}
			}

		} catch (ParseException e) {
			throw new RuntimeException("Erro ao formatar data atual na validação da licença ambiental.");
		}

		return c;
	}

	private CheckDTO item04(Long idContrato) {
		CheckDTO c = new CheckDTO(ItemDeVerificacaoParaEnum.CONTRATO);
		c.setIndice(1L);
		c.setLabel(LABEL_ANEXO_INSTRUMENTO_CONTRATUAL);

		c.setStatus(StatusCheckEnum.NOK.toString());

		List<AnexoBD> anexos = anexoRepository.recuperarAnexoPorIdContrato(idContrato);
		for (AnexoBD anexoBD : anexos) {
			if ("INSTRUMENTO_CONTRATUAL".equals(anexoBD.getInTipoAnexo()))
				c.setStatus(StatusCheckEnum.OK.toString());
		}

		return c;
	}

	private CheckDTO item05(Long idContrato) {
		CheckDTO c = new CheckDTO(ItemDeVerificacaoParaEnum.CONTRATO);
		c.setIndice(2L);
		c.setLabel(LABEL_ANEXO_PUBLICACAO_EXTRATO);
		c.setStatus(StatusCheckEnum.NOK.toString());

		List<AnexoBD> anexos = anexoRepository.recuperarAnexoPorIdContrato(idContrato);
		for (AnexoBD anexoBD : anexos) {
			if ("PUBLICACAO_EXTRATO".equals(anexoBD.getInTipoAnexo()))
				c.setStatus(StatusCheckEnum.OK.toString());
		}

		return c;
	}
	
	private CheckDTO item06(ContratoBD contrato) {
		CheckDTO c = new CheckDTO(ItemDeVerificacaoParaEnum.CONTRATO);
		c.setIndice(3L);
		c.setLabel(LABEL_PRAZO_EXECUCAO);
		c.setStatus(StatusCheckEnum.NOK.toString());

		Long maiorDuracaoDePO = 0L;
		
		List<LoteBD> lotes = loteRepository.recuperarLotesDoContrato(contrato.getId());
		for (LoteBD loteBD : lotes) {
			List<SubmetaBD> submetas = submetaRepository.recuperarSubmetasDoLote(loteBD.getId());
			for (SubmetaBD submetaBD : submetas) {
				PoBD po = poRepository.recuperarPoPorSubmeta(submetaBD.getId()).get();

				if (po.getQtMesesDuracaoObra() > maiorDuracaoDePO) {
					maiorDuracaoDePO = po.getQtMesesDuracaoObra();
				}
			}
		}

		if (contrato.getDuracaoVigenciaEmMeses() >= maiorDuracaoDePO) {
			c.setStatus(StatusCheckEnum.OK.toString());
		}

		return c;
	}

	private CheckDTO item07(ContratoBD contrato) {
		CheckDTO c = new CheckDTO(ItemDeVerificacaoParaEnum.CONTRATO);
		c.setIndice(4L);
		c.setLabel(LABEL_INSTRUMENTO_CONTRATUAL_VIGENTE);
		c.setStatus(StatusCheckEnum.NOK.toString());

		SimpleDateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");

		Date fimVigenciaContrato = java.sql.Date.valueOf(contrato.getFimVigencia());

		if (Date.from(Instant.now()).before(fimVigenciaContrato)
				|| formatoData.format(Date.from(Instant.now())).equals(formatoData.format(fimVigenciaContrato))) {
			c.setStatus(StatusCheckEnum.OK.toString());
		}

		return c;
	}

	private CheckDTO item08(Long idContrato) {
		CheckDTO c = new CheckDTO(ItemDeVerificacaoParaEnum.AIO);
		c.setIndice(6L);
		c.setLabel(LABEL_SEM_PENDENCIAS);
		c.setStatus(StatusCheckEnum.NOK.toString());

		// TODO Item 8 do checklist
		// c.setStatus( false );

		return c;
	}

	private CheckDTO item09(Long idContrato) {
		CheckDTO c = new CheckDTO(ItemDeVerificacaoParaEnum.CONTRATO);
		c.setIndice(5L);
		c.setLabel(LABEL_EMPRESA_CADASTRADA);

		String status = StatusCheckEnum.NOK.toString();

		List<LoteBD> lotes = this.loteRepository.recuperarLotesDoContrato(idContrato);

		if (lotes != null && !lotes.isEmpty()) {
			status = checaEmpresaCadastradaPlataforma(c, lotes);
		}

		c.setStatus(status);

		return c;
	}

	private CheckDTO item09Alterar(ContratoDTO contrato) {
		CheckDTO c = new CheckDTO(ItemDeVerificacaoParaEnum.CONTRATO);
		c.setIndice(5L);
		c.setLabel(LABEL_EMPRESA_CADASTRADA);

		String status = StatusCheckEnum.NOK.toString();

		List<LoteBD> lotes = new ArrayList<>();
		List<LoteDTO> lotesDTO = contrato.getLotes();
		for (LoteDTO loteDTO : lotesDTO) {
			lotes.add(loteDTO.converterParaBD());
		}

		if (lotes != null && !lotes.isEmpty()) {
			status = checaEmpresaCadastradaPlataforma(c, lotes);
		}

		c.setStatus(status);

		return c;
	}

	private String checaEmpresaCadastradaPlataforma(CheckDTO c, List<LoteBD> lotes) {
		String status;
		FornecedorBD empresaVencedora = this.fornecedorRepository
				.recuperarFornecedorPorId(lotes.get(0).getFornecedorId()).get();

		if (empresaVencedora.getTipoIdentificacao().equals("CNPJ")) {
			try {

				MaisBrasilCadastroGRPCClient cadastroGRPCClient = new MaisBrasilCadastroGRPCClient(enderecoServicoCadastro,
						portaServicoCadastro);

				EmpresaResponse empresaCadastrada = cadastroGRPCClient.getEmpresaByCnpj(empresaVencedora.getIdentificacao());
				cadastroGRPCClient.shutdown();
				cadastroGRPCClient = null;

				if (empresaCadastrada != null && empresaCadastrada.getCnpj() != null
						&& !empresaCadastrada.getCnpj().equals("")) {
					status = StatusCheckEnum.OK.toString();

				} else {
					c.setMensagemErro("Não existe na Plataforma +Brasil, o cadastro da empresa " + ""
							+ empresaVencedora.getIdentificacao() + " - " + empresaVencedora.getRazaoSocial() + ". "
							+ "Para concluir este instrumento contratual, será necessário que a empresa "
							+ "seja cadastrada na Plataforma +Brasil. Favor solicitar à empresa executora que providencie "
							+ "o respectivo cadastro na Plataforma +Brasil.");

					status = StatusCheckEnum.NOK.toString();
				}

			} catch (Exception e) {
				log.error("Serviço Indisponível. Erro ao tentar consultar dados do Cadastro de Empresa da Plataforma +Brasil. Favor tentar novamente mais tarde.", e);
				c.setMensagemErro(
						"Serviço Indisponível. Erro ao tentar consultar dados do Cadastro de Empresa da Plataforma +Brasil. Favor tentar novamente mais tarde.");
				status = StatusCheckEnum.INDISPONIVEL.toString();
			}
		} else {
			status = StatusCheckEnum.NA.toString();
		}
		return status;
	}

	private List<CheckDTO> geraChecklistOkContrato() {
		List<CheckDTO> res = new ArrayList<>();

		CheckDTO c = new CheckDTO(ItemDeVerificacaoParaEnum.CONTRATO);
		c.setIndice(1L);
		c.setLabel(LABEL_ANEXO_INSTRUMENTO_CONTRATUAL);
		c.setStatus(StatusCheckEnum.OK.toString());
		res.add(c);

		c = new CheckDTO(ItemDeVerificacaoParaEnum.CONTRATO);
		c.setIndice(2L);
		c.setLabel(LABEL_ANEXO_PUBLICACAO_EXTRATO);
		c.setStatus(StatusCheckEnum.OK.toString());
		res.add(c);

		c = new CheckDTO(ItemDeVerificacaoParaEnum.CONTRATO);
		c.setIndice(3L);
		c.setLabel(LABEL_PRAZO_EXECUCAO);
		c.setStatus(StatusCheckEnum.OK.toString());
		res.add(c);

		c = new CheckDTO(ItemDeVerificacaoParaEnum.CONTRATO);
		c.setIndice(4L);
		c.setLabel(LABEL_INSTRUMENTO_CONTRATUAL_VIGENTE);
		c.setStatus(StatusCheckEnum.OK.toString());
		res.add(c);

		c = new CheckDTO(ItemDeVerificacaoParaEnum.CONTRATO);
		c.setIndice(5L);
		c.setLabel(LABEL_EMPRESA_CADASTRADA);
		c.setStatus(StatusCheckEnum.OK.toString());
		res.add(c);

		return res;
	}

	private List<CheckDTO> geraChecklistOkAIO(PropostaBD proposta) {
		List<CheckDTO> res = new ArrayList<>();

		CheckDTO c = new CheckDTO(ItemDeVerificacaoParaEnum.AIO);
		c.setIndice(1L);
		c.setLabel(LABEL_RECEBIMENTO_RECURSOS);
		c.setStatus(StatusCheckEnum.OK.toString());
		c = verificaNaoSeAplicaItem01(proposta, c);
		res.add(c);

		c = new CheckDTO(ItemDeVerificacaoParaEnum.AIO);
		c.setIndice(2L);
		c.setLabel(LABEL_CLAUSULA_SUSPENSIVA);
		c.setStatus(StatusCheckEnum.OK.toString());
		res.add(c);

		c = new CheckDTO(ItemDeVerificacaoParaEnum.AIO);
		c.setIndice(3L);
		c.setLabel(LABEL_LICENCA_AMBIENTAL);
		c.setStatus(StatusCheckEnum.OK.toString());
		res.add(c);

		return res;
	}

	private CheckDTO verificaNaoSeAplicaItem01(PropostaBD proposta, CheckDTO c) {
		if ( ModalidadeInstrumentoEnum.TERMO_DE_COMPROMISSO_CONCEDENTE.getValor() == proposta.getModalidade().longValue()
				|| ModalidadeInstrumentoEnum.TERMO_DE_COMPROMISSO_MANDATARIA.getValor() == proposta.getModalidade().longValue()) {

			c.setStatus(StatusCheckEnum.NA.toString());
		} else {
			try {
				String nivel = consultaNivelPropostaCPS(proposta);
				if (nivel != null) {
					if (!(nivel.equalsIgnoreCase("Nível I") || nivel.equalsIgnoreCase("Nível I - A"))) {
						c.setStatus(StatusCheckEnum.NA.toString());
					}
				}
			} catch (RuntimeException e) {
				log.error("Erro ao tentar consultar o nível da Proposta no módulo CPS.", e);
				c.setStatus(StatusCheckEnum.INDISPONIVEL.toString());
				c.setMensagemErro("Erro ao tentar consultar o nível da Proposta no módulo CPS.");
			}
		}

		return c;
	}

	private CheckDTO item09ok() {
		CheckDTO c = new CheckDTO(ItemDeVerificacaoParaEnum.CONTRATO);
		c.setIndice(5L);
		c.setLabel(LABEL_EMPRESA_CADASTRADA);
		c.setStatus(StatusCheckEnum.OK.toString());
		return c;
	}

	private boolean isAioEmitido(Long propostaId) {
		AioBD aio = aioRepository.recuperarAio(propostaId);
		return aio != null && aio.getDataEmissaoAio() != null;
	}

	private boolean isContratoConcluido(ContratoBD contrato) {
		return SituacaoContratoEnum.CONCLUIDO.getSigla().equals(contrato.getInSituacao());
	}

	private String recuperaDataDaProposta(PropostaBD propostaBD) {
		String resultado = null;

		try {
			SiconvGRPCClient siconvGrpcClient = SiconvClientGRPCProducer.create();
			DataDaPropostaResponse dpr = siconvGrpcClient.consultarDataDaProposta(propostaBD.getIdSiconv());

			SiconvClientGRPCProducer.shutdown();
			siconvGrpcClient = null;

			resultado = dpr.getDataDaProposta();
		} catch (Exception e) {
			log.error("Erro ao tentar acessar o serviço GRPC do Siconv para consulta da Data da Proposta.", e);
			throw new RuntimeException("Erro ao tentar acessar o serviço GRPC do Siconv para consulta da Data da Proposta.");
		}

		return resultado;
	}

}