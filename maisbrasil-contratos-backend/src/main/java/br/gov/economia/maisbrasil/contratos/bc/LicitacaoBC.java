package br.gov.economia.maisbrasil.contratos.bc;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.bc.exception.LicitacaoNaoEncontradoException;
import br.gov.economia.maisbrasil.contratos.domain.bd.LicitacaoBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.LicitacaoDTO;
import br.gov.economia.maisbrasil.contratos.repository.LicitacaoRepository;
import br.gov.economia.maisbrasil.contratos.security.SecurityUtils;
import br.gov.economia.maisbrasil.contratos.security.SiconvUser;

@Controller
public class LicitacaoBC {
    
	private final LicitacaoRepository licitacaoRepository;
	
	@Autowired
	public LicitacaoBC (LicitacaoRepository licitacaoRepository) {
		this.licitacaoRepository = licitacaoRepository;
	}
    
    public LicitacaoDTO inserir(LicitacaoDTO licitacao) {
    	Objects.requireNonNull(licitacao);
    	LicitacaoBD bd = this.licitacaoRepository.inserirLicitacao(licitacao.converterParaBD());
    	return bd.converterParaDTO();

    }
    
    public LicitacaoDTO alterar(LicitacaoDTO licitacao) {
    	
    	Objects.requireNonNull(licitacao);

        // valida se o Licitacao existe
        recuperarLicitacaoPorId(licitacao.getId());

        LicitacaoBD bd = this.licitacaoRepository.alterarLicitacao(licitacao.converterParaBD());
        return bd.converterParaDTO();

    }
    
    
    public void excluir(Long id) {
    	Objects.requireNonNull(id);
        // levanta excecao caso Licitacao nao exista
        this.recuperarLicitacaoPorId(id);
        this.licitacaoRepository.excluirLicitacao(id);
    }
    
    
    
    public LicitacaoDTO recuperarLicitacaoPorId (Long id) {
    	
    	Objects.requireNonNull(id);
    	
    	LicitacaoBD licitacaoBD = this.licitacaoRepository.recuperarLicitacaoPorId(id).get();
        if (licitacaoBD == null) {
            throw new LicitacaoNaoEncontradoException();
        }

        LicitacaoDTO licitacaoDTO = licitacaoBD.converterParaDTO();
        return licitacaoDTO;
    }
    
    public List<LicitacaoDTO> recuperarTodosLicitacao() {
		return this.licitacaoRepository.recuperarTodosLicitacao().stream().map(e -> e.converterParaDTO())
				.collect(Collectors.toList());
    }

    
    public List<LicitacaoDTO> recuperarLicitacoesPorPropostaIdSiconv() {
    	
    	Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();

		return this.licitacaoRepository.recuperarLicitacoesPorPropostaIdSiconv(usuarioLogado.getIdProposta()).stream().map(e -> e.converterParaDTO())
				.collect(Collectors.toList());
    }

    
    
    public List<LicitacaoDTO> recuperarLicitacoesPorIdFornecedor(Long idFornecedor) {

    	Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();

		return this.licitacaoRepository.recuperarLicitacoesDaPropostaPorIdFornecedor(idFornecedor, usuarioLogado.getIdProposta()).stream().map(e -> e.converterParaDTO())
				.collect(Collectors.toList());
    }

}
