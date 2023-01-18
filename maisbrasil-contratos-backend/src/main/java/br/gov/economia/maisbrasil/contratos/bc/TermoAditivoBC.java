package br.gov.economia.maisbrasil.contratos.bc;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jdbi.v3.core.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.bc.exception.JaExisteTermoAditivoComONumeroException;
import br.gov.economia.maisbrasil.contratos.bc.exception.TermoAditivoNaoEncontradoException;
import br.gov.economia.maisbrasil.contratos.domain.SituacaoTermoAditivoEnum;
import br.gov.economia.maisbrasil.contratos.domain.bd.TermoAditivoBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.TermoAditivoDTO;
import br.gov.economia.maisbrasil.contratos.repository.TermoAditivoRepository;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.TermoAditivoDAO;

@Controller
public class TermoAditivoBC {
    
	private final TermoAditivoRepository termoAditivoRepository;
	
	@Autowired
	public TermoAditivoBC (TermoAditivoRepository termoAditivoRepository) {
		this.termoAditivoRepository = termoAditivoRepository;
	}
    
    public TermoAditivoBD inserir(TermoAditivoDTO termoAditivo, Handle transaction) {
    	Objects.requireNonNull(termoAditivo);

		if (this.termoAditivoRepository.seExisteTermoAditivoComONumero(termoAditivo)) {
			throw new JaExisteTermoAditivoComONumeroException(termoAditivo.getNumero());
		}
    	
    	TermoAditivoBD bd = transaction.attach(TermoAditivoDAO.class).inserirTermoAditivo(termoAditivo.converterParaBD()); 
    	return bd;
    }

    
    public TermoAditivoBD alterar(TermoAditivoDTO termoAditivo, Handle transaction) {
    	Objects.requireNonNull(termoAditivo);
    	
    	TermoAditivoDTO termoAditivoDaBase = recuperarTermoAditivoPorId(termoAditivo.getId());
    	
    	if (termoAditivoDaBase == null) {
    		throw new TermoAditivoNaoEncontradoException();
    	}
    	
    	TermoAditivoBD termoAditivoAlteradoBD = termoAditivo.converterParaBD();
    	
    	if (this.termoAditivoRepository.seExisteTermoAditivoComONumeroDiferenteDoId(termoAditivoAlteradoBD)) {
			throw new JaExisteTermoAditivoComONumeroException(termoAditivo.getNumero());
		}

    	termoAditivoAlteradoBD = transaction.attach(TermoAditivoDAO.class).alterarTermoAditivo(termoAditivoAlteradoBD);
    	
    	return termoAditivoAlteradoBD;
    }

    
    public TermoAditivoDTO alterar(TermoAditivoDTO termoAditivo) {
    	
    	Objects.requireNonNull(termoAditivo);

        // valida se o TermoAditivo existe
        recuperarTermoAditivoPorId(termoAditivo.getId());

        TermoAditivoBD bd = this.termoAditivoRepository.alterarTermoAditivo(termoAditivo.converterParaBD());
        return bd.converterParaDTO();

    }

    
    public TermoAditivoBD excluir(Long id, Handle transaction) {
    	
    	Objects.requireNonNull(id);

        // valida se o TermoAditivo existe
        
    	TermoAditivoBD bd = transaction.attach(TermoAditivoDAO.class).recuperarTermoAditivoPorId(id).get();
    	
    	if (bd != null) {
    		bd.setInSituacao(SituacaoTermoAditivoEnum.EXCLUIDO.getSigla());
    		
    		bd = transaction.attach(TermoAditivoDAO.class).alterarTermoAditivo(bd);	
    	}
    	
    	return bd;
    }
    
    public TermoAditivoDTO recuperarTermoAditivoPorId (Long id) {
    	
    	Objects.requireNonNull(id);
    	
    	TermoAditivoBD termoAditivoBD = this.termoAditivoRepository.recuperarTermoAditivoPorId(id).get();
        if (termoAditivoBD == null) {
            throw new TermoAditivoNaoEncontradoException();
        }

        TermoAditivoDTO termoAditivoDTO = termoAditivoBD.converterParaDTO();
        return termoAditivoDTO;
    }
    
    
    public List<TermoAditivoDTO> recuperarTermoAditivoPorContratoId(Long contratoId) {
		return this.termoAditivoRepository.recuperarTermoAditivoPorContratoId(contratoId).stream().map(e -> e.converterParaDTO())
				.collect(Collectors.toList());
    	
    }

    public List<TermoAditivoDTO> recuperarTodosTermoAditivo() {
		return this.termoAditivoRepository.recuperarTodosTermoAditivo().stream().map(e -> e.converterParaDTO())
				.collect(Collectors.toList());
    	
    }
    
    
}
