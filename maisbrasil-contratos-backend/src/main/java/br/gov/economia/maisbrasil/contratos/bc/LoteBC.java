package br.gov.economia.maisbrasil.contratos.bc;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jdbi.v3.core.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.bc.exception.LoteNaoEncontradoException;
import br.gov.economia.maisbrasil.contratos.domain.bd.LoteBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.LoteDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.SubmetaDTO;
import br.gov.economia.maisbrasil.contratos.repository.LoteRepository;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.LoteDAO;
import br.gov.economia.maisbrasil.contratos.security.SecurityUtils;
import br.gov.economia.maisbrasil.contratos.security.SiconvUser;

@Controller
public class LoteBC {

	private final LoteRepository loteRepository;

	private final SubmetaBC submetaBC;

	@Autowired
	public LoteBC (LoteRepository loteRepository, SubmetaBC submetaBC) {
		this.loteRepository = loteRepository;
		this.submetaBC = submetaBC;
	}

    public LoteDTO inserir(LoteDTO lote) {
    	Objects.requireNonNull(lote);
    	LoteBD bd = this.loteRepository.inserirLote(lote.converterParaBD());
    	return this.converterBDParaDTODetalhado(bd);

    }

    public LoteDTO alterar(LoteDTO lote) {

    	Objects.requireNonNull(lote);

        // valida se o Lote existe
        recuperarLotePorId(lote.getId());

        LoteBD bd = this.loteRepository.alterarLote(lote.converterParaBD());
        return this.converterBDParaDTODetalhado(bd);

    }


    public void excluir(Long id) {
    	Objects.requireNonNull(id);
        // levanta excecao caso Lote nao exista
        this.recuperarLotePorId(id);
        this.loteRepository.excluirLote(id);
    }


    public LoteDTO converterBDParaDTODetalhado(LoteBD bd) {
    	LoteDTO dto = bd.converterParaDTO();

    	List<SubmetaDTO> listaSubmetas = submetaBC.recuperarSubmetasDoLote(bd.getId());

    	dto.setSubmetas(listaSubmetas);
    	dto.setSomatorioSubmetas(submetaBC.recuperarSomatorioListaSubmetas(listaSubmetas));

    	return dto;
    }


    public LoteDTO recuperarLotePorId (Long id) {

    	Objects.requireNonNull(id);

    	Optional<LoteBD> loteBDOptional = this.loteRepository.recuperarLotePorId(id);

    	if (loteBDOptional.isEmpty()) {
    	    throw new LoteNaoEncontradoException();
    	}

    	LoteBD loteBD = loteBDOptional.get();
        if (loteBD == null) {
            throw new LoteNaoEncontradoException();
        }

        return this.converterBDParaDTODetalhado(loteBD);
    }


    public List<LoteDTO> recuperarLotesDoContrato(Long idContrato) {

    	return this.loteRepository
    			.recuperarLotesDoContrato(idContrato)
    			.stream().map(this::converterBDParaDTODetalhado)
				.collect(Collectors.toList());
    }

    public List<LoteDTO> recuperarLotesPorIdFornecedorEIdLicitacao(Long idFornecedor, Long idLicitacao) {

    	return this.loteRepository
    			.recuperarLotesPorIdFornecedorEIdLicitacao(idFornecedor, idLicitacao)
    			.stream().map(this::converterBDParaDTODetalhado)
				.collect(Collectors.toList());
    }


    public List<LoteDTO> recuperarTodosLotesDaProposta() {

    	Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();


    	return this.loteRepository.recuperarTodosLotesDaProposta(usuarioLogado.getIdProposta())
    			.stream().map(this::converterBDParaDTODetalhado)
				.collect(Collectors.toList());

    }

    public void associarLotesAoContrato(List<LoteDTO> lotes, Long idContrato, Handle transaction) {

    	List<Long> ids = lotes.stream().map(LoteDTO::getId).collect(Collectors.toList());
    	if(ids != null) {
	    	for (Long idLote : ids) {
	    		Optional<LoteBD> loteOptional = transaction.attach(LoteDAO.class).recuperarLotePorId(idLote);

	    		if (loteOptional.isEmpty()) {
	    			throw new LoteNaoEncontradoException();
	    		}

	    		LoteBD lote = loteOptional.get();

				lote.setContratoId(idContrato);

				transaction.attach(LoteDAO.class).alterarLote(lote);
			}
    	}
    }

    public void removerLotesDoContrato(Long idContrato, Handle transaction) {
    	List<LoteBD> lotes = transaction.attach(LoteDAO.class).recuperarLotesDoContrato(idContrato);
    	if(lotes != null) {
    		for (LoteBD loteBD : lotes) {
				loteBD.setContratoId(null);
				transaction.attach(LoteDAO.class).alterarLote(loteBD);
			}
    	}
    }

}
