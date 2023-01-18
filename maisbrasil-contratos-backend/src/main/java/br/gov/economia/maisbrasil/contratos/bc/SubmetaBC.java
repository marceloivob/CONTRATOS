package br.gov.economia.maisbrasil.contratos.bc;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.bc.exception.SubmetaNaoEncontradoException;
import br.gov.economia.maisbrasil.contratos.domain.bd.SubmetaBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.MetaDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.PoDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.SubmetaDTO;
import br.gov.economia.maisbrasil.contratos.repository.SubmetaRepository;

@Controller
public class SubmetaBC {

	private final SubmetaRepository submetaRepository;
	private final PoBC poBC;
	private final MetaBC metaBC;

	@Autowired
	public SubmetaBC (SubmetaRepository submetaRepository, PoBC poBC, MetaBC metaBC) {
		this.submetaRepository = submetaRepository;
		this.poBC = poBC;
		this.metaBC = metaBC;
	}

    public SubmetaDTO inserir(SubmetaDTO submeta) {
    	Objects.requireNonNull(submeta);
    	SubmetaBD bd = this.submetaRepository.inserirSubmeta(submeta.converterParaBD());
    	return this.converterBDParaDTODetalhado(bd);

    }

    public SubmetaDTO alterar(SubmetaDTO submeta) {

    	Objects.requireNonNull(submeta);

        // valida se o Submeta existe
        recuperarSubmetaPorId(submeta.getId());

        SubmetaBD bd = this.submetaRepository.alterarSubmeta(submeta.converterParaBD());
        return this.converterBDParaDTODetalhado(bd);

    }


    public void excluir(Long id) {
    	Objects.requireNonNull(id);
        // levanta excecao caso Submeta nao exista
        this.recuperarSubmetaPorId(id);
        this.submetaRepository.excluirSubmeta(id);
    }

    public SubmetaDTO converterBDParaDTODetalhado(SubmetaBD bd) {
    	SubmetaDTO dto = bd.converterParaDTO();

    	dto.setPo(poBC.recuperarPoPorSubmeta(bd.getId()));
    	dto.setMeta(metaBC.recuperarMetaPorId(bd.getMetaId()));

    	return dto;
    }

    public SubmetaDTO recuperarSubmetaPorId (Long id) {

    	Objects.requireNonNull(id);

    	Optional<SubmetaBD> submetaBDOptional = this.submetaRepository.recuperarSubmetaPorId(id);

    	if (submetaBDOptional.isEmpty()) {
    	    throw new SubmetaNaoEncontradoException();
    	}

    	SubmetaBD submetaBD = submetaBDOptional.get();

        if (submetaBD == null) {
            throw new SubmetaNaoEncontradoException();
        }

        return this.converterBDParaDTODetalhado(submetaBD);
    }

    public List<SubmetaDTO> recuperarTodosSubmeta() {
		return this.submetaRepository.recuperarTodosSubmeta().stream().map(this::converterBDParaDTODetalhado)
				.collect(Collectors.toList());

    }

    public List<SubmetaDTO> recuperarSubmetasDoLote(Long idLote) {
    	List<SubmetaBD> listaSubmetaBD = this.submetaRepository.recuperarSubmetasDoLote(idLote);

    	List<Long> listaIdsSubmetas = listaSubmetaBD.stream().map(SubmetaBD::getId).collect(Collectors.toList());
    	List<Long> listaIdsMetas = listaSubmetaBD.stream().map(SubmetaBD::getMetaId).collect(Collectors.toList());


    	Map<Long, PoDTO> mapaPosSubmetas = this.poBC.recuperarPoPorListaSubmetas(listaIdsSubmetas);
    	Map<Long, MetaDTO> mapaMetas = this.metaBC.recuperarMetaPorListaIds(listaIdsMetas);

		return listaSubmetaBD.stream().map(e -> {
			SubmetaDTO dto = e.converterParaDTO();

			// Mapa <submeta.id, PoDTO>
	    	PoDTO poSubmeta = mapaPosSubmetas.get(e.getId());
			dto.setPo(poSubmeta);

			// Mapa <meta.id, MetaDTO>
			MetaDTO metaSubmeta = mapaMetas.get(e.getMetaId());
	    	dto.setMeta(metaSubmeta);

	    	return dto;

		}).collect(Collectors.toList());

    }

    public BigDecimal recuperarSomatorioSubmetas(Long idLote) {
    	return this.recuperarSubmetasDoLote(idLote).stream()
    			.map(SubmetaDTO::getVlTotalLicitado).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

	public BigDecimal recuperarSomatorioListaSubmetas(List<SubmetaDTO> listaSubmetas) {
		return listaSubmetas.stream()
    			.map(SubmetaDTO::getVlTotalLicitado).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
