package br.gov.economia.maisbrasil.contratos.bc;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.bc.exception.SubmetaDocComplementarNaoEncontradoException;
import br.gov.economia.maisbrasil.contratos.domain.bd.SubmetaDocComplementarBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.SubmetaDocComplementarDTO;
import br.gov.economia.maisbrasil.contratos.repository.SubmetaDocComplementarRepository;

@Controller
public class SubmetaDocComplementarBC {
    
	private final SubmetaDocComplementarRepository submetaDocComplementarRepository;
	
	@Autowired
	public SubmetaDocComplementarBC (SubmetaDocComplementarRepository submetaDocComplementarRepository) {
		this.submetaDocComplementarRepository = submetaDocComplementarRepository;
	}
    
    public SubmetaDocComplementarDTO inserir(SubmetaDocComplementarDTO submetaDocComplementar) {
    	Objects.requireNonNull(submetaDocComplementar);
    	SubmetaDocComplementarBD bd = this.submetaDocComplementarRepository.inserirSubmetaDocComplementar(submetaDocComplementar.converterParaBD());
    	return bd.converterParaDTO();

    }
    
    public SubmetaDocComplementarDTO alterar(SubmetaDocComplementarDTO submetaDocComplementar) {
    	
    	Objects.requireNonNull(submetaDocComplementar);

        // valida se o SubmetaDocComplementar existe
        recuperarSubmetaDocComplementarPorId(submetaDocComplementar.getId());

        SubmetaDocComplementarBD bd = this.submetaDocComplementarRepository.alterarSubmetaDocComplementar(submetaDocComplementar.converterParaBD());
        return bd.converterParaDTO();

    }
    
    
    public void excluir(Long id) {
    	Objects.requireNonNull(id);
        // levanta excecao caso SubmetaDocComplementar nao exista
        this.recuperarSubmetaDocComplementarPorId(id);
        this.submetaDocComplementarRepository.excluirSubmetaDocComplementar(id);
    }
    
    
    
    public SubmetaDocComplementarDTO recuperarSubmetaDocComplementarPorId (Long id) {
    	
    	Objects.requireNonNull(id);
    	
    	SubmetaDocComplementarBD submetaDocComplementarBD = this.submetaDocComplementarRepository.recuperarSubmetaDocComplementarPorId(id).get();
        if (submetaDocComplementarBD == null) {
            throw new SubmetaDocComplementarNaoEncontradoException();
        }

        SubmetaDocComplementarDTO submetaDocComplementarDTO = submetaDocComplementarBD.converterParaDTO();
        return submetaDocComplementarDTO;
    }
    
    public List<SubmetaDocComplementarDTO> recuperarTodosSubmetaDocComplementar() {
		return this.submetaDocComplementarRepository.recuperarTodosSubmetaDocComplementar().stream().map(e -> e.converterParaDTO())
				.collect(Collectors.toList());
    	
    }
}
