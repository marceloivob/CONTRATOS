package br.gov.economia.maisbrasil.contratos.bc;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.bc.exception.PoNaoEncontradoException;
import br.gov.economia.maisbrasil.contratos.domain.bd.PoBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.PoDTO;
import br.gov.economia.maisbrasil.contratos.repository.PoRepository;

@Controller
public class PoBC {

	private final PoRepository poRepository;

	@Autowired
	public PoBC (PoRepository poRepository) {
		this.poRepository = poRepository;
	}

    public PoDTO inserir(PoDTO po) {
    	Objects.requireNonNull(po);
    	PoBD bd = this.poRepository.inserirPo(po.converterParaBD());
    	return bd.converterParaDTO();

    }

    public PoDTO alterar(PoDTO po) {
    	Objects.requireNonNull(po);

        // valida se o Submeta existe
        recuperarPoPorId(po.getId());

        PoBD bd = this.poRepository.alterarPo(po.converterParaBD());
        return bd.converterParaDTO();

    }


    public void excluir(Long id) {
    	Objects.requireNonNull(id);
        // levanta excecao caso Submeta nao exista
        this.recuperarPoPorId(id);
        this.poRepository.excluirPo(id);
    }



    public PoDTO recuperarPoPorId (Long id) {
    	Objects.requireNonNull(id);

    	Optional<PoBD> poBDOptional = this.poRepository.recuperarPoPorId(id);

    	if (poBDOptional.isEmpty()) {
    		throw new PoNaoEncontradoException();
    	}

    	PoBD poBD = poBDOptional.get();

        if (poBD == null) {
            throw new PoNaoEncontradoException();
        }

        return poBD.converterParaDTO();
    }

    public List<PoDTO> recuperarTodosPo() {
		return this.poRepository.recuperarTodosPo().stream().map(PoBD::converterParaDTO)
				.collect(Collectors.toList());
    }

    public PoDTO recuperarPoPorSubmeta (Long idSubmeta) {
    	Objects.requireNonNull(idSubmeta);

    	Optional<PoBD> poBDOptional = this.poRepository.recuperarPoPorSubmeta(idSubmeta);

    	if (poBDOptional.isEmpty()) {
    		throw new PoNaoEncontradoException();
    	}

    	PoBD poBD = poBDOptional.get();

        if (poBD == null) {
            throw new PoNaoEncontradoException();
        }

        return poBD.converterParaDTO();
    }

    public Map<Long, PoDTO> recuperarPoPorListaSubmetas (List<Long> listaIdsSubmetas) {

    	Objects.requireNonNull(listaIdsSubmetas);

    	if (listaIdsSubmetas.isEmpty()) {
    		return new TreeMap<>();
    	}

    	List<PoBD> listaPoBD = this.poRepository.recuperarPoPorListaSubmetas(listaIdsSubmetas);
        if (listaPoBD == null) {
            throw new PoNaoEncontradoException();
        }

        return listaPoBD.stream().map(PoBD::converterParaDTO)
        		.collect(Collectors.toMap(PoDTO::getSubmetaId, p -> p));
    }

}
