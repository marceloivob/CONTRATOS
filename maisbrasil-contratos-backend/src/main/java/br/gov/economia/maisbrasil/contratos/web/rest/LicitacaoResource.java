package br.gov.economia.maisbrasil.contratos.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.economia.maisbrasil.contratos.bc.LicitacaoBC;
import br.gov.economia.maisbrasil.contratos.domain.dto.LicitacaoDTO;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.gov.economia.maisbrasil.contratos.domain.Licitacao}.
 */
@RestController
@RequestMapping("/api")
public class LicitacaoResource {

    private final Logger log = LoggerFactory.getLogger(LicitacaoResource.class);

    private static final String ENTITY_NAME = "maisbrasilContratosBackendLicitacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LicitacaoBC licitacaoBC;

    public LicitacaoResource(LicitacaoBC licitacaoBC) {
        this.licitacaoBC = licitacaoBC;
    }

 
    /**
	 * {@code GET  /licitacoes} : get all the licitacoes.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of licitacoes in body.
	 */
	@GetMapping("/licitacoes")
	public List<LicitacaoDTO> getAlllicitacoes() {
		log.debug("REST request to get all licitacoes");
        return licitacaoBC.recuperarLicitacoesPorPropostaIdSiconv();
    }

    /**
	 * {@code GET  /licitacoes/:id} : get the "id" licitacao.
	 *
	 * @param id the id of the licitacao to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the licitacao, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/licitacoes/{id}")
    public ResponseEntity<LicitacaoDTO> getLicitacao(@PathVariable Long id) {
        log.debug("REST request to get Licitacao : {}", id);
        Optional<LicitacaoDTO> optional = Optional.of(licitacaoBC.recuperarLicitacaoPorId(id));
        return ResponseUtil.wrapOrNotFound(optional);
    }

    /**
	 * {@code GET  /licitacoes/fornecedor/:id} : get licitacoes by id fornecedor
	 *
	 * @param id the id of the fornecedor to retrieve licitacoes.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of licitacoes in body.
	 */
	@GetMapping("/licitacoes/fornecedor/{id}")
    public List<LicitacaoDTO> getLicitacaoByIdFornecedor(@PathVariable Long id) {
        log.debug("REST request to get Licitacoes by IdFornecedor : {}", id);
        return licitacaoBC.recuperarLicitacoesPorIdFornecedor(id);
    }
}
