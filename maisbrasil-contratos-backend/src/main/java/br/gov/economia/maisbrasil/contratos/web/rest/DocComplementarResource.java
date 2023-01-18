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

import br.gov.economia.maisbrasil.contratos.bc.DocComplementarBC;
import br.gov.economia.maisbrasil.contratos.domain.dto.DocComplementarDTO;
import br.gov.economia.maisbrasil.contratos.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.gov.economia.maisbrasil.contratos.domain.DocComplementar}.
 */
@RestController
@RequestMapping("/api")
public class DocComplementarResource extends AbstractResource {

    private final Logger log = LoggerFactory.getLogger(DocComplementarResource.class);

    private static final String ENTITY_NAME = "maisbrasilContratosBackendDocComplementar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocComplementarBC docComplementarBC;

    public DocComplementarResource(DocComplementarBC docComplementarBC) {
        this.docComplementarBC = docComplementarBC;
    }

    /**
	 * {@code POST  /docComplementars} : Create a new docComplementar.
	 *
	 * @param docComplementar the docComplementar to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new docComplementar, or with status {@code 400 (Bad Request)} if
	 *         the docComplementar has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/docComplementars")
    public ResponseEntity<DocComplementarDTO> createDocComplementar(@Valid @RequestBody DocComplementarDTO docComplementar) throws URISyntaxException {
        log.debug("REST request to save DocComplementar : {}", docComplementar);

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        if (docComplementar.getId() != null) {
            throw new BadRequestAlertException("A new docComplementar cannot already have an ID", ENTITY_NAME, "idexists");
        }

        DocComplementarDTO result = docComplementarBC.inserir(docComplementar);

		return ResponseEntity.created(new URI("/api/docComplementars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
	 * {@code PUT  /docComplementars} : Updates an existing docComplementar.
	 *
	 * @param docComplementar the docComplementar to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated docComplementar, or with status {@code 400 (Bad Request)} if the
	 *         docComplementar is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the docComplementar couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/docComplementars")
    public ResponseEntity<DocComplementarDTO> updateDocComplementar(@Valid @RequestBody DocComplementarDTO docComplementar) throws URISyntaxException {
        log.debug("REST request to update DocComplementar : {}", docComplementar);

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        if (docComplementar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        DocComplementarDTO result = docComplementarBC.alterar(docComplementar);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docComplementar.getId().toString()))
            .body(result);
    }

    /**
	 * {@code GET  /docComplementars} : get all the docComplementars.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of docComplementars in body.
	 */
	@GetMapping("/docComplementars")
	public List<DocComplementarDTO> getAllDocComplementars() {
		log.debug("REST request to get all DocComplementars");
        return docComplementarBC.recuperarTodosDocComplementar();
    }

    /**
	 * {@code GET  /docComplementars/:id} : get the "id" docComplementar.
	 *
	 * @param id the id of the docComplementar to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the docComplementar, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/docComplementars/{id}")
    public ResponseEntity<DocComplementarDTO> getDocComplementar(@PathVariable Long id) {
        log.debug("REST request to get DocComplementar : {}", id);
        Optional<DocComplementarDTO> optional = Optional.of(docComplementarBC.recuperarDocComplementarPorId(id));
        return ResponseUtil.wrapOrNotFound(optional);
    }

    /**
	 * {@code DELETE  /docComplementars/:id} : delete the "id" docComplementar.
	 *
	 * @param id the id of the docComplementar to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/docComplementars/{id}")
    public ResponseEntity<Void> deleteDocComplementar(@PathVariable Long id) {
        log.debug("REST request to delete DocComplementar : {}", id);

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        docComplementarBC.excluir(id);

        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
