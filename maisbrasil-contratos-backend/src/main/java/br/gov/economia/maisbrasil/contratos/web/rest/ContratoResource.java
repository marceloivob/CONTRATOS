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

import br.gov.economia.maisbrasil.contratos.bc.ContratoBC;
import br.gov.economia.maisbrasil.contratos.domain.dto.ContratoDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.DadosChecklistDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.EmissaoAIORequestDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.MensagemDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.TermoAditivoDTO;
import br.gov.economia.maisbrasil.contratos.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.gov.economia.maisbrasil.contratos.domain.Contrato}.
 */
@RestController
@RequestMapping("/api")
public class ContratoResource extends AbstractResource{

    private final Logger log = LoggerFactory.getLogger(ContratoResource.class);

    private static final String ENTITY_NAME = "maisbrasilContratosBackendContrato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContratoBC contratoBC;


    public ContratoResource(ContratoBC contratoBC) {
        this.contratoBC = contratoBC;
    }

    /**
	 * {@code POST  /contratos} : Create a new contrato.
	 *
	 * @param contrato the contrato to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new contrato, or with status {@code 400 (Bad Request)} if
	 *         the contrato has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/contratos")
    public ResponseEntity<ContratoDTO> createContrato(@Valid @RequestBody ContratoDTO contrato) throws URISyntaxException {
        log.debug("REST request to save Contrato : {}", contrato.getNumero());

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        if (contrato.getId() != null) {
            throw new BadRequestAlertException("A new contrato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContratoDTO result = contratoBC.inserir(contrato);
		return ResponseEntity.created(new URI("/api/contratos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
	 * {@code PUT  /contratos} : Updates an existing contrato.
	 *
	 * @param contrato the contrato to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated contrato, or with status {@code 400 (Bad Request)} if the
	 *         contrato is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the contrato couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/contratos")
    public ResponseEntity<ContratoDTO> updateContrato(@Valid @RequestBody ContratoDTO contrato) throws URISyntaxException {
		log.debug("REST request to update Contrato : {}", contrato.getNumero());

		this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

		if (contrato.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContratoDTO result = contratoBC.alterar(contrato);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contrato.getId().toString()))
            .body(result);
    }

    /**
	 * {@code GET  /contratos} : get all the contratos.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of contratos in body.
	 */
	@GetMapping("/contratos")
	public List<ContratoDTO> getAllContratos() {
		log.debug("REST request to get all Contratos");
        return contratoBC.recuperarTodosContrato();
    }

    /**
	 * {@code GET  /contratos/:id} : get the "id" contrato.
	 *
	 * @param id the id of the contrato to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the contrato, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/contratos/{id}")
    public ResponseEntity<ContratoDTO> getContrato(@PathVariable Long id) {
        log.debug("REST request to get Contrato : {}", id);
        Optional<ContratoDTO> optional = Optional.of(contratoBC.recuperarContratoPorId(id));
        return ResponseUtil.wrapOrNotFound(optional);
    }

    /**
	 * {@code DELETE  /contratos/:id} : delete the "id" contrato.
	 *
	 * @param id the id of the contrato to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/contratos/{id}")
    public ResponseEntity<Void> deleteContrato(@PathVariable Long id) {
        log.debug("REST request to delete Contrato : {}", id);

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        contratoBC.excluir(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
	 * {@code GET  /contratos/:id/checklist} : get checklist contrato.
	 *
	 * @param id the id of the contrato to retrieve checklist.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the checklist contrato, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/contratos/{id}/checklist")
    public DadosChecklistDTO getChecklistContrato(@PathVariable Long id) {
        log.debug("REST request to get Checklist Contrato : {}", id);
        return contratoBC.recuperarDadosChecklistDoContrato(id);
    }

    /**
	 * {@code PUT  /contratos/concluir} : Updates an existing concluir contrato.
	 *
	 * @param id the id of the contrato to update.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@PutMapping("/contratos/concluir/{versao}")
	public ResponseEntity<List<MensagemDTO>> concluirContrato(@Valid @RequestBody Long idContrato,
			@PathVariable Long versao) {

		log.debug("REST request to update Concluir Contrato : {}, Versão: {}", idContrato, versao);

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

		List<MensagemDTO> msgs = contratoBC.concluir(idContrato, versao);

        return ResponseEntity.ok()
	        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, idContrato.toString()))
	        .body(msgs);
    }

    /**
	 * {@code PUT  /contratos/emissaoaio} : Verificar emissao da AIO
	 *
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@PutMapping("/contratos/emissaoaio")
	public ResponseEntity<List<MensagemDTO>> verificarEmissaoAIO(@Valid @RequestBody EmissaoAIORequestDTO requestEmissaoAIO) {

		Long idContrato = requestEmissaoAIO.getIdContrato();

		log.debug("REST request to verificar emissao AIO Contrato : {}", idContrato);

        this.verificaSeUsuarioLogadoTemPermissaoParaEmitirAIO(ENTITY_NAME);

		List<MensagemDTO> msgs = contratoBC.verificarEmissaoAIO(idContrato, requestEmissaoAIO);

        return ResponseEntity.ok()
	        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, idContrato.toString()))
	        .body(msgs);
    }

	@PostMapping("/contratoanexos")
    public ResponseEntity<ContratoDTO> createContratoAnexos(@Valid @RequestBody ContratoDTO contrato) throws URISyntaxException {

        log.debug("REST request to save Contrato and Anexos: {}", contrato.getNumero());

        this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

        if (contrato.getId() != null) {
            throw new BadRequestAlertException("A new contrato cannot already have an ID", ENTITY_NAME, "idexists");
        }

        ContratoDTO result = contratoBC.inserirContratoAnexos(contrato);
		return ResponseEntity.created(new URI("/api/contratos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
	
    /**
	 * {@code GET  /termosaditivos} : get all the termosaditivos.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of termosaditivos in body.
	 */
	@GetMapping("/contratos/{id}/termosaditivos")
	public List<TermoAditivoDTO> getTermosAditivosPorContratoId(@PathVariable Long id) {
	    return contratoBC.recuperarTermoAditivoPorContratoId(id);
    }


}



