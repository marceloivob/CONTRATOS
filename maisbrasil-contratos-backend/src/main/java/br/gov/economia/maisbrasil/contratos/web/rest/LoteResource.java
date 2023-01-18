package br.gov.economia.maisbrasil.contratos.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.economia.maisbrasil.contratos.bc.LoteBC;
import br.gov.economia.maisbrasil.contratos.domain.dto.LoteDTO;
import br.gov.economia.maisbrasil.contratos.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.gov.economia.maisbrasil.contratos.domain.Lote}.
 */
@RestController
@RequestMapping("/api")
public class LoteResource {

    private final Logger log = LoggerFactory.getLogger(LoteResource.class);

    private static final String ENTITY_NAME = "maisbrasilContratosBackendLote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoteBC loteBC;

    public LoteResource(LoteBC loteBC) {
        this.loteBC = loteBC;
    }

    /**
	 * {@code GET  /lotes} : get all the lotes.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of lotes in body.
	 */
	@GetMapping("/lotes")
	public List<LoteDTO> getAllLotes() {
		log.debug("REST request to get all Lotes");
        return loteBC.recuperarTodosLotesDaProposta();
    }

    /**
	 * {@code GET  /lotes/:id} : get the "id" lote.
	 *
	 * @param id the id of the lote to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the lote, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/lotes/{id}")
    public ResponseEntity<LoteDTO> getLote(@PathVariable Long id) {
        log.debug("REST request to get Lote : {}", id);
        Optional<LoteDTO> optional = Optional.of(loteBC.recuperarLotePorId(id));
        return ResponseUtil.wrapOrNotFound(optional);
    }

 	
    /**
	 * {@code GET  /lotes} : get all the lotes.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of lotes in body.
	 */
	@GetMapping("/lotes/fornecedor/{idFornecedor}/licitacao/{idLicitacao}")
	public List<LoteDTO> recuperarLotesPorIdFornecedorEIdLicitacao(@PathVariable Long idFornecedor, @PathVariable Long idLicitacao) {
		log.debug("REST request to get all Lotes");
        return loteBC.recuperarLotesPorIdFornecedorEIdLicitacao(idFornecedor, idLicitacao);
    }
	
}
