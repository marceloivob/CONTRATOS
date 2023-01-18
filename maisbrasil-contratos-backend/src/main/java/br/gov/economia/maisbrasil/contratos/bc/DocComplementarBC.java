package br.gov.economia.maisbrasil.contratos.bc;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.bc.exception.DocComplementarNaoEncontradoException;
import br.gov.economia.maisbrasil.contratos.domain.bd.DocComplementarBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.DocComplementarDTO;
import br.gov.economia.maisbrasil.contratos.repository.DocComplementarRepository;

@Controller
public class DocComplementarBC {
    
	private final DocComplementarRepository docComplementarRepository;
	
	@Autowired
	public DocComplementarBC (DocComplementarRepository docComplementarRepository) {
		this.docComplementarRepository = docComplementarRepository;
	}
    
    public DocComplementarDTO inserir(DocComplementarDTO docComplementar) {
    	Objects.requireNonNull(docComplementar);
    	DocComplementarBD bd = this.docComplementarRepository.inserirDocComplementar(docComplementar.converterParaBD());
    	return bd.converterParaDTO();

    }
    
    public DocComplementarDTO alterar(DocComplementarDTO docComplementar) {
    	
    	Objects.requireNonNull(docComplementar);

        // valida se o DocComplementar existe
        recuperarDocComplementarPorId(docComplementar.getId());

        DocComplementarBD bd = this.docComplementarRepository.alterarDocComplementar(docComplementar.converterParaBD());
        return bd.converterParaDTO();

    }
    
    
    public void excluir(Long id) {
    	Objects.requireNonNull(id);
        // levanta excecao caso DocComplementar nao exista
        this.recuperarDocComplementarPorId(id);
        this.docComplementarRepository.excluirDocComplementar(id);
    }
    
    
    
    public DocComplementarDTO recuperarDocComplementarPorId (Long id) {
    	
    	Objects.requireNonNull(id);
    	
    	DocComplementarBD docComplementarBD = this.docComplementarRepository.recuperarDocComplementarPorId(id).get();
        if (docComplementarBD == null) {
            throw new DocComplementarNaoEncontradoException();
        }

        DocComplementarDTO docComplementarDTO = docComplementarBD.converterParaDTO();
        return docComplementarDTO;
    }
    
    public List<DocComplementarDTO> recuperarTodosDocComplementar() {
		return this.docComplementarRepository.recuperarTodosDocComplementar().stream().map(e -> e.converterParaDTO())
				.collect(Collectors.toList());
    	
    }
}
