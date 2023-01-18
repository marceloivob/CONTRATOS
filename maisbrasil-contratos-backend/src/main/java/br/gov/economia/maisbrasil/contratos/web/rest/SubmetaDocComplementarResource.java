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

import br.gov.economia.maisbrasil.contratos.bc.SubmetaDocComplementarBC;
import br.gov.economia.maisbrasil.contratos.domain.dto.SubmetaDocComplementarDTO;
import br.gov.economia.maisbrasil.contratos.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.gov.economia.maisbrasil.contratos.domain.SubmetaDocComplementar}.
 */
@RestController
@RequestMapping("/api")
public class SubmetaDocComplementarResource extends AbstractResource {

    private final Logger log = LoggerFactory.getLogger(SubmetaDocComplementarResource.class);

    private static final String ENTITY_NAME = "maisbrasilContratosBackendSubmetaDocComplementar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubmetaDocComplementarBC submetaDocComplementarBC;

    public SubmetaDocComplementarResource(SubmetaDocComplementarBC submetaDocComplementarBC) {
        this.submetaDocComplementarBC = submetaDocComplementarBC;
    }

    /**
	 * {@code POST  /submetaDocComplementars} : Create a new submetaDocComplementar.
	 *
	 * @param submetaDocComplementar the submetaDocComplementar to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new submetaDocComplementar, or with status {@code 400 (Bad Request)} if
	 *         the submetaDocComplementar has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/submetaDocComplementars")
    public ResponseEntity<SubmetaDocComplementarDTO> createSubmetaDocComplementar(@Valid @RequestBody SubmetaDocComplementarDTO submetaDocComplementar) throws URISyntaxException {
        log.debug("REST request to save SubmetaDocComplementar : {}", submetaDocComplementar);

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        if (submetaDocComplementar.getId() != null) {
            throw new BadRequestAlertException("A new submetaDocComplementar cannot already have an ID", ENTITY_NAME, "idexists");
        }

        SubmetaDocComplementarDTO result = submetaDocComplementarBC.inserir(submetaDocComplementar);

		return ResponseEntity.created(new URI("/api/submetaDocComplementars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
	 * {@code PUT  /submetaDocComplementars} : Updates an existing submetaDocComplementar.
	 *
	 * @param submetaDocComplementar the submetaDocComplementar to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated submetaDocComplementar, or with status {@code 400 (Bad Request)} if the
	 *         submetaDocComplementar is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the submetaDocComplementar couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/submetaDocComplementars")
    public ResponseEntity<SubmetaDocComplementarDTO> updateSubmetaDocComplementar(@Valid @RequestBody SubmetaDocComplementarDTO submetaDocComplementar) throws URISyntaxException {
        log.debug("REST request to update SubmetaDocComplementar : {}", submetaDocComplementar);

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        if (submetaDocComplementar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        SubmetaDocComplementarDTO result = submetaDocComplementarBC.alterar(submetaDocComplementar);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, submetaDocComplementar.getId().toString()))
            .body(result);
    }

    /**
	 * {@code GET  /submetaDocComplementars} : get all the submetaDocComplementars.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of submetaDocComplementars in body.
	 */
	@GetMapping("/submetaDocComplementars")
	public List<SubmetaDocComplementarDTO> getAllSubmetaDocComplementars() {
		log.debug("REST request to get all SubmetaDocComplementars");
        return submetaDocComplementarBC.recuperarTodosSubmetaDocComplementar();
    }

    /**
	 * {@code GET  /submetaDocComplementars/:id} : get the "id" submetaDocComplementar.
	 *
	 * @param id the id of the submetaDocComplementar to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the submetaDocComplementar, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/submetaDocComplementars/{id}")
    public ResponseEntity<SubmetaDocComplementarDTO> getSubmetaDocComplementar(@PathVariable Long id) {
        log.debug("REST request to get SubmetaDocComplementar : {}", id);
        Optional<SubmetaDocComplementarDTO> optional = Optional.of(submetaDocComplementarBC.recuperarSubmetaDocComplementarPorId(id));
        return ResponseUtil.wrapOrNotFound(optional);
    }

    /**
	 * {@code DELETE  /submetaDocComplementars/:id} : delete the "id" submetaDocComplementar.
	 *
	 * @param id the id of the submetaDocComplementar to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/submetaDocComplementars/{id}")
    public ResponseEntity<Void> deleteSubmetaDocComplementar(@PathVariable Long id) {
        log.debug("REST request to delete SubmetaDocComplementar : {}", id);

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        submetaDocComplementarBC.excluir(id);

        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
