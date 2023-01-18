package br.gov.economia.maisbrasil.contratos.bc;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.bc.exception.FornecedorNaoEncontradoException;
import br.gov.economia.maisbrasil.contratos.domain.bd.FornecedorBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.FornecedorDTO;
import br.gov.economia.maisbrasil.contratos.repository.FornecedorRepository;
import br.gov.economia.maisbrasil.contratos.security.SecurityUtils;
import br.gov.economia.maisbrasil.contratos.security.SiconvUser;

@Controller
public class FornecedorBC {
    
	private final FornecedorRepository fornecedorRepository;
	
	@Autowired
	public FornecedorBC (FornecedorRepository fornecedorRepository) {
		this.fornecedorRepository = fornecedorRepository;
	}
    
    public FornecedorDTO inserir(FornecedorDTO fornecedor) {
    	Objects.requireNonNull(fornecedor);
    	FornecedorBD bd = this.fornecedorRepository.inserirFornecedor(fornecedor.converterParaBD());
    	return bd.converterParaDTO();

    }
    
    public FornecedorDTO alterar(FornecedorDTO fornecedor) {
    	
    	Objects.requireNonNull(fornecedor);

        // valida se o Fornecedor existe
        recuperarFornecedorPorId(fornecedor.getId());

        FornecedorBD bd = this.fornecedorRepository.alterarFornecedor(fornecedor.converterParaBD());
        return bd.converterParaDTO();

    }
    
    
    public void excluir(Long id) {
    	Objects.requireNonNull(id);
        // levanta excecao caso Fornecedor nao exista
        this.recuperarFornecedorPorId(id);
        this.fornecedorRepository.excluirFornecedor(id);
    }
    
    
    
    public FornecedorDTO recuperarFornecedorPorId (Long id) {
    	
    	Objects.requireNonNull(id);
    	
    	FornecedorBD fornecedorBD = this.fornecedorRepository.recuperarFornecedorPorId(id).get();
        if (fornecedorBD == null) {
            throw new FornecedorNaoEncontradoException();
        }

        FornecedorDTO fornecedorDTO = fornecedorBD.converterParaDTO();
        return fornecedorDTO;
    }
    
    public List<FornecedorDTO> recuperarTodosFornecedor() {
		return this.fornecedorRepository.recuperarTodosFornecedor().stream().map(e -> e.converterParaDTO())
				.collect(Collectors.toList());
    }
    
    public List<FornecedorDTO> recuperarFornecedoresPorProposta() {
    	
    	Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();
    	
		return this.fornecedorRepository.recuperarFornecedoresPorProposta(usuarioLogado.getIdProposta()).stream().map(e -> e.converterParaDTO())
				.collect(Collectors.toList());
    }

}
