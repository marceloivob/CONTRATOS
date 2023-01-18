package br.gov.economia.maisbrasil.contratos.bc;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.bc.exception.LicitacaoNaoEncontradaPropostaException;
import br.gov.economia.maisbrasil.contratos.bc.exception.NaoExisteVRPLAceitoException;
import br.gov.economia.maisbrasil.contratos.bc.exception.PropostaNaoEncontradaException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ServicoSiconvIndisponivelException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ServicoVrplIndisponivelException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.domain.EventoGeradorHistoricoEnum;
import br.gov.economia.maisbrasil.contratos.domain.bd.AioBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.PropostaBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.CheckDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.MensagemDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.PropostaDTO;
import br.gov.economia.maisbrasil.contratos.integracao.client.SiconvClientGRPCProducer;
import br.gov.economia.maisbrasil.contratos.integracao.client.VrplClientGRPCProducer;
import br.gov.economia.maisbrasil.contratos.integracao.restclient.AcionarServicoSiconvException;
import br.gov.economia.maisbrasil.contratos.integracao.restclient.AtualizarHistoricoResponse;
import br.gov.economia.maisbrasil.contratos.integracao.restclient.SiconvRestImpl;
import br.gov.economia.maisbrasil.contratos.repository.AioRepository;
import br.gov.economia.maisbrasil.contratos.repository.PropostaRepository;
import br.gov.economia.maisbrasil.contratos.web.rest.PropostaResource;
import br.gov.planejamento.siconv.grpc.SiconvGRPCClient;
import br.gov.serpro.vrpl.grpc.client.VRPLGrpcClient;
import io.grpc.Status;



@Controller
public class PropostaBC {

	private final PropostaRepository propostaRepository;

	private final AioRepository aioRepository;

	private final VrplBC vrplBC;

	private final FornecedorBC fornecedorBC;
	
	private final ChecklistBC checklistBC;
	
	private final EmailBC emailBC;
	
	private final Logger log = LoggerFactory.getLogger(PropostaResource.class);
	
	@Autowired
	private SiconvRestImpl siconvRest;

	@Autowired
	public PropostaBC(PropostaRepository propostaRepository, AioRepository aioRepository, VrplBC vrplBC,
			FornecedorBC fornecedorBC, ChecklistBC checklistBC, EmailBC emailBC) {
		this.aioRepository = aioRepository;
		this.propostaRepository = propostaRepository;
		this.vrplBC = vrplBC;
		this.fornecedorBC = fornecedorBC;
		this.checklistBC = checklistBC;
		this.emailBC = emailBC;
	}

	public PropostaDTO inserir(PropostaDTO proposta) {
		Objects.requireNonNull(proposta);
		PropostaBD bd = this.propostaRepository.inserirProposta(proposta.converterParaBD());
		return bd.converterParaDTO();

	}

	public PropostaDTO alterar(PropostaDTO proposta) {

		Objects.requireNonNull(proposta);

		// valida se o Proposta existe
		recuperarPropostaPorId(proposta.getId());

		
		
		PropostaBD bd = this.propostaRepository.alterarProposta(proposta.converterParaBD());
		return bd.converterParaDTO();

	}

	public void excluir(Long id) {
		Objects.requireNonNull(id);
		// levanta excecao caso Proposta nao exista
		this.recuperarPropostaPorId(id);
		this.propostaRepository.excluirProposta(id);
	}

	public Optional<PropostaDTO> recuperarPropostaPorId(Long id) {

		Objects.requireNonNull(id);

		Optional<PropostaBD> propostaBD = this.propostaRepository.recuperarPropostaPorId(id);

		if (propostaBD.isPresent()) {
			PropostaDTO propostaDTO = propostaBD.get().converterParaDTO();

			return Optional.of(propostaDTO);
		} else {
			throw new PropostaNaoEncontradaException();
		}
	}

	public Optional<PropostaDTO> recuperarPropostaPorIdSiconv(Long idSiconv) {

		Objects.requireNonNull(idSiconv);

		// chama a migraÃƒÂ§ÃƒÂ£o
		List<MensagemDTO> mensagens = this.vrplBC.importarDadosDoVRPL(idSiconv);

		Optional<PropostaBD> propostaBD = this.propostaRepository.recuperarPropostaPorIdSiconv(idSiconv);

		if (propostaBD.isPresent()) {
			PropostaDTO propostaDTO = propostaBD.get().converterParaDTO();
			propostaDTO.setFornecedores(this.fornecedorBC.recuperarFornecedoresPorProposta());
			propostaDTO.setMensagens(mensagens);
			return Optional.of(propostaDTO);
		} else {
			throw new PropostaNaoEncontradaException();
		}
	}

	public AioBD recuperarAio(Long propostaId) {
		AioBD aioBD = this.aioRepository.recuperarAio(propostaId);

		return aioBD;
	}
	
	public boolean validarChecklistProposta(Long idProposta) {
		List<CheckDTO> check = this.checklistBC.recuperarChecklistDaProposta(idProposta);
		return this.checklistBC.resolveChecklist(check);
	}
	
	public boolean informarEmissaoAIOBatch(Long idProposta) {
		
		Objects.requireNonNull(idProposta);
		
		PropostaDTO proposta = this.recuperarPropostaPorId(idProposta).get();
		
		boolean retorno = false;
		
		try {
			AtualizarHistoricoResponse ahr = siconvRest.atualizarHistorico(
					proposta.getIdSiconv(), 
					EventoGeradorHistoricoEnum.EMISSAO_AIO_ROTINA_AUTOMATICA.getMensagemSICONV(), true);
			
			retorno = ahr.getSucesso();
			
		} catch (AcionarServicoSiconvException e) {
			// TODO Tratar exceÃƒÂ§ÃƒÂ£o
		}

		if (retorno) {
			this.emailBC.enviarEmailEmissaoAIO(proposta.converterParaBD(), null, 
					EventoGeradorHistoricoEnum.EMISSAO_AIO_ROTINA_AUTOMATICA);
		}
		
		return retorno;
	}
	
	public Boolean verificaInicioContratos(Long idProposta){	
		try {
			return this.validaGrpcVrpl(idProposta);			
		}catch(PropostaNaoEncontradaException e) {
			throw validaGrpcSiconv(idProposta).get();
		}
	}
	
	private Supplier<BusinessException> validaGrpcSiconv(Long idProposta){
		
		Boolean retornoPropostaVrpl = false;
		
		// Verifica se a proposta estÃƒÂ¡ sendo trabalhada pelo VRPL
		try {
			SiconvGRPCClient siconvGRPCClient = SiconvClientGRPCProducer.create();			
			retornoPropostaVrpl = siconvGRPCClient.isVRPLResponsavelAceiteProcessoExecucao(idProposta).getRetorno();				
			SiconvClientGRPCProducer.shutdown();
			
		} catch (Exception e) {
			Status status = Status.fromThrowable(e);
			
			if(status.getCode() == Status.INVALID_ARGUMENT.getCode() || 
			   status.getCode() == Status.NOT_FOUND.getCode()) {
				return () -> new PropostaNaoEncontradaException();
			}else {
			    log.error("Erro na chamada ao servico GRPC do Siconv", e);
				return () -> new ServicoSiconvIndisponivelException();				
			}
		}	
		  
		if(retornoPropostaVrpl) {
			return () -> new NaoExisteVRPLAceitoException(idProposta);
		} else {
			return () -> new LicitacaoNaoEncontradaPropostaException(idProposta);				
		}
		
	}
	
	private Boolean validaGrpcVrpl(Long idProposta){

		Boolean retornoVrplAceita = false;
		
		// Verifica se existe VRPL Aceita para a LicitaÃƒÂ§ÃƒÂ£o vinculada ÃƒÂ   Proposta. Caso retorne Null sinalizar que a proposta nÃƒÂ£o foi localizada.
		try {
			VRPLGrpcClient vrplGRPCClient = VrplClientGRPCProducer.create();			
			retornoVrplAceita = vrplGRPCClient.existeVRPLAceita(idProposta);
			VrplClientGRPCProducer.shutdown();

        } catch (Exception e) {
        	    Status status = Status.fromThrowable(e);

				if(status.getCode() == Status.INVALID_ARGUMENT.getCode() || 
				   status.getCode() == Status.NOT_FOUND.getCode()) {			
					throw new PropostaNaoEncontradaException();					
				}else {
				    log.error("Erro na chamada ao servico GRPC do VRPL", e);
					throw new ServicoVrplIndisponivelException();
				}
        }		 
		
 		if(!retornoVrplAceita) {   			
			throw new NaoExisteVRPLAceitoException(idProposta);			
		}				
		
		return true;
		
	}

}
