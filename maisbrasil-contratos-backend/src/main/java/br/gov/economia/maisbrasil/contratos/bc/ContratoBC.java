package br.gov.economia.maisbrasil.contratos.bc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.bc.exception.AlteracaoNaoPermitidaException;
import br.gov.economia.maisbrasil.contratos.bc.exception.AlteracaoNaoPermitidaMedicaoIniciadaException;
import br.gov.economia.maisbrasil.contratos.bc.exception.AlteracaoParcialMedicaoIniciadaException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ChecklistNaoOkException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ConclusaoTermoAditivoSemAnexoPublicacaoTermoAditivoException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ConclusaoTermoAditivoSemAnexoTermoAditivoException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ContratoNaoConcluidoException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ContratoNaoEncontradoException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ContratoPossuiDatasInvalidasException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ExclusaoNaoPermitidaExisteAIOException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ExclusaoNaoPermitidaMedicaoIniciadaException;
import br.gov.economia.maisbrasil.contratos.bc.exception.IncoerenciaAoExcluirContratoException;
import br.gov.economia.maisbrasil.contratos.bc.exception.JaExisteContratoConcluidoComONumeroException;
import br.gov.economia.maisbrasil.contratos.bc.exception.PropostaNaoEncontradaException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ServicoMedicaoIndisponivelException;
import br.gov.economia.maisbrasil.contratos.bc.exception.TermoAditivoNaoEncontradoException;
import br.gov.economia.maisbrasil.contratos.bc.exception.TermoAditivoUnicoEmRascunhoException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.CampoObrigatorioException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;
import br.gov.economia.maisbrasil.contratos.core.exceptions.Severity;
import br.gov.economia.maisbrasil.contratos.domain.EventoGeradorHistoricoEnum;
import br.gov.economia.maisbrasil.contratos.domain.PermissaoAlteracaoContratoEnum;
import br.gov.economia.maisbrasil.contratos.domain.SituacaoContratoEnum;
import br.gov.economia.maisbrasil.contratos.domain.SituacaoHistoricoContratoEnum;
import br.gov.economia.maisbrasil.contratos.domain.SituacaoSubmetaEnum;
import br.gov.economia.maisbrasil.contratos.domain.SituacaoTermoAditivoEnum;
import br.gov.economia.maisbrasil.contratos.domain.TipoAnexoEnum;
import br.gov.economia.maisbrasil.contratos.domain.bd.AioBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.AnexoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.ContratoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.HistoricoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.SubmetaBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.TermoAditivoBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.AnexoDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.CheckDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.ContratoAnexoDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.ContratoDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.DadosChecklistDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.EmissaoAIORequestDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.LoteDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.MensagemDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.PropostaDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.SubmetaDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.TermoAditivoDTO;
import br.gov.economia.maisbrasil.contratos.integracao.restclient.AcionarServicoSiconvException;
import br.gov.economia.maisbrasil.contratos.integracao.restclient.AtualizarHistoricoResponse;
import br.gov.economia.maisbrasil.contratos.integracao.restclient.SiconvRestCancelarEmissaoAIOImpl;
import br.gov.economia.maisbrasil.contratos.integracao.restclient.SiconvRestEmissaoDiretaAIOImpl;
import br.gov.economia.maisbrasil.contratos.integracao.restclient.SiconvRestImpl;
import br.gov.economia.maisbrasil.contratos.repository.ContratoRepository;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.AnexoDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.ContratoDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.HistoricoDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.SubmetaDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.TermoAditivoDAO;
import br.gov.economia.maisbrasil.contratos.security.PermissionController;
import br.gov.serpro.siconv.med.grpc.services.MedicaoGRPCClient;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

@Controller
public class ContratoBC {

    private final Logger log = LoggerFactory.getLogger(ContratoBC.class);

	private final ContratoRepository contratoRepository;

	private final HistoricoBC historicoBC;

	private final AioBC aioBC;

	private final LoteBC loteBC;

	private final AnexoBC anexoBC;

	private final ChecklistBC checklistBC;

	private final PropostaBC propostaBC;

	private final SubmetaBC submetaBC;

	private final EmailBC emailBC;
	
	private final TermoAditivoBC termoAditivoBC;

	@Autowired
	private SiconvRestImpl siconvRest;

	@Autowired
	private SiconvRestCancelarEmissaoAIOImpl cancelarEmissaoAIOImpl;

	@Autowired
	private SiconvRestEmissaoDiretaAIOImpl siconvRestEmissaoDiretaAIO;

	@Autowired
	protected PermissionController controladorPermissoes;

	@Value("${maisbrasil.medicao.url}")
	private String enderecoMedicao;

	@Value("${maisbrasil.medicao.porta}")
	private Integer portaMedicao;

	@Value("${maisbrasil.contratos.inativar-emissao-aio-periodo-eleitoral}")
	private boolean inativarEmissaoAIOperiodoEleitoral;

	@Autowired
	private Jdbi jdbi;
	
	@Autowired
	public ContratoBC(ContratoRepository contratoRepository, HistoricoBC historicoBC, AioBC aioBC, LoteBC loteBC,
			AnexoBC anexoBC, ChecklistBC checklistBC, PropostaBC propostaBC, SubmetaBC submetaBC, EmailBC emailBC,
			TermoAditivoBC termoAditivoBC) {
		this.contratoRepository = contratoRepository;
		this.historicoBC = historicoBC;
		this.aioBC = aioBC;
		this.loteBC = loteBC;
		this.anexoBC = anexoBC;
		this.checklistBC = checklistBC;
		this.propostaBC = propostaBC;
		this.submetaBC = submetaBC;
		this.emailBC = emailBC;
		this.termoAditivoBC = termoAditivoBC;
	}

	public ContratoDTO inserir(ContratoDTO contrato) {
		Objects.requireNonNull(contrato);

		if (this.contratoRepository.seExisteContratoConcluidoComONumero(contrato)) {
			throw new JaExisteContratoConcluidoComONumeroException(contrato.getNumero());
		}

		this.validarDatasContrato(contrato);

		return jdbi.withHandle(transaction -> {
			
			contrato.setFimVigenciaOriginal(contrato.getFimVigencia());
			
			ContratoBD bd = transaction.attach(ContratoDAO.class).inserirContrato(contrato.converterParaBD());

			this.historicoBC.gerarEInserir(bd, EventoGeradorHistoricoEnum.SALVAR_RASCUNHO, transaction);

			this.loteBC.associarLotesAoContrato(contrato.getLotes(), bd.getId(), transaction);

			return this.converterBDParaDTODetalhado(bd);

		});
	}

	private void validarDatasContrato(ContratoDTO contrato){

		if (contrato == null ||
			contrato.getDataAssinatura() == null ||
			contrato.getDataPublicacao() == null ||
			contrato.getInicioVigencia() == null ||
			contrato.getFimVigencia() 	 == null) {

			throw new CampoObrigatorioException();
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate dataAssinatura = LocalDate.parse(contrato.getDataAssinatura().substring(0, 10), formatter);
		LocalDate dataPublicacao = LocalDate.parse(contrato.getDataPublicacao().substring(0, 10), formatter);
		LocalDate inicioVigencia = LocalDate.parse(contrato.getInicioVigencia().substring(0, 10), formatter);
		LocalDate fimVigencia = LocalDate.parse(contrato.getFimVigencia().substring(0, 10), formatter);
		LocalDate agora = LocalDate.now();

		this.compararDatasDepois(dataAssinatura, agora,             ErrorInfo.DATA_ASSINATURA_SUPERIOR_A_ATUAL);
		this.compararDatasDepois(dataPublicacao, agora,             ErrorInfo.DATA_PUBLICACAO_SUPERIOR_A_ATUAL);
		this.compararDatasAntes (dataPublicacao, dataAssinatura,    ErrorInfo.DATA_PUBLICACAO_INFERIOR_A_ASSINATURA);
		this.compararDatasAntes (inicioVigencia, dataAssinatura,    ErrorInfo.INICIO_VIGENCIA_INFERIOR_A_ASSINATURA);
		this.compararDatasDepois(inicioVigencia, fimVigencia,       ErrorInfo.INICIO_VIGENCIA_SUPERIOR_A_FIM_VIGENCIA);
		this.compararDatasAntes (fimVigencia,    dataAssinatura,    ErrorInfo.FIM_VIGENCIA_INFERIOR_A_ASSINATURA);
		this.compararDatasAntes (fimVigencia,    dataPublicacao,    ErrorInfo.FIM_VIGENCIA_INFERIOR_A_PUBLICACAO);
	}
	
	private void validarDatasTermoAditivoSalvarRascunho(TermoAditivoDTO termoAditivo, ContratoDTO contrato){

		if (termoAditivo == null ||
				(Boolean.TRUE.equals(termoAditivo.inAlteracaoVigencia) 
						&& termoAditivo.getNovaDataFimVigencia() == null)) {

			throw new CampoObrigatorioException();
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate inicioVigencia = LocalDate.parse(contrato.getInicioVigencia().substring(0, 10), formatter);
		LocalDate fimVigencia = LocalDate.parse(contrato.getFimVigencia().substring(0, 10), formatter);
		LocalDate agora = LocalDate.now();
		
		LocalDate dataAssinatura = null;
		LocalDate dataPublicacao = null;
		
		if (termoAditivo.getDataAssinatura() != null) {
			dataAssinatura = LocalDate.parse(termoAditivo.getDataAssinatura().substring(0, 10), formatter);	
			
			this.compararDatasDepois(dataAssinatura, agora, ErrorInfo.DATA_ASSINATURA_SUPERIOR_A_ATUAL);
		}
		
		if (termoAditivo.getDataPublicacao() != null) {
			dataPublicacao = LocalDate.parse(termoAditivo.getDataPublicacao().substring(0, 10), formatter);		
			
			this.compararDatasDepois(dataPublicacao, agora, ErrorInfo.DATA_PUBLICACAO_SUPERIOR_A_ATUAL);
		}

		if (dataAssinatura != null && dataPublicacao != null) {
			this.compararDatasAntes (dataPublicacao, dataAssinatura, ErrorInfo.DATA_PUBLICACAO_INFERIOR_A_ASSINATURA);			
		}
		
		if (Boolean.TRUE.equals(termoAditivo.inAlteracaoVigencia)) {
			LocalDate novoFimVigencia = LocalDate.parse(termoAditivo.getNovaDataFimVigencia().substring(0, 10), formatter);
			
			this.compararDatasAntes(novoFimVigencia, inicioVigencia, ErrorInfo.NOVO_FIM_VIGENCIA_INFERIOR_A_INICIO_VIGENCIA);
			if (dataAssinatura != null ) {
				this.compararDatasAntes(novoFimVigencia, dataAssinatura, ErrorInfo.NOVO_FIM_VIGENCIA_INFERIOR_A_ASSINATURA);				
			}
			if (dataPublicacao != null) {
				this.compararDatasAntes(novoFimVigencia, dataPublicacao, ErrorInfo.NOVO_FIM_VIGENCIA_INFERIOR_A_PUBLICACAO);				
			}
			this.compararDatasAntes(novoFimVigencia, fimVigencia, ErrorInfo.NOVO_FIM_VIGENCIA_INFERIOR_A_FIM_VIGENCIA_ATUAL);
		}

	}
	
	private void validarDatasTermoAditivoConcluir(TermoAditivoDTO termoAditivo, ContratoDTO contrato){

		if (termoAditivo == null ||
				termoAditivo.getDataAssinatura() == null ||
				termoAditivo.getDataPublicacao() == null ||
				(Boolean.TRUE.equals(termoAditivo.inAlteracaoVigencia) 
						&& termoAditivo.getNovaDataFimVigencia() == null)) {

			throw new CampoObrigatorioException();
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate dataAssinatura = LocalDate.parse(termoAditivo.getDataAssinatura().substring(0, 10), formatter);
		LocalDate dataPublicacao = LocalDate.parse(termoAditivo.getDataPublicacao().substring(0, 10), formatter);
		LocalDate inicioVigencia = LocalDate.parse(contrato.getInicioVigencia().substring(0, 10), formatter);
		LocalDate fimVigencia = LocalDate.parse(contrato.getFimVigencia().substring(0, 10), formatter);
		LocalDate agora = LocalDate.now();
		
		this.compararDatasDepois(dataAssinatura, agora,             ErrorInfo.DATA_ASSINATURA_SUPERIOR_A_ATUAL);
		this.compararDatasDepois(dataPublicacao, agora,             ErrorInfo.DATA_PUBLICACAO_SUPERIOR_A_ATUAL);
		this.compararDatasAntes (dataPublicacao, dataAssinatura,    ErrorInfo.DATA_PUBLICACAO_INFERIOR_A_ASSINATURA);
		
		if (Boolean.TRUE.equals(termoAditivo.inAlteracaoVigencia)) {
			LocalDate novoFimVigencia = LocalDate.parse(termoAditivo.getNovaDataFimVigencia().substring(0, 10), formatter);
			
			this.compararDatasAntes(novoFimVigencia, inicioVigencia, ErrorInfo.NOVO_FIM_VIGENCIA_INFERIOR_A_INICIO_VIGENCIA);
			this.compararDatasAntes(novoFimVigencia, dataAssinatura, ErrorInfo.NOVO_FIM_VIGENCIA_INFERIOR_A_ASSINATURA);
			this.compararDatasAntes(novoFimVigencia, dataPublicacao, ErrorInfo.NOVO_FIM_VIGENCIA_INFERIOR_A_PUBLICACAO);
			this.compararDatasAntes(novoFimVigencia, fimVigencia,    ErrorInfo.NOVO_FIM_VIGENCIA_INFERIOR_A_FIM_VIGENCIA_ATUAL);
		}

	}


	private void compararDatasAntes(LocalDate data1, LocalDate data2, ErrorInfo errorInfo) {
		
		if (data1.isBefore(data2)) {
			throw new ContratoPossuiDatasInvalidasException(
					errorInfo,
					data1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
					data2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
	}

	private void compararDatasDepois(LocalDate data1, LocalDate data2, ErrorInfo errorInfo) {
		if (data1.isAfter(data2)) {
			throw new ContratoPossuiDatasInvalidasException(
					errorInfo,
					data1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
					data2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
	}


	private void testaSePodeAlterarContrato(ContratoDTO contratoDaBase, ContratoDTO contratoAlterado) {

		PermissaoAlteracaoContratoEnum permissao = contratoDaBase.getPermissaoAlteracao();

		if (permissao.equals(PermissaoAlteracaoContratoEnum.INDISPONIVEL)) {
			throw new ServicoMedicaoIndisponivelException();
		}

		if (permissao.equals(PermissaoAlteracaoContratoEnum.NAO_PERMITIDA)) {
			throw new AlteracaoNaoPermitidaMedicaoIniciadaException();
		} else if (permissao.equals(PermissaoAlteracaoContratoEnum.PARCIAL)) {
			if (!contratoDaBase.getNumero().equals(contratoAlterado.getNumero())) {
				throw new AlteracaoParcialMedicaoIniciadaException();
			}
			if (!contratoDaBase.possuiLotesDoMesmoFornecedorELicitacao(contratoAlterado)) {
				throw new AlteracaoParcialMedicaoIniciadaException();
			}
		}
	}

	public ContratoDTO alterar(ContratoDTO contrato) {

		Objects.requireNonNull(contrato);

		log.debug("[CONTRATO ID: "+contrato.getId()+"] alterar contrato: validando datas");
		this.validarDatasContrato(contrato);
		log.debug("[CONTRATO ID: "+contrato.getId()+"] alterar contrato: datas validadas");

		return jdbi.withHandle(transaction -> {

			log.debug("[CONTRATO ID: "+contrato.getId()+"] alterar contrato: recuperar informacoes do contrato");
			ContratoDTO contratoDaBaseDTO = recuperarContratoPorId(contrato.getId());

			log.debug("[PROPOSTA ID: "+contratoDaBaseDTO.getPropostaIdSiconv()+"][CONTRATO ID: "+contratoDaBaseDTO.getId()+"] alterar contrato: testa se pode alterar contrato");
			this.testaSePodeAlterarContrato(contratoDaBaseDTO, contrato);

			ContratoBD contratoDaBase = contratoDaBaseDTO.converterParaBD();
			
			contrato.setFimVigenciaOriginal(contrato.getFimVigencia());
			ContratoBD contratoAlteradoBD = contrato.converterParaBD();

			log.debug("[PROPOSTA ID: "+contratoDaBaseDTO.getPropostaIdSiconv()+"][CONTRATO ID: "+contratoDaBaseDTO.getId()+"] alterar contrato: se existe contrato concluido com numero diferente do id");
			if (this.contratoRepository.seExisteContratoConcluidoComONumeroDiferenteDoId(contratoAlteradoBD)) {
				throw new JaExisteContratoConcluidoComONumeroException(contratoAlteradoBD.getNumero());
			}

			log.debug("[PROPOSTA ID: "+contratoDaBaseDTO.getPropostaIdSiconv()+"][CONTRATO ID: "+contratoDaBaseDTO.getId()+"] alterar contrato: checklist");
			if (SituacaoContratoEnum.CONCLUIDO.getSigla().equals(contratoDaBase.getInSituacao())) {
				List<CheckDTO> checklist = checklistBC.recuperarChecklistDoContratoAlterar(contrato);

				if (!checklistBC.resolveChecklist(checklist)) {
					String labelItensPendentes = "";
					for (CheckDTO item : checklist) {
						if (item.isPendente()){
							labelItensPendentes = labelItensPendentes + "<br> - "+ item.getLabel();
						}
					}
					throw new AlteracaoNaoPermitidaException(labelItensPendentes);
				}
			}
			
			log.debug("[PROPOSTA ID: "+contratoDaBaseDTO.getPropostaIdSiconv()+"][CONTRATO ID: "+contratoDaBaseDTO.getId()+"] alterar contrato: salvando contrato na base");
			contratoAlteradoBD = transaction.attach(ContratoDAO.class).alterarContrato(contratoAlteradoBD);

			log.debug("[PROPOSTA ID: "+contratoDaBaseDTO.getPropostaIdSiconv()+"][CONTRATO ID: "+contratoDaBaseDTO.getId()+"] alterar contrato: gerando historico");
			this.historicoBC.gerarEInserir(contratoAlteradoBD, EventoGeradorHistoricoEnum.SALVAR_RASCUNHO, 
					 this.recuperarSituacaoExibicaoContrato(contrato), transaction);

			log.debug("[PROPOSTA ID: "+contratoDaBaseDTO.getPropostaIdSiconv()+"][CONTRATO ID: "+contratoDaBaseDTO.getId()+"] alterar contrato: remover lotes");
			this.loteBC.removerLotesDoContrato(contrato.getId(), transaction);
			log.debug("[PROPOSTA ID: "+contratoDaBaseDTO.getPropostaIdSiconv()+"][CONTRATO ID: "+contratoDaBaseDTO.getId()+"] alterar contrato: associar lotes");
			this.loteBC.associarLotesAoContrato(contrato.getLotes(), contrato.getId(), transaction);

			log.debug("[PROPOSTA ID: "+contratoDaBaseDTO.getPropostaIdSiconv()+"][CONTRATO ID: "+contratoDaBaseDTO.getId()+"] alterar contrato: converter resultado para DTO");
			ContratoDTO retorno = this.converterBDParaDTODetalhado(contratoAlteradoBD);

			log.debug("[PROPOSTA ID: "+contratoDaBaseDTO.getPropostaIdSiconv()+"][CONTRATO ID: "+contratoDaBaseDTO.getId()+"] alterar contrato: retornar resultado");
			return retorno;

		});
	}

	public void excluir(Long id) {
		Objects.requireNonNull(id);
		// levanta excecao caso Contrato nao exista
		ContratoDTO contrato = this.recuperarContratoPorId(id);

		if (contrato.getPermissaoAlteracao().equals(PermissaoAlteracaoContratoEnum.INDISPONIVEL)) {
			throw new ServicoMedicaoIndisponivelException();
		}

		if (!contrato.getPermissaoAlteracao().equals(PermissaoAlteracaoContratoEnum.TOTAL)) {
			throw new ExclusaoNaoPermitidaMedicaoIniciadaException();
		}

		if (!this.validacaoExclusaoParaAIOEmitida(contrato)) {
			throw new ExclusaoNaoPermitidaExisteAIOException();
		}

		jdbi.useTransaction(transaction -> {
			if (existeAIOEmitidoParaProposta(contrato)) {
				atualizarSubmetasContrato(contrato, SituacaoSubmetaEnum.ACEITA_PELO_VRPL, transaction);
				this.verificaSeLimpaContratoEmissor(contrato, transaction);
			}
			
			ContratoBD contratoBD = contrato.converterParaBD();
			
			contratoBD.setInSituacao(SituacaoContratoEnum.EXCLUIDO.getSigla());
			transaction.attach(ContratoDAO.class).alterarContrato(contratoBD);
			transaction.attach(TermoAditivoDAO.class).excluirTermosAditivosPorContratoId(id);
			this.historicoBC.gerarEInserir(contratoBD, EventoGeradorHistoricoEnum.EXCLUSAO_CONTRATO, transaction);
			
			this.loteBC.removerLotesDoContrato(id, transaction);
		});
	}
	
	

	private boolean validacaoExclusaoParaAIOEmitida(ContratoDTO contrato) {

		AioBD aio = this.propostaBC.recuperarAio(contrato.getPropostaId());

		if (contrato.inSituacao.equals(SituacaoContratoEnum.EM_RASCUNHO.getSigla())) {
			return true;
		}

		if (aio != null) {
			if (contrato.getId().equals(aio.getContratoEmissorFk())) {
				log.debug("[EXCLUSAO DE CONTRATO NAO PERMITIDA][Contrato ID: {} igual ao Contrato Emissor ID: {}]"
						,contrato.getId(),aio.getContratoEmissorFk());
				return false;
			} else if(aio.getContratoEmissorFk() == null) {
				List<ContratoDTO> contratosConcluidos = this.loadContratosPorIdProposta(contrato.getPropostaId()).stream()
						.filter(c -> c.inSituacao.equals(SituacaoContratoEnum.CONCLUIDO.getSigla()))
						.collect(Collectors.toList());

				if (contratosConcluidos.size() == 1) {
					if (contratosConcluidos.get(0).getId().equals(contrato.getId())) {
						log.debug("[EXCLUSAO DE CONTRATO NAO PERMITIDA][Contrato ID: {} igual ao unico Contrato Concluido Restante ID: {}]"
								,contrato.getId(),contratosConcluidos.get(0).getId());
						return false;
					} else {
						log.debug("[EXCLUSAO DE CONTRATO][Situacao Inesperada][Contrato Emissor: {}][Contratos Concluidos restantes: {}]"
								+ "[Contrato ID: {} diferente do unico Contrato Concluido Restante ID: {}]",aio.getContratoEmissorFk()
								,contratosConcluidos.size(),contrato.getId(),contratosConcluidos.get(0).getId());
						throw new IncoerenciaAoExcluirContratoException();
					}
				}
			}
		}

		return true;
	}

	private boolean existeAIOEmitidoParaProposta(ContratoDTO contrato) {
		AioBD aio = this.propostaBC.recuperarAio(contrato.getPropostaId());

		if (aio == null) {
			return false;
		} else {
			return aio.isAIOEmitido();
		}
	}

	private void verificaSeLimpaContratoEmissor(ContratoDTO contrato, Handle transaction) {
		AioBD aio = this.propostaBC.recuperarAio(contrato.getPropostaId());

		if (aio != null) {
			if (aio.getContratoEmissorFk() == contrato.getId()) {
				this.aioBC.limparContratoEmissor(contrato.getPropostaId(), transaction);
			}
		}
	}

	public ContratoDTO converterBDParaDTODetalhado(ContratoBD bd) {
		ContratoDTO dto = bd.converterParaDTO();
		log.debug("[CONTRATO ID: "+bd.getId()+"] alterar contrato: recuperar informacoes do contrato: lotes");
		dto.setLotes(loteBC.recuperarLotesDoContrato(bd.getId()));

		Optional<PropostaDTO> proposta = propostaBC.recuperarPropostaPorId(bd.getPropostaId());

		dto.setPropostaIdSiconv(proposta.get().getIdSiconv());

		dto.setValorReferenteModalidade(
				dto.lotes.stream().map(LoteDTO::getSomatorioSubmetas).reduce(BigDecimal.ZERO, BigDecimal::add));

		dto.setPermissaoAlteracao(this.recuperarPermissaoAlteracaoContrato(dto));

		log.debug("[PROPOSTA ID: "+dto.getPropostaIdSiconv()+"][CONTRATO ID: "+bd.getId()+"] alterar contrato: recuperar informacoes do contrato: mensagens checklist");
		List<CheckDTO> preCheck = this.checklistBC.recuperarPreChecklistDoContrato(bd);
		List<MensagemDTO> mensagens = new ArrayList<>();

		for (CheckDTO check : preCheck) {
			mensagens.add(new MensagemDTO(check.getMensagemErro(), Severity.WARN.toString()));
		}

		dto.setMensagens(mensagens);
		
		this.setSituacaoExibicaoContratoDTO(dto);

		return dto;
	}

	public ContratoDTO recuperarContratoPorId(Long id) {

		Objects.requireNonNull(id);

		ContratoBD contratoBD = this.contratoRepository.recuperarContratoPorId(id).get();
		if (contratoBD == null) {
			throw new ContratoNaoEncontradoException();
		}

		return this.converterBDParaDTODetalhado(contratoBD);
	}

	public List<ContratoDTO> recuperarTodosContrato() {
		return this.contratoRepository.recuperarTodosContrato().stream().map(e -> this.converterBDParaDTODetalhado(e))
				.collect(Collectors.toList());

	}

	public List<ContratoDTO> loadContratosPorIdSiconvProposta(Long idProposta) {
		Objects.requireNonNull(idProposta);

		return this.contratoRepository.loadContratosPorIdSiconvProposta(idProposta).stream()
				.map(e -> this.converterBDParaDTODetalhado(e)).collect(Collectors.toList());
	}

	public List<ContratoDTO> loadContratosPorIdProposta(Long idProposta) {
		Objects.requireNonNull(idProposta);

		return this.contratoRepository.loadContratosPorIdProposta(idProposta).stream()
				.map(e -> this.converterBDParaDTODetalhado(e)).collect(Collectors.toList());
	}

	@SuppressWarnings("findsecbugs:CRLF_INJECTION_LOGS") // Nao ocorre CRLF injection no log info desse metodo
	public List<MensagemDTO> concluir(Long idContrato, Long versao) {

		List<MensagemDTO> retorno = new ArrayList<>();

		Objects.requireNonNull(idContrato);
		Objects.requireNonNull(versao);

		if (!checklistBC.tudoOk(idContrato, true, true)) {
			throw new ChecklistNaoOkException();
		}

		boolean verificarSituacaoAIO;

		if (this.inativarEmissaoAIOperiodoEleitoral && controladorPermissoes.usuarioLogadoTemPermissaoParaEmitirAIOdurantePeriodoEleitoral()) {
			log.info("Usuario logado tem permissao para emitir AIO durante periodo eleitoral, contrato.id {}", idContrato);

			verificarSituacaoAIO = true;

		} else if (this.inativarEmissaoAIOperiodoEleitoral) {
			log.info("Emissao AIO inativada devido ao periodo eleitoral, contrato.id {}", idContrato);

			verificarSituacaoAIO = false;

		} else {
			verificarSituacaoAIO = true;
		}

		// Na conclusão o formulário da Emissão do AIO não é preenchido
		EmissaoAIORequestDTO requestEmissaoAIO = new EmissaoAIORequestDTO();
		requestEmissaoAIO.setIdContrato(idContrato);

		Optional<ContratoBD> opt = contratoRepository.recuperarContratoPorId(idContrato);
		if (opt.isPresent()) {
			ContratoBD contrato = opt.get();

			if (this.contratoRepository.seExisteContratoConcluidoComONumeroDiferenteDoId(contrato)) {
				throw new JaExisteContratoConcluidoComONumeroException(contrato.getNumero());
			}

			contrato.setInSituacao(SituacaoContratoEnum.CONCLUIDO.getSigla());
			contrato.setVersao(versao);

			retorno.add(new MensagemDTO("Instrumento contratual concluído com sucesso.", Severity.SUCESS.toString()));

			jdbi.useTransaction(transaction -> {
				transaction.attach(ContratoDAO.class).alterarContrato(contrato);
				this.historicoBC.gerarEInserir(contrato, EventoGeradorHistoricoEnum.CONCLUSAO_CONTRATO, transaction);

				// Remocao temporaria da emissao automatica de AIO - Relato 3486702
				//if (verificarSituacaoAIO) {
				//	retorno.addAll(this.verificarSituacaoAIO(contrato, requestEmissaoAIO, transaction, EventoGeradorHistoricoEnum.EMISSAO_AIO_CONCLUSAO));
				//}
			});
		}

		return retorno;
	}

	public List<MensagemDTO> verificarEmissaoAIO(Long idContrato, EmissaoAIORequestDTO requestEmissaoAIO) {
		List<MensagemDTO> retorno = new ArrayList<>();

		Objects.requireNonNull(idContrato);

		Optional<ContratoBD> opt = contratoRepository.recuperarContratoPorId(idContrato);

		if (opt.isPresent()) {
			ContratoBD contrato = opt.get();

			if (!SituacaoContratoEnum.CONCLUIDO.getSigla().equals(contrato.getInSituacao())) {
				throw new ContratoNaoConcluidoException();
			}

			jdbi.useTransaction(transaction -> {
				retorno.addAll(this.verificarSituacaoAIO(contrato, requestEmissaoAIO, transaction, EventoGeradorHistoricoEnum.EMISSAO_AIO_VERIFICACAO));
			});
		}

		return retorno;
	}

	private List<MensagemDTO> verificarSituacaoAIO(ContratoBD contrato, EmissaoAIORequestDTO requestEmissaoAIO, Handle transaction,
			EventoGeradorHistoricoEnum evento) {

		List<MensagemDTO> retorno = new ArrayList<>();

		if (this.inativarEmissaoAIOperiodoEleitoral && controladorPermissoes.usuarioLogadoTemPermissaoParaEmitirAIOdurantePeriodoEleitoral()) {
			// Continua o processo normalmente
			log.info("Usuario logado tem permissao para emitir AIO durante periodo eleitoral, contrato.id {}", contrato.getId());

		} else if (this.inativarEmissaoAIOperiodoEleitoral) {
			// Interrompe o processo
			log.info("Emissao AIO inativada devido ao periodo eleitoral, contrato.id {}", contrato.getId());

			retorno.add(new MensagemDTO(ErrorInfo.EMISSAO_AIO_INATIVADA_PERIODO_ELEITORAL.getMensagem(),
					ErrorInfo.EMISSAO_AIO_INATIVADA_PERIODO_ELEITORAL.getSeverity().toString()));

			return retorno;
		}

		if (controladorPermissoes.usuarioLogadoPrecisaPreencherFormularioEmissaoAIO()) {
			this.validarEmissaoAIORequest(requestEmissaoAIO);
		}

		AioBD aio = this.propostaBC.recuperarAio(contrato.getPropostaId());

		if (aio == null) {

			List<CheckDTO> checkCompleto = checklistBC.recuperarChecklistDoContrato(contrato.getId(), false, false);
			int totalItensPendentes = 0;
			boolean item01Pendente = false;

			for (CheckDTO checkDTO : checkCompleto) {
				if (checkDTO.isPendente()) {
					totalItensPendentes++;
					if (checkDTO.getIndice() == 1L) {
						item01Pendente = true;
					}
				}
			}

			PropostaDTO proposta = this.propostaBC.recuperarPropostaPorId(contrato.getPropostaId()).get();

			if (totalItensPendentes == 0) {

				retorno.addAll(this.emitirAIO(proposta, contrato, requestEmissaoAIO, transaction, evento));

			} else if (totalItensPendentes == 1 && item01Pendente) {
				retorno.add(new MensagemDTO(
						"O item do checklist \"Recebimento dos recursos após o aceite da fase de análise do Projeto Básico\" não foi atendido. "
						+ "A Autorização de Início do Objeto (AIO) só poderá ser emitida após a liberação de recursos na Plataforma +Brasil. "
						+ "Favor aguardar a liberação de recursos para a emissão da AIO.",
						Severity.WARN.toString()));

				this.emailBC.enviarEmailPendenciaLiberacaoValores(proposta.converterParaBD(), contrato);
			} else {
				retorno.add(new MensagemDTO(
						"Não é possível realizar a emissão da AIO, pois o(s) item\\itens exibido(s) como pendente(s) na tabela de verificação "
						+ "para a emissão da AIO abaixo não foi\\foram atendido(s).", Severity.WARN.toString()));
			}

		} else if (aio.isAIOEmitido()) {
			this.atualizarSubmetasParaAptoAIniciar(contrato, transaction);
		}

		return retorno;
	}


	private void validarEmissaoAIORequest(EmissaoAIORequestDTO requestEmissaoAIO) {
		// 605665: SICONV-InstrumentosContratuais-ECU-ListarChecklist - [A3.1.3]
		// 772989: SICONV-InstrumentosContratuais-RN-FormularioEmissaoAIOPeloAdministrador

		if (requestEmissaoAIO.getIdContrato() == null ||
			requestEmissaoAIO.getJustificativa() == null ||
			requestEmissaoAIO.getJustificativa().isBlank() ||
			requestEmissaoAIO.getDataEmissaoAIO() == null ||
			requestEmissaoAIO.getDataEmissaoAIO().isBlank()
				) {

			throw new CampoObrigatorioException();
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate dataEmissaoAIO = LocalDate.parse(requestEmissaoAIO.getDataEmissaoAIO().substring(0, 10), formatter);
		LocalDate agora = LocalDate.now();

		this.compararDatasDepois(dataEmissaoAIO, agora, ErrorInfo.DATA_EMISSAO_AIO_SUPERIOR_A_ATUAL);
	}

	private List<MensagemDTO> emitirAIO(PropostaDTO proposta, ContratoBD contrato, EmissaoAIORequestDTO requestEmissaoAIO,
			Handle transaction, EventoGeradorHistoricoEnum evento) {

		List<MensagemDTO> retorno = new ArrayList<>();

		LocalDate dataEmissaoAIO;

		if (controladorPermissoes.usuarioLogadoPrecisaPreencherFormularioEmissaoAIO()) {
			// 606271: SICONV-InstrumentosContratuais-ListarChecklist-RN-CabecalhoChecklistAIO
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			dataEmissaoAIO = LocalDate.parse(requestEmissaoAIO.getDataEmissaoAIO().substring(0, 10), formatter);

		} else {
			dataEmissaoAIO = LocalDate.now();
		}

		this.aioBC.emitirAIO(contrato.getId(), proposta.getId(), dataEmissaoAIO, transaction);
		retorno.add(new MensagemDTO("A Autorização de Início do Objeto foi emitida com sucesso.", Severity.SUCESS.toString()));

		this.historicoBC.gerarEInserir(contrato, evento, transaction);

		this.atualizarSubmetasParaAptoAIniciar(contrato, transaction);

		this.atualizarSubmetasDosContratosConcluidosParaAptoAIniciar(proposta, transaction);

		try {
			// RN 616347: SICONV-InstrumentosContratuais-Geral-RN-RegistrarEmissao_e_Cancelamento_AIOHistoricoSICONV

			String mensagemSICONV = evento.getMensagemSICONV() +
					(evento == EventoGeradorHistoricoEnum.EMISSAO_AIO_CONCLUSAO ? contrato.getNumero() + "." : "");

			AtualizarHistoricoResponse respostaDoServico;
			if (evento == EventoGeradorHistoricoEnum.EMISSAO_AIO_CONCLUSAO) {
				// Emissao indireta, client padrao
				respostaDoServico = siconvRest.atualizarHistorico(proposta.getIdSiconv(), mensagemSICONV, false);

			} else {

				// Emissao direta, client especifico
				if (controladorPermissoes.usuarioLogadoPrecisaPreencherFormularioEmissaoAIO()) {
					// Relato 3362442 - Exibir mensagem no histórico do SICONV de forma mais inteligível conforme tela em anexo
					mensagemSICONV += "\n\nData de Emissão da AIO: " + dataEmissaoAIO.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".";

					String msgJustificativa = "Justificativa para emissão da AIO: " + requestEmissaoAIO.getJustificativa();

					respostaDoServico = siconvRestEmissaoDiretaAIO.atualizarHistoricoComJustificativa(proposta.getIdSiconv(), mensagemSICONV, msgJustificativa);

				} else {
					respostaDoServico = siconvRestEmissaoDiretaAIO.atualizarHistorico(proposta.getIdSiconv(), mensagemSICONV, false);
				}
			}

			if (respostaDoServico.getSucesso()) {
				retorno.add(new MensagemDTO("A Autorização de Início do Objeto foi emitida com sucesso.",
						Severity.SUCESS.toString()));

				this.emailBC.enviarEmailEmissaoAIO(proposta.converterParaBD(), contrato, evento);
			} else {
				transaction.rollback();
				retorno.add(new MensagemDTO(
 					"Serviço Siconv não conseguiu gravar evento de Emissão da Autorização de Início do Objeto.",
						Severity.ERROR.toString()));
				return retorno;
			}

		} catch (AcionarServicoSiconvException e) {
			transaction.rollback();
			throw e;
		}

		return retorno;
	}

	private void atualizarSubmetasParaAptoAIniciar(ContratoBD contrato, Handle transaction) {
		this.atualizarSubmetasContrato(this.converterBDParaDTODetalhado(contrato), SituacaoSubmetaEnum.APTA_A_INICIAR,
				transaction);
	}

	private void atualizarSubmetasDosContratosConcluidosParaAptoAIniciar(PropostaDTO proposta, Handle transaction) {

		List<ContratoDTO> contratosAtualizar = this.loadContratosPorIdProposta(proposta.getId()).stream()
				.filter(contrato -> contrato.inSituacao.equals(SituacaoContratoEnum.CONCLUIDO.getSigla()))
				.collect(Collectors.toList());

		contratosAtualizar.stream().forEach(
				contrato -> this.atualizarSubmetasContrato(contrato, SituacaoSubmetaEnum.APTA_A_INICIAR, transaction));
	}

	private void atualizarSubmetasContrato(ContratoDTO contrato, SituacaoSubmetaEnum situacao, Handle transaction) {

		contrato.getLotes().stream().forEach(lote -> {
			List<SubmetaDTO> submetas = this.submetaBC.recuperarSubmetasDoLote(lote.getId());

			submetas.stream().forEach(submeta -> {
				SubmetaBD bd = submeta.converterParaBD();
				bd.setInSituacao(situacao.getSigla());
				transaction.attach(SubmetaDAO.class).alterarSubmeta(bd);
			});
		});
	}

	public DadosChecklistDTO recuperarDadosChecklistDoContrato(Long idContrato) {

		DadosChecklistDTO dados = new DadosChecklistDTO();

		dados.setSituacaoAIO(recuperarSituacaoAIO(idContrato));

		dados.setChecklist(checklistBC.recuperarChecklistDoContrato(idContrato, false, false));

		return dados;
	}

	public String recuperarSituacaoAIO(Long idContrato) {

		ContratoDTO contrato = this.recuperarContratoPorId(idContrato);
		AioBD aio = this.propostaBC.recuperarAio(contrato.getPropostaId());

		if (aio == null) {
			String retorno = "Não Emitida.";

			return retorno;
		} else {
			String formatacao = "dd/MM/yyyy";

			String dataEmissao = DateTimeFormatter.ofPattern(formatacao).format(aio.getDataEmissaoAio());

			String retorno = String.format("Emitida pelo sistema em %s.", dataEmissao);

			return retorno;
		}

	}

	public void podeAlterarAnexoContratoConcluido(Long idAnexo, String tipoAnexo) {

		Objects.requireNonNull(idAnexo);
		Objects.requireNonNull(tipoAnexo);

		AnexoBD anexo = this.anexoBC.recuperarAnexoPorId(idAnexo).get();

		Objects.requireNonNull(anexo);

		anexo.setInTipoAnexo(tipoAnexo);

		ContratoDTO contrato = this.recuperarContratoPorId(anexo.getContratoId());

		if (SituacaoContratoEnum.CONCLUIDO.getSigla().equals(contrato.getInSituacao())) {
			List<AnexoDTO> instrumentos = anexoBC.getAnexosContratoPorTipo(anexo.getContratoId(),
					TipoAnexoEnum.INSTRUMENTO_CONTRATUAL.toString());
			List<AnexoDTO> publicacoes = anexoBC.getAnexosContratoPorTipo(anexo.getContratoId(),
					TipoAnexoEnum.PUBLICACAO_EXTRATO.toString());

			this.validarSePodeAlterarAnexoNaLista(instrumentos, anexo);
			this.validarSePodeAlterarAnexoNaLista(publicacoes, anexo);
		}
	}

	private void validarSePodeAlterarAnexoNaLista(List<AnexoDTO> anexos, AnexoBD anexo) {

		if (anexos.size() == 1) {
			AnexoDTO filhoUnico = anexos.get(0);

			if (filhoUnico.getId().equals(anexo.getId())
					&& !filhoUnico.getInTipoAnexo().equals(anexo.getInTipoAnexo())) {
				throw new ChecklistNaoOkException();
			}
		}
	}

	public void podeRemoverAnexoContratoConcluido(Long idAnexo) {

		Objects.requireNonNull(idAnexo);

		AnexoBD anexo = this.anexoBC.recuperarAnexoPorId(idAnexo).get();

		Objects.requireNonNull(anexo);

		ContratoDTO contrato = this.recuperarContratoPorId(anexo.getContratoId());

		if (SituacaoContratoEnum.CONCLUIDO.getSigla().equals(contrato.getInSituacao())) {

			if (TipoAnexoEnum.INSTRUMENTO_CONTRATUAL.toString().equals(anexo.getInTipoAnexo())
					|| TipoAnexoEnum.PUBLICACAO_EXTRATO.toString().equals(anexo.getInTipoAnexo())) {

				List<AnexoDTO> anexos = anexoBC.getAnexosContratoPorTipo(anexo.getContratoId(), anexo.getInTipoAnexo());

				if (anexos.size() == 1) {
					String labelItemPendente = "<br> - "+ChecklistBC.LABEL_ANEXO_INSTRUMENTO_CONTRATUAL;
					if ( TipoAnexoEnum.PUBLICACAO_EXTRATO.toString().equals(anexo.getInTipoAnexo())) {
						labelItemPendente = "<br> - "+ChecklistBC.LABEL_ANEXO_PUBLICACAO_EXTRATO;
					}
					throw new AlteracaoNaoPermitidaException(labelItemPendente);
				}
			}
		}
	}

	public List<MensagemDTO> verificarCancelarEmissaoAIO(Long idProposta){
		List<MensagemDTO> retorno = new ArrayList<>();

		List<ContratoDTO> contratos = this.loadContratosPorIdSiconvProposta(idProposta);

		for (ContratoDTO contrato: contratos) {
		PermissaoAlteracaoContratoEnum permissao = this.recuperarPermissaoAlteracaoContrato(contrato);

			if (PermissaoAlteracaoContratoEnum.INDISPONIVEL.equals(permissao) && retorno.isEmpty()) {
				retorno.add(new MensagemDTO("Serviço Indisponível. Erro ao tentar consultar dados do módulo de "
						+ "Acompanhamento de Obras e Serviços de Engenharia da Plataforma +Brasil. Favor tentar "
						+ "novamente mais tarde.",
						Severity.WARN.toString()));
			}

			if (PermissaoAlteracaoContratoEnum.NAO_PERMITIDA.equals(permissao) ||
					PermissaoAlteracaoContratoEnum.PARCIAL.equals(permissao)) {
				retorno = new ArrayList<>();
				retorno.add(new MensagemDTO("Não é possível cancelar a emissão da Autorização de Início de Obra, "
						+ "pois já existem informações referente a este instrumento contratual que foram inseridas "
						+ "no módulo de Acompanhamento de Obras e Serviços de Engenharia da Plataforma +Brasil.",
						Severity.WARN.toString()));
				break;
			}
		}
		return retorno;
	}

	public List<MensagemDTO> cancelarEmissaoAIOTransaction(Long idProposta) {

		Objects.requireNonNull(idProposta);
		List<MensagemDTO> retorno = new ArrayList<>();

		jdbi.useTransaction(transaction -> {
			retorno.addAll(this.cancelarEmissaoAIO(idProposta, transaction));
		});

		return retorno;
	}

	public List<MensagemDTO> cancelarEmissaoAIO(Long idProposta, Handle transaction){

		List<MensagemDTO> retorno = new ArrayList<>();
		Optional<PropostaDTO> opt = this.propostaBC.recuperarPropostaPorIdSiconv(idProposta);
		if (opt.isEmpty()) {
			throw new PropostaNaoEncontradaException();
		}
		PropostaDTO proposta = opt.get();
		AioBD aio = this.propostaBC.recuperarAio(proposta.getId());

		log.debug("[CANCELAMENTO DE AIO][Proposta IdSiconv: {}][Proposta Id: {}][AIO Id: {}]"
				,idProposta,proposta.getId(),aio.getId());

		this.regredirSubmetasDosContratosConcluidosParaAceitaPeloVRPL(idProposta, transaction);
		this.regredirContratosConcluidosParaRascunho(idProposta, transaction);
		this.apagarRegistroAIO(aio, transaction);

		try {AtualizarHistoricoResponse respostaDoServico = cancelarEmissaoAIOImpl.atualizarHistorico(idProposta,
				EventoGeradorHistoricoEnum.CANCELAMENTO_EMISSAO_AIO.getMensagemSICONV(), false);

			if (Boolean.TRUE.equals(respostaDoServico.getSucesso())) {
				retorno.add(new MensagemDTO("Cancelamento da Autorização de Início de Obra concluída com sucesso.",
						Severity.SUCESS.toString()));
			} else {
				transaction.rollback();
				retorno.add(new MensagemDTO("Erro ao tentar registrar evento de cancelamento de AIO no histórico SICONV.",
						Severity.ERROR.toString()));
				return retorno;
			}

		} catch (AcionarServicoSiconvException e) {
			transaction.rollback();
			throw e;
		}

		return retorno;
	}

	private void apagarRegistroAIO(AioBD aio, Handle transaction) {

		Objects.requireNonNull(aio);
		log.debug("[CANCELAMENTO DE AIO][AIO Id: {}][PropostaFk: {}] - Removendo registro da AIO"
				,aio.getId(),aio.getPropostaFk());
		this.aioBC.apagarRegistroAIO(aio.getPropostaFk(), transaction);

	}

	private void regredirContratosConcluidosParaRascunho(Long idProposta, Handle transaction) {

		List<ContratoDTO> contratosAtualizar = this.loadContratosPorIdSiconvProposta(idProposta).stream()
				.filter(contrato -> contrato.inSituacao.equals(SituacaoContratoEnum.CONCLUIDO.getSigla()))
				.collect(Collectors.toList());

		contratosAtualizar.stream().forEach(contrato -> {
			log.debug("[CANCELAMENTO DE AIO][Proposta IdSiconv: {}][Contrato ID: {}] - "
					+ "Regredindo situacao dos contratos Concluidos para Rascunho",idProposta,contrato.getId());
			contrato.setInSituacao(SituacaoContratoEnum.EM_RASCUNHO.getSigla());
			ContratoBD bd = contrato.converterParaBD();
			transaction.attach(ContratoDAO.class).alterarContrato(bd);
			this.historicoBC.gerarEInserir(bd, EventoGeradorHistoricoEnum.CANCELAMENTO_EMISSAO_AIO, transaction);
		});

	}

	private void regredirSubmetasDosContratosConcluidosParaAceitaPeloVRPL(Long idProposta, Handle transaction) {

		List<ContratoDTO> contratosAtualizar = this.loadContratosPorIdSiconvProposta(idProposta).stream()
				.filter(contrato -> contrato.inSituacao.equals(SituacaoContratoEnum.CONCLUIDO.getSigla()))
				.collect(Collectors.toList());

		contratosAtualizar.stream().forEach(contrato -> {
			log.debug("[CANCELAMENTO DE AIO][Proposta IdSiconv: {}][Contrato ID: {}] - "
					+ "Regredindo situacao das submetas de Apta a Iniciar para Aceita",idProposta,contrato.getId());
			this.atualizarSubmetasContrato(contrato, SituacaoSubmetaEnum.ACEITA_PELO_VRPL, transaction);
		});

	}

	public PermissaoAlteracaoContratoEnum recuperarPermissaoAlteracaoContrato(ContratoDTO contrato) {
		PermissaoAlteracaoContratoEnum retorno = PermissaoAlteracaoContratoEnum.TOTAL;

		// 617674:
		// SICONV-InstrumentosContratuais-ManterInstrumentosContratuais-RN-ValidacaoAlteracao
		if (existeAIOEmitidoParaProposta(contrato) && contrato.estaConcluido()) {

			try {

				MedicaoGRPCClient client = new MedicaoGRPCClient(enderecoMedicao, portaMedicao, false);

				try {

					log.debug("**** [PROPOSTA ID: "+ contrato.getPropostaIdSiconv()+"][CONTRATO ID: "+ contrato.getId()+"] recuperando permissão de alteração no medição ****");

					br.gov.serpro.siconv.med.grpc.dto.ContratoDTO permissoes = client.consultarContrato(contrato.getId())
							.get();

					log.debug("**** [PROPOSTA ID: "+ contrato.getPropostaIdSiconv()+"][CONTRATO ID: "+ contrato.getId()+"] fim da chamada do serviço permissão de alteração no medição ****");

					client.shutdown();

					log.debug("**** [PROPOSTA ID: "+ contrato.getPropostaIdSiconv()+"][CONTRATO ID: "+ contrato.getId()+"] fechando cliente do serviço do medição (shutdown) ****");

					client = null;
					if (permissoes != null) {
						if (permissoes.isMedicaoIniciada() && permissoes.isConfiguracaoIniciada()) {
							retorno = PermissaoAlteracaoContratoEnum.NAO_PERMITIDA;
						} else if (permissoes.isConfiguracaoIniciada()) {
							retorno = PermissaoAlteracaoContratoEnum.PARCIAL;
						}
					}
				} catch (StatusRuntimeException e) {
					if (e.getStatus().getCode().equals(Status.NOT_FOUND.getCode())) {
						retorno = PermissaoAlteracaoContratoEnum.TOTAL;
					} else {
					    log.error("Erro na chamada ao servico GRPC do Medicao", e);
						retorno = PermissaoAlteracaoContratoEnum.INDISPONIVEL;
					}
				}  catch (Exception e) {
				    log.error("Erro na chamada ao servico GRPC do Medicao", e);
					retorno = PermissaoAlteracaoContratoEnum.INDISPONIVEL;
				} finally {
					if (client != null) {
						client.shutdown();
						client = null;
						log.debug("**** [PROPOSTA ID: "+ contrato.getPropostaIdSiconv()+"][CONTRATO ID: "+ contrato.getId()+"] fechando cliente do serviço do medição (shutdown, finally) ****");
					}
				}
			} catch (Exception e) {
			    log.error("Erro na chamada ao servico GRPC do Medicao", e);
				retorno = PermissaoAlteracaoContratoEnum.INDISPONIVEL;
			}
		}

		return retorno;

	}

	public ContratoDTO inserirContratoAnexos(@Valid ContratoDTO contrato) {
		Objects.requireNonNull(contrato);

		if (this.contratoRepository.seExisteContratoConcluidoComONumero(contrato)) {
			throw new JaExisteContratoConcluidoComONumeroException(contrato.getNumero());
		}

		this.validarDatasContrato(contrato);
		
		this.anexoBC.validarListaContratoAnexos(contrato.getContratoAnexos());

		return jdbi.withHandle(transaction -> {
			ContratoBD bd = transaction.attach(ContratoDAO.class).inserirContrato(contrato.converterParaBD());

			this.historicoBC.gerarEInserir(bd, EventoGeradorHistoricoEnum.SALVAR_RASCUNHO, transaction);

			this.loteBC.associarLotesAoContrato(contrato.getLotes(), bd.getId(), transaction);

			ContratoDTO res = this.converterBDParaDTODetalhado(bd);

			this.anexoBC.anexarArquivosDeContrato(res.getId(), contrato.getContratoAnexos(), transaction);

			return res;
		});
	}
	
    public List<TermoAditivoDTO> recuperarTermoAditivoPorContratoId(Long contratoId) {
		return this.termoAditivoBC.recuperarTermoAditivoPorContratoId(contratoId);
    	
    }
    
    public TermoAditivoDTO concluirNovoTermoAditivoAnexos(@Valid TermoAditivoDTO termoAditivo) {
    	
    	ContratoBD contratoBD = jdbi.onDemand(ContratoDAO.class).recuperarContratoPorId(termoAditivo.getContratoId()).get();
    	
    	if (contratoBD == null) {
    		throw new ContratoNaoEncontradoException();
    	}
    	
    	if (this.contratoPossuiTermoAditivoEmRascunho(termoAditivo.getContratoId())) {
    		throw new TermoAditivoUnicoEmRascunhoException();
    	}
    	
    	this.validarDatasTermoAditivoConcluir(termoAditivo, contratoBD.converterParaDTO());
    	
    	this.validarAnexosTermoAditivoNaConclusao(termoAditivo.getContratoAnexos());
    	
    	this.anexoBC.validarListaContratoAnexos(termoAditivo.getContratoAnexos());
    	
    	return jdbi.withHandle(transaction -> {
        	
    		termoAditivo.setInSituacao(SituacaoTermoAditivoEnum.CONCLUIDO.getSigla());
    		TermoAditivoBD termoAditivoSalvoBD = this.termoAditivoBC.inserir(termoAditivo, transaction);
        	
        	this.anexoBC.anexarArquivosDeTermoAditivo(termoAditivoSalvoBD.getId(), termoAditivo.getContratoAnexos(), transaction);
        	
        	this.realizarAlteracoesConclusaoTermoAditivoNoContrato(contratoBD, termoAditivoSalvoBD, transaction);
        	
        	return termoAditivoSalvoBD.converterParaDTO();
    	});
    	
    }
    
    public void validarAnexosTermoAditivoNaConclusao(List<ContratoAnexoDTO> contratoAnexos) {
        
    	if (contratoAnexos.stream().noneMatch(anexo -> TipoAnexoEnum.TERMO_ADITIVO.toString().equals(anexo.getTipoAnexo()))) {
    		throw new ConclusaoTermoAditivoSemAnexoTermoAditivoException();
    	}
    	
    	if (contratoAnexos.stream().noneMatch(anexo -> TipoAnexoEnum.PUBLICACAO_TERMO_ADITIVO.toString().equals(anexo.getTipoAnexo()))) {
    		throw new ConclusaoTermoAditivoSemAnexoPublicacaoTermoAditivoException();
    	}
    }
    
    public TermoAditivoDTO concluirTermoAditivoExistente(@Valid TermoAditivoDTO termoAditivo) {
    	
    	ContratoBD contratoBD = jdbi.onDemand(ContratoDAO.class).recuperarContratoPorId(termoAditivo.getContratoId()).get();
    	
    	if (contratoBD == null) {
    		throw new ContratoNaoEncontradoException();
    	}
    	
    	TermoAditivoBD termoBD = jdbi.onDemand(TermoAditivoDAO.class).recuperarTermoAditivoPorId(termoAditivo.getId()).get();
    	
    	if (termoBD == null) {
    		throw new TermoAditivoNaoEncontradoException();
    	}

    	this.validarDatasTermoAditivoConcluir(termoAditivo, contratoBD.converterParaDTO());
    	
    	List<AnexoBD> anexosTermoAditivo = jdbi.onDemand(AnexoDAO.class).recuperarAnexoPorIdTermoAditivo(termoAditivo.getId());
    	
    	this.validarAnexosBDTermoAditivoNaConclusao(anexosTermoAditivo);
    	
    	boolean termoAditivoJaEstavaConcluidoEDeixouDeSerDeVigencia = 
    			SituacaoTermoAditivoEnum.CONCLUIDO.getSigla().equals(termoBD.getInSituacao()) &&
    			Boolean.TRUE.equals(termoBD.getInAlteracaoVigencia()) &&
    			Boolean.FALSE.equals(termoAditivo.getInAlteracaoVigencia()); 
    	
    	if (termoAditivoJaEstavaConcluidoEDeixouDeSerDeVigencia) {
    		contratoBD.setFimVigencia(this.recuperarFimVigenciaAnteriorDoContratoDoTermoAditivo(termoAditivo, contratoBD));
    	}
    	
    	return jdbi.withHandle(transaction -> {
    		
    		termoAditivo.setInSituacao(SituacaoTermoAditivoEnum.CONCLUIDO.getSigla());
        	TermoAditivoBD termoAditivoSalvoBD = this.termoAditivoBC.alterar(termoAditivo, transaction);
        	
        	this.realizarAlteracoesConclusaoTermoAditivoNoContrato(contratoBD, termoAditivoSalvoBD, transaction);
        	
        	return termoAditivoSalvoBD.converterParaDTO();
    	});
    }
    
    public void validarAnexosBDTermoAditivoNaConclusao(List<AnexoBD> contratoAnexos) {
        
    	if (contratoAnexos.stream().noneMatch(anexo -> TipoAnexoEnum.TERMO_ADITIVO.toString().equals(anexo.getInTipoAnexo()))) {
    		throw new ConclusaoTermoAditivoSemAnexoTermoAditivoException();
    	}
    	
    	if (contratoAnexos.stream().noneMatch(anexo -> TipoAnexoEnum.PUBLICACAO_TERMO_ADITIVO.toString().equals(anexo.getInTipoAnexo()))) {
    		throw new ConclusaoTermoAditivoSemAnexoPublicacaoTermoAditivoException();
    	}
    }
    
    public void realizarAlteracoesConclusaoTermoAditivoNoContrato(ContratoBD contrato, TermoAditivoBD termoAditivo, Handle transaction) {
    	
    	if (Boolean.TRUE.equals(termoAditivo.getInAlteracaoVigencia())) {
    		contrato.setFimVigencia(termoAditivo.getDtNovaDataFimVigencia());
    	}
    	
    	ContratoBD contratoAlteradoBD = transaction.attach(ContratoDAO.class).alterarContrato(contrato);
    	
    	this.historicoBC.gerarEInserir(contratoAlteradoBD, termoAditivo,
    			EventoGeradorHistoricoEnum.TERMO_ADITIVO_CONCLUSAO, transaction);

    }
    
    public TermoAditivoDTO inserirTermoAditivo(TermoAditivoDTO termoAditivo) {
    	
    	ContratoBD contratoBD = jdbi.onDemand(ContratoDAO.class).recuperarContratoPorId(termoAditivo.getContratoId()).get();
    	
    	if (contratoBD == null) {
    		throw new ContratoNaoEncontradoException();
    	}
    	
    	if (this.contratoPossuiTermoAditivoEmRascunho(termoAditivo.getContratoId())) {
    		throw new TermoAditivoUnicoEmRascunhoException();
    	}
    	
    	this.validarDatasTermoAditivoSalvarRascunho(termoAditivo, contratoBD.converterParaDTO());
    	
    	return jdbi.withHandle(transaction -> {
        	TermoAditivoBD termoAditivoSalvoBD = this.termoAditivoBC.inserir(termoAditivo, transaction);
        	
        	this.historicoBC.gerarEInserir(contratoBD, termoAditivoSalvoBD,
        			EventoGeradorHistoricoEnum.TERMO_ADITIVO_SALVAR_RASCUNHO, transaction);
        	
        	return termoAditivoSalvoBD.converterParaDTO();
    	});
    	
    }
    
    public TermoAditivoDTO inserirTermoAditivoAnexos(@Valid TermoAditivoDTO termoAditivo) {
    	
    	ContratoBD contratoBD = jdbi.onDemand(ContratoDAO.class).recuperarContratoPorId(termoAditivo.getContratoId()).get();
    	
    	if (contratoBD == null) {
    		throw new ContratoNaoEncontradoException();
    	}
    	
    	if (this.contratoPossuiTermoAditivoEmRascunho(termoAditivo.getContratoId())) {
    		throw new TermoAditivoUnicoEmRascunhoException();
    	}
    	
    	this.validarDatasTermoAditivoSalvarRascunho(termoAditivo, contratoBD.converterParaDTO());
    	
    	this.anexoBC.validarListaContratoAnexos(termoAditivo.getContratoAnexos());
    	
    	return jdbi.withHandle(transaction -> {
        	TermoAditivoBD termoAditivoSalvoBD = this.termoAditivoBC.inserir(termoAditivo, transaction);
        	
        	this.anexoBC.anexarArquivosDeTermoAditivo(termoAditivoSalvoBD.getId(), termoAditivo.getContratoAnexos(), transaction);
        	
        	this.historicoBC.gerarEInserir(contratoBD, termoAditivoSalvoBD,
        			EventoGeradorHistoricoEnum.TERMO_ADITIVO_SALVAR_RASCUNHO, transaction);
        	
        	return termoAditivoSalvoBD.converterParaDTO();
    	});
    	
    }

    public TermoAditivoDTO alterarTermoAditivo(TermoAditivoDTO termoAditivo) {
    	
    	ContratoBD contratoBD = jdbi.onDemand(ContratoDAO.class).recuperarContratoPorId(termoAditivo.getContratoId()).get();
    	
    	if (contratoBD == null) {
    		throw new ContratoNaoEncontradoException();
    	}
    	
    	TermoAditivoBD termoBD = jdbi.onDemand(TermoAditivoDAO.class).recuperarTermoAditivoPorId(termoAditivo.getId()).get();
    	
    	if (termoBD == null) {
    		throw new TermoAditivoNaoEncontradoException();
    	}

    	if (!SituacaoTermoAditivoEnum.EM_RASCUNHO.getSigla().equals(termoBD.getInSituacao()) &&  
    			this.contratoPossuiTermoAditivoEmRascunho(termoAditivo.getContratoId())) {
    		throw new TermoAditivoUnicoEmRascunhoException();
    	}
    	
    	this.validarDatasTermoAditivoSalvarRascunho(termoAditivo, contratoBD.converterParaDTO());
    	
    	return jdbi.withHandle(transaction -> {
        	TermoAditivoBD termoAditivoSalvoBD = this.termoAditivoBC.alterar(termoAditivo, transaction);
        	
        	this.historicoBC.gerarEInserir(contratoBD, termoAditivoSalvoBD,
        			EventoGeradorHistoricoEnum.TERMO_ADITIVO_SALVAR_RASCUNHO, transaction);
        	
        	return termoAditivoSalvoBD.converterParaDTO();
    	});
    	
    }

    public void excluirTermoAditivo(Long termoAditivoId) {
    	
    	jdbi.useHandle(transaction -> {
        	
    		TermoAditivoDTO termoAditivoAntesExcluir = this.termoAditivoBC.recuperarTermoAditivoPorId(termoAditivoId);
    		
    		TermoAditivoBD termoAditivoExcluidoBD = this.termoAditivoBC.excluir(termoAditivoId, transaction);
        	
        	ContratoDTO contratoDaBaseDTO = recuperarContratoPorId(termoAditivoExcluidoBD.getContratoId());

    		ContratoBD contratoDaBaseBD = contratoDaBaseDTO.converterParaBD();
        	
        	if (Boolean.TRUE.equals(termoAditivoAntesExcluir.getInAlteracaoVigencia())) {
        		contratoDaBaseBD.setFimVigencia(this.recuperarFimVigenciaAnteriorDoContratoDoTermoAditivo(termoAditivoAntesExcluir, contratoDaBaseBD));
    		}
        	
        	ContratoBD contratoAlteradoBD = transaction.attach(ContratoDAO.class).alterarContrato(contratoDaBaseBD);
        	
        	SituacaoHistoricoContratoEnum situacaoHistorico = this.recuperarSituacaoExibicaoContrato(contratoDaBaseDTO);
        	
        	this.historicoBC.gerarEInserir(contratoAlteradoBD, termoAditivoExcluidoBD,
        			EventoGeradorHistoricoEnum.TERMO_ADITIVO_EXCLUSAO, situacaoHistorico, transaction);
    	});
    }
    
    public LocalDate recuperarFimVigenciaAnteriorDoContratoDoTermoAditivo(TermoAditivoDTO termoAditivo, ContratoBD contrato) {
    	
    	LocalDate retorno = contrato.getDtFimVigenciaOriginal();
    	
    	List<HistoricoBD> historicoConclusaoDeOutrosTermosAditivos = jdbi.onDemand(HistoricoDAO.class)
    			.recuperarHistoricoDeConclusaoDeOutrosTermosAditivosDoContrato(contrato.getId(), termoAditivo.getId());
    	
    	if (historicoConclusaoDeOutrosTermosAditivos != null && !historicoConclusaoDeOutrosTermosAditivos.isEmpty()) {
    		
    		for (HistoricoBD historico : historicoConclusaoDeOutrosTermosAditivos) {
            	TermoAditivoBD ultimoTermoAditivoConcluido = jdbi.onDemand(TermoAditivoDAO.class)
            			.recuperarTermoAditivoPorId(historico.getTermoAditivoId()).get();
            	
        		if (ultimoTermoAditivoConcluido != null && ultimoTermoAditivoConcluido.getInAlteracaoVigencia()) {
        			retorno = ultimoTermoAditivoConcluido.getDtNovaDataFimVigencia();	
        			break;
        		}
    		}
    	}
    	
    	return retorno;
    }
    
    public boolean contratoPossuiTermoAditivoEmRascunho(Long contratoId) {
    	List<TermoAditivoDTO> termosAditivosContrato = this.recuperarTermoAditivoPorContratoId(contratoId);
    	
    	return termosAditivosContrato.stream().anyMatch(termo -> 
			SituacaoTermoAditivoEnum.EM_RASCUNHO.getSigla().equals(termo.getInSituacao())); 
    }
    
    public boolean contratoPossuiTermoAditivoConcluido(Long contratoId) {
    	List<TermoAditivoDTO> termosAditivosContrato = this.recuperarTermoAditivoPorContratoId(contratoId);
    	
    	return termosAditivosContrato.stream().anyMatch(termo -> 
			SituacaoTermoAditivoEnum.CONCLUIDO.getSigla().equals(termo.getInSituacao())); 
    }
    
    public void setSituacaoExibicaoContratoDTO(ContratoDTO contrato) {

    	contrato.setInSituacaoExibicao(contrato.getInSituacao());
		contrato.setInSituacaoExibicaoDescricao(contrato.getInSituacaoDescricao());
		
		boolean contratoPossuiTermoAditivoEmRascunho = this.contratoPossuiTermoAditivoEmRascunho(contrato.getId()); 
		boolean contratoPossuiTermoAditivoConcluido = this.contratoPossuiTermoAditivoConcluido(contrato.getId()); 
		
    	
		if (SituacaoContratoEnum.EM_RASCUNHO.getSigla().equals(contrato.getInSituacao())  ) {
	    	if (contratoPossuiTermoAditivoEmRascunho) {
	    		contrato.setInSituacaoExibicao(SituacaoHistoricoContratoEnum.RASCUNHO_ADITIVACAO_EM_RASCUNHO.getSigla());
	    		contrato.setInSituacaoExibicaoDescricao(SituacaoHistoricoContratoEnum.RASCUNHO_ADITIVACAO_EM_RASCUNHO.getDescricao());
	    	} else if (contratoPossuiTermoAditivoConcluido) {
	    		contrato.setInSituacaoExibicao(SituacaoHistoricoContratoEnum.RASCUNHO_ADITIVACAO_CONCLUIDA.getSigla());
	    		contrato.setInSituacaoExibicaoDescricao(SituacaoHistoricoContratoEnum.RASCUNHO_ADITIVACAO_CONCLUIDA.getDescricao());
	    	}
		} else if (SituacaoContratoEnum.CONCLUIDO.getSigla().equals(contrato.getInSituacao())  ) {
	    	if (contratoPossuiTermoAditivoEmRascunho) {
	    		contrato.setInSituacaoExibicao(SituacaoHistoricoContratoEnum.CONCLUIDO_ADITIVACAO_EM_RASCUNHO.getSigla());
	    		contrato.setInSituacaoExibicaoDescricao(SituacaoHistoricoContratoEnum.CONCLUIDO_ADITIVACAO_EM_RASCUNHO.getDescricao());
	    	} else if (contratoPossuiTermoAditivoConcluido) {
	    		contrato.setInSituacaoExibicao(SituacaoHistoricoContratoEnum.CONCLUIDO_ADITIVACAO_CONCLUIDA.getSigla());
	    		contrato.setInSituacaoExibicaoDescricao(SituacaoHistoricoContratoEnum.CONCLUIDO_ADITIVACAO_CONCLUIDA.getDescricao());
	    	}
		}
		
    }
    
    
    public SituacaoHistoricoContratoEnum recuperarSituacaoExibicaoContrato(ContratoDTO contrato) {

    	SituacaoHistoricoContratoEnum situacao = SituacaoHistoricoContratoEnum.fromSigla(contrato.getInSituacao());
    	
		boolean contratoPossuiTermoAditivoEmRascunho = this.contratoPossuiTermoAditivoEmRascunho(contrato.getId()); 
		boolean contratoPossuiTermoAditivoConcluido = this.contratoPossuiTermoAditivoConcluido(contrato.getId()); 

    	if (SituacaoContratoEnum.EM_RASCUNHO.getSigla().equals(contrato.getInSituacao())  ) {
	    	if (contratoPossuiTermoAditivoEmRascunho) {
	    		situacao = SituacaoHistoricoContratoEnum.RASCUNHO_ADITIVACAO_EM_RASCUNHO;
	    	} else if (contratoPossuiTermoAditivoConcluido) {
	    		situacao = SituacaoHistoricoContratoEnum.RASCUNHO_ADITIVACAO_CONCLUIDA;
	    	}
		} else if (SituacaoContratoEnum.CONCLUIDO.getSigla().equals(contrato.getInSituacao())  ) {
	    	if (contratoPossuiTermoAditivoEmRascunho) {
	    		situacao = SituacaoHistoricoContratoEnum.CONCLUIDO_ADITIVACAO_EM_RASCUNHO;
	    	} else if (contratoPossuiTermoAditivoConcluido) {
	    		situacao = SituacaoHistoricoContratoEnum.CONCLUIDO_ADITIVACAO_CONCLUIDA;
	    	}
		}
		
    	return situacao;
    }


}

















