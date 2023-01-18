package br.gov.economia.maisbrasil.contratos.bc;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.bc.exception.MetaNaoEncontradoException;
import br.gov.economia.maisbrasil.contratos.domain.bd.MetaBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.MetaDTO;
import br.gov.economia.maisbrasil.contratos.repository.MetaRepository;

@Controller
public class MetaBC {

	private final MetaRepository metaRepository;

	@Autowired
	public MetaBC (MetaRepository metaRepository) {
		this.metaRepository = metaRepository;
	}

    public MetaDTO inserir(MetaDTO meta) {
    	Objects.requireNonNull(meta);
    	MetaBD bd = this.metaRepository.inserirMeta(meta.converterParaBD());
    	return bd.converterParaDTO();

    }

    public MetaDTO alterar(MetaDTO meta) {

    	Objects.requireNonNull(meta);

        // valida se o Meta existe
        recuperarMetaPorId(meta.getId());

        MetaBD bd = this.metaRepository.alterarMeta(meta.converterParaBD());
        return bd.converterParaDTO();

    }


    public void excluir(Long id) {
    	Objects.requireNonNull(id);
        // levanta excecao caso Meta nao exista
        this.recuperarMetaPorId(id);
        this.metaRepository.excluirMeta(id);
    }



    public MetaDTO recuperarMetaPorId (Long id) {

    	Objects.requireNonNull(id);

    	Optional<MetaBD> metaBDOptional = this.metaRepository.recuperarMetaPorId(id);

    	if (metaBDOptional.isEmpty()) {
    	    throw new MetaNaoEncontradoException();
    	}

    	MetaBD metaBD = metaBDOptional.get();
        if (metaBD == null) {
            throw new MetaNaoEncontradoException();
        }

        return metaBD.converterParaDTO();
    }

    public List<MetaDTO> recuperarTodosMeta() {
		return this.metaRepository.recuperarTodosMeta().stream().map(MetaBD::converterParaDTO)
				.collect(Collectors.toList());

    }

	public Map<Long, MetaDTO> recuperarMetaPorListaIds(List<Long> listaIdsMetas) {

    	Objects.requireNonNull(listaIdsMetas);

    	if (listaIdsMetas.isEmpty()) {
    		return new TreeMap<>();
    	}

    	List<MetaBD> listaMetaBD = this.metaRepository.recuperarMetaPorListaIds(listaIdsMetas);

        if (listaMetaBD == null) {
            throw new MetaNaoEncontradoException();
        }

        return listaMetaBD.stream().map(MetaBD::converterParaDTO)
        		.collect(Collectors.toMap(MetaDTO::getId, p -> p));
	}
}
