package br.gov.economia.maisbrasil.contratos.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import br.gov.economia.maisbrasil.contratos.bc.ContratoBC;
import br.gov.economia.maisbrasil.contratos.bc.TermoAditivoBC;
import br.gov.economia.maisbrasil.contratos.domain.dto.TermoAditivoDTO;
import br.gov.economia.maisbrasil.contratos.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.gov.economia.maisbrasil.contratos.domain.TermoAditivo}.
 */
@RestController
@RequestMapping("/api")
public class TermoAditivoResource extends AbstractResource {

    private static final String ENTITY_NAME = "maisbrasilContratosBackendTermoAditivo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TermoAditivoBC termoAditivoBC;
    
    private final ContratoBC contratoBC;

    public TermoAditivoResource(TermoAditivoBC termoAditivoBC, ContratoBC contratoBC) {
        this.termoAditivoBC = termoAditivoBC;
        this.contratoBC = contratoBC;
    }

    /**
	 * {@code POST  /termosaditivos} : Create a new termoAditivo.
	 *
	 * @param termoAditivo the termoAditivo to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new termoAditivo, or with status {@code 400 (Bad Request)} if
	 *         the termoAditivo has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/termosaditivos")
    public ResponseEntity<TermoAditivoDTO> createTermoAditivo(@Valid @RequestBody TermoAditivoDTO termoAditivo) throws URISyntaxException {

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        if (termoAditivo.getId() != null) {
            throw new BadRequestAlertException("A new termoAditivo cannot already have an ID", ENTITY_NAME, "idexists");
        }

        TermoAditivoDTO result = contratoBC.inserirTermoAditivo(termoAditivo);

		return ResponseEntity.created(new URI("/api/termosaditivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
	
	@PostMapping("/termosaditivosanexos")
    public ResponseEntity<TermoAditivoDTO> createTermoAditivoAnexos(@Valid @RequestBody TermoAditivoDTO termoAditivo) throws URISyntaxException {

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        if (termoAditivo.getId() != null) {
            throw new BadRequestAlertException("A new termoAditivo cannot already have an ID", ENTITY_NAME, "idexists");
        }

        TermoAditivoDTO result = contratoBC.inserirTermoAditivoAnexos(termoAditivo);

		return ResponseEntity.created(new URI("/api/termosaditivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
	
	@PostMapping("/termosaditivosanexos/concluir")
    public ResponseEntity<TermoAditivoDTO> concluirNovoTermoAditivoAnexos(@Valid @RequestBody TermoAditivoDTO termoAditivo) throws URISyntaxException {

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        if (termoAditivo.getId() != null) {
            throw new BadRequestAlertException("A new termoAditivo cannot already have an ID", ENTITY_NAME, "idexists");
        }

        TermoAditivoDTO result = contratoBC.concluirNovoTermoAditivoAnexos(termoAditivo);

		return ResponseEntity.created(new URI("/api/termosaditivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
	
	

    /**
	 * {@code PUT  /termosaditivos} : Updates an existing termoAditivo.
	 *
	 * @param termoAditivo the termoAditivo to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated termoAditivo, or with status {@code 400 (Bad Request)} if the
	 *         termoAditivo is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the termoAditivo couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/termosaditivos")
    public ResponseEntity<TermoAditivoDTO> updateTermoAditivo(@Valid @RequestBody TermoAditivoDTO termoAditivo) throws URISyntaxException {

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        if (termoAditivo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        TermoAditivoDTO result = contratoBC.alterarTermoAditivo(termoAditivo);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoAditivo.getId().toString()))
            .body(result);
    }
	
	@PutMapping("/termosaditivos/concluir")
    public ResponseEntity<TermoAditivoDTO> concluirTermoAditivo(@Valid @RequestBody TermoAditivoDTO termoAditivo) throws URISyntaxException {

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        if (termoAditivo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        TermoAditivoDTO result = contratoBC.concluirTermoAditivoExistente(termoAditivo);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoAditivo.getId().toString()))
            .body(result);
    }

    /**
	 * {@code GET  /termosaditivos} : get all the termosaditivos.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of termosaditivos in body.
	 */
	@GetMapping("/termosaditivos")
	public List<TermoAditivoDTO> getAlltermosaditivos() {
        return termoAditivoBC.recuperarTodosTermoAditivo();
    }

    /**
	 * {@code GET  /termosaditivos/:id} : get the "id" termoAditivo.
	 *
	 * @param id the id of the termoAditivo to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the termoAditivo, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/termosaditivos/{id}")
    public ResponseEntity<TermoAditivoDTO> getTermoAditivo(@PathVariable Long id) {
        Optional<TermoAditivoDTO> optional = Optional.of(termoAditivoBC.recuperarTermoAditivoPorId(id));
        return ResponseUtil.wrapOrNotFound(optional);
    }

    /**
	 * {@code DELETE  /termosaditivos/:id} : delete the "id" termoAditivo.
	 *
	 * @param id the id of the termoAditivo to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/termosaditivos/{id}")
    public ResponseEntity<Void> deleteTermoAditivo(@PathVariable Long id) {

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        contratoBC.excluirTermoAditivo(id);

        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
