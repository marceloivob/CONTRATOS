package br.gov.economia.maisbrasil.contratos.bc;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.bc.exception.HistoricoNaoEncontradoException;
import br.gov.economia.maisbrasil.contratos.domain.EventoGeradorHistoricoEnum;
import br.gov.economia.maisbrasil.contratos.domain.SituacaoContratoEnum;
import br.gov.economia.maisbrasil.contratos.domain.SituacaoHistoricoContratoEnum;
import br.gov.economia.maisbrasil.contratos.domain.bd.ContratoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.HistoricoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.TermoAditivoBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.HistoricoContratoDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.HistoricoDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.RegistroHistoricoContratoDTO;
import br.gov.economia.maisbrasil.contratos.repository.HistoricoRepository;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.ContratoDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.HistoricoDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.TermoAditivoDAO;
import br.gov.economia.maisbrasil.contratos.security.SecurityUtils;
import br.gov.economia.maisbrasil.contratos.security.SiconvUser;

@Controller
public class HistoricoBC {
    
	private final HistoricoRepository historicoRepository;
	
	private final Logger log = LoggerFactory.getLogger(ContratoBC.class);
	
	@Autowired
	private Jdbi jdbi;
	
	@Autowired
	public HistoricoBC (HistoricoRepository historicoRepository) {
		this.historicoRepository = historicoRepository;
	}
    
    public HistoricoDTO inserir(HistoricoDTO historico, Handle transaction) {
    	Objects.requireNonNull(historico);
    	HistoricoBD bd = transaction.attach(HistoricoDAO.class).inserirHistorico(historico.converterParaBD());
    	return bd.converterParaDTO();
    }

    
    public HistoricoDTO gerarEInserir(ContratoBD contrato, EventoGeradorHistoricoEnum evento, Handle transaction) {
    	Objects.requireNonNull(contrato);
    	
		Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();

		HistoricoBD historico = new HistoricoBD();
		historico.setContratoId(contrato.getId());
		historico.setSituacaoContrato(contrato.getInSituacao());
		historico.setEventoGerador(evento.getSigla());
		historico.setNomeResponsavel(usuarioLogado.getUsername());
		historico.setCpfResponsavel(usuarioLogado.getCpf());

    	HistoricoBD bd = transaction.attach(HistoricoDAO.class).inserirHistorico(historico);
    	
    	return bd.converterParaDTO();
    }
    
    public HistoricoDTO gerarEInserir(ContratoBD contrato,  EventoGeradorHistoricoEnum evento, 
    		SituacaoHistoricoContratoEnum situacao, Handle transaction) {
    	Objects.requireNonNull(contrato);
    	
		Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();

		HistoricoBD historico = new HistoricoBD();
		historico.setContratoId(contrato.getId());
		historico.setSituacaoContrato(situacao.getSigla());
		historico.setEventoGerador(evento.getSigla());
		historico.setNomeResponsavel(usuarioLogado.getUsername());
		historico.setCpfResponsavel(usuarioLogado.getCpf());

    	HistoricoBD bd = transaction.attach(HistoricoDAO.class).inserirHistorico(historico);
    	
    	return bd.converterParaDTO();
    }
    
    public HistoricoDTO gerarEInserir(ContratoBD contrato, TermoAditivoBD termoAditivo, 
    		EventoGeradorHistoricoEnum evento, SituacaoHistoricoContratoEnum situacao, Handle transaction) {
    	Objects.requireNonNull(termoAditivo);
    	
		Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();

		HistoricoBD historico = new HistoricoBD();
		historico.setContratoId(contrato.getId());
		historico.setSituacaoContrato(situacao.getSigla());
		historico.setEventoGerador(evento.getSigla());
		historico.setNomeResponsavel(usuarioLogado.getUsername());
		historico.setCpfResponsavel(usuarioLogado.getCpf());
		historico.setTermoAditivoId(termoAditivo.getId());

    	HistoricoBD bd = transaction.attach(HistoricoDAO.class).inserirHistorico(historico);
    	
    	return bd.converterParaDTO();
    }
    
    public HistoricoDTO gerarEInserir(ContratoBD contrato, TermoAditivoBD termoAditivo,
            EventoGeradorHistoricoEnum evento, Handle transaction) {
    	Objects.requireNonNull(evento);
    	Objects.requireNonNull(contrato);
    	Objects.requireNonNull(termoAditivo);
        
        Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
        SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();
        
        SituacaoHistoricoContratoEnum situacao = null;
        
        if (EventoGeradorHistoricoEnum.TERMO_ADITIVO_SALVAR_RASCUNHO.equals(evento)) {
            if (SituacaoContratoEnum.EM_RASCUNHO.getSigla().equals(contrato.getInSituacao())) {
                situacao = SituacaoHistoricoContratoEnum.RASCUNHO_ADITIVACAO_EM_RASCUNHO;
            } else if (SituacaoContratoEnum.CONCLUIDO.getSigla().equals(contrato.getInSituacao())){
                situacao = SituacaoHistoricoContratoEnum.CONCLUIDO_ADITIVACAO_EM_RASCUNHO;
            }
        } else if (EventoGeradorHistoricoEnum.TERMO_ADITIVO_CONCLUSAO.equals(evento)) {
            if (SituacaoContratoEnum.EM_RASCUNHO.getSigla().equals(contrato.getInSituacao())) {
                situacao = SituacaoHistoricoContratoEnum.RASCUNHO_ADITIVACAO_CONCLUIDA;
            } else if (SituacaoContratoEnum.CONCLUIDO.getSigla().equals(contrato.getInSituacao())){
                situacao = SituacaoHistoricoContratoEnum.CONCLUIDO_ADITIVACAO_CONCLUIDA;
            }
        }
        
        if (situacao == null) {
        	
        	String msgErro = "Não foi possível determinar a situação do histórico do contrato.";
        	
        	log.error(msgErro+" Evento: "+evento+" Contrato: id "+ contrato.getId()+" numero "+	contrato.getNumero()+" situacao "+ contrato.getInSituacao()+ 
    				" Termo Aditivo: id "+termoAditivo.getId()+ " numero " +termoAditivo.getNumero()+" situacao "+termoAditivo.getInSituacao());
        	
        	throw new IllegalArgumentException(msgErro);
        }

        HistoricoBD historico = new HistoricoBD();
        historico.setContratoId(contrato.getId());
        historico.setSituacaoContrato(situacao.getSigla());
        historico.setEventoGerador(evento.getSigla());
        historico.setNomeResponsavel(usuarioLogado.getUsername());
        historico.setCpfResponsavel(usuarioLogado.getCpf());
        historico.setTermoAditivoId(termoAditivo.getId());

        HistoricoBD bd = transaction.attach(HistoricoDAO.class).inserirHistorico(historico);
        
        return bd.converterParaDTO();
    }

    
    public void excluirHistoricoDoContrato(Long idContrato, Handle transaction) {
    	Objects.requireNonNull(idContrato);
    	transaction.attach(HistoricoDAO.class).excluirHistoricoDoContrato(idContrato);
    }
    
    public HistoricoDTO recuperarHistoricoPorId (Long id) {
    	
    	Objects.requireNonNull(id);
    	
    	HistoricoBD historicoBD = this.historicoRepository.recuperarHistoricoPorId(id).get();
        if (historicoBD == null) {
            throw new HistoricoNaoEncontradoException();
        }

        HistoricoDTO historicoDTO = historicoBD.converterParaDTO();
        return historicoDTO;
    }
    
    public List<HistoricoDTO> recuperarTodosHistorico() {
		return this.historicoRepository.recuperarTodosHistorico().stream().map(e -> e.converterParaDTO())
				.collect(Collectors.toList());
    	
    }
    
    public List<HistoricoContratoDTO> recuperarHistoricoContratosDaProposta(Long idSiconvProposta) {
    	
   		List<ContratoBD> contratos = jdbi.onDemand(ContratoDAO.class).loadContratosPorIdSiconvPropostaOrdenadosPorDataInclusao(idSiconvProposta);
    	List<HistoricoContratoDTO> retorno = this.recuperarHistoricoDaListaDeContratos(contratos);
    	
    	return retorno;
    }

    public List<HistoricoContratoDTO> recuperarHistoricoContratosExcluidosDaProposta(Long idSiconvProposta) {
    	
    	List<ContratoBD> contratos = jdbi.onDemand(ContratoDAO.class).loadContratosExcluidosPorIdSiconvPropostaOrdenadosPorDataInclusao(idSiconvProposta);
    	List<HistoricoContratoDTO> retorno = this.recuperarHistoricoDaListaDeContratos(contratos); 
    	
    	return retorno;
    }
    
    public List<HistoricoContratoDTO> recuperarHistoricoDaListaDeContratos(List<ContratoBD> contratos) {
    	
    	
		Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();
    	
    	List<HistoricoContratoDTO> retorno = new ArrayList<>();
    	
		for (ContratoBD contratoBD : contratos) {
			
			HistoricoContratoDTO historicoDeUmContrato = HistoricoContratoDTO.from(contratoBD);
			
			List<HistoricoBD> listaHistoricoBD = jdbi.onDemand(HistoricoDAO.class).recuperarHistoricoDoContrato(contratoBD.getId());
			
			for (HistoricoBD historicoBD : listaHistoricoBD) {
				RegistroHistoricoContratoDTO registro = RegistroHistoricoContratoDTO.from(historicoBD, usuarioLogado.isApenasGuest());

				registro.setNumeroContratoTA(this.recuperarNumeroContratoTA(contratoBD, historicoBD));

				historicoDeUmContrato.getListaRegistros().add(registro);
			}
			
			retorno.add(historicoDeUmContrato);
		}
    	
    	return retorno;
    }
    
    public String recuperarNumeroContratoTA(ContratoBD contratoBD, HistoricoBD historicoBD) {
    	String retorno = contratoBD.getNumero();
    	
    	if (historicoBD.getTermoAditivoId() != null) {
    		TermoAditivoBD termoAditivo = jdbi.onDemand(TermoAditivoDAO.class).recuperarTermoAditivoPorId(historicoBD.getTermoAditivoId()).get();
    		
    		if (termoAditivo != null) {
    			retorno += " - aditivo " + termoAditivo.getNumero();
    		}
    	}
    	
    	return retorno;
    }
}
