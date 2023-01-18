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

import br.gov.economia.maisbrasil.contratos.bc.FornecedorBC;
import br.gov.economia.maisbrasil.contratos.domain.dto.FornecedorDTO;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.gov.economia.maisbrasil.contratos.domain.Fornecedor}.
 */
@RestController
@RequestMapping("/api")
public class FornecedorResource {

    private final Logger log = LoggerFactory.getLogger(FornecedorResource.class);

    private static final String ENTITY_NAME = "maisbrasilContratosBackendFornecedor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FornecedorBC fornecedorBC;

    public FornecedorResource(FornecedorBC fornecedorBC) {
        this.fornecedorBC = fornecedorBC;
    }

  
    /**
	 * {@code GET  /fornecedores} : get all the fornecedores.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of fornecedores in body.
	 */
	@GetMapping("/fornecedores")
	public List<FornecedorDTO> getAllfornecedores() {
		log.debug("REST request to get all fornecedores");
        return fornecedorBC.recuperarFornecedoresPorProposta();
    }

    /**
	 * {@code GET  /fornecedores/:id} : get the "id" fornecedor.
	 *
	 * @param id the id of the fornecedor to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the fornecedor, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/fornecedores/{id}")
    public ResponseEntity<FornecedorDTO> getFornecedor(@PathVariable Long id) {
        log.debug("REST request to get Fornecedor : {}", id);
        Optional<FornecedorDTO> optional = Optional.of(fornecedorBC.recuperarFornecedorPorId(id));
        return ResponseUtil.wrapOrNotFound(optional);
    }
}
