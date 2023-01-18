package br.gov.economia.maisbrasil.contratos.web.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.economia.maisbrasil.contratos.bc.AnexoBC;
import br.gov.economia.maisbrasil.contratos.bc.ContratoBC;
import br.gov.economia.maisbrasil.contratos.domain.bd.AnexoBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.AnexoDTO;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link br.gov.economia.maisbrasil.contratos.domain.Anexo}.
 */
@RestController
@RequestMapping("/api")
public class AnexoResource extends AbstractResource{

	private final Logger log = LoggerFactory.getLogger(AnexoResource.class);

	private static final String ENTITY_NAME = "maisbrasilContratosBackendAnexo";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final AnexoBC anexoBC;

	private final ContratoBC contratoBC;

	@Autowired
	public AnexoResource(AnexoBC anexoBC, ContratoBC contratoBC) {
		this.anexoBC = anexoBC;
		this.contratoBC = contratoBC;
	}
	
	
	/**
	 * {@code GET  /contratos/:id} : get the "id" contrato.
	 *
	 * @param id the id of the contrato to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the contrato, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/contratos/{id}/anexos")
    public List<AnexoDTO> getAnexosContrato(@PathVariable Long id) {
        log.debug("REST request to get anexos do Contrato : {}", id);
        List<AnexoDTO> anexos = anexoBC.recuperarAnexosPorIdContrato(id);
        return anexos;
    }
	
	
	@PostMapping(value = "/anexos/{idContrato}", consumes = "multipart/form-data", 
		    produces = "application/json; charset=utf-8")
    public ResponseEntity<String> anexarArquivo(@ModelAttribute FormWrapper model, @NotNull @PathVariable("idContrato") Long idContrato) throws IOException {
		
		this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);
		
    	this.anexoBC.anexarArquivo(idContrato, model.getNomeArquivo(), model.getDescricao(),
    			model.getTipoAnexo(), model.getArquivo().getInputStream(), model.getTamanhoArquivo());

        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(value = "/anexos/termo-aditivo/{idTermoAditivo}", consumes = "multipart/form-data", 
		    produces = "application/json; charset=utf-8")
    public ResponseEntity<String> anexarArquivoTermoAditivo(@ModelAttribute FormWrapper model, @NotNull @PathVariable("idTermoAditivo") Long idTermoAditivo) throws IOException {
		
		this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);
		
    	this.anexoBC.anexarArquivoTermoAditivo(idTermoAditivo, model.getNomeArquivo(), model.getDescricao(),
    			model.getTipoAnexo(), model.getArquivo().getInputStream(), model.getTamanhoArquivo());
    	
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
    // produces = { "application/json", "application/xml" }
	@PutMapping(value = "/anexos/{idAnexo}", consumes = "multipart/form-data", 
			produces = "application/json; charset=utf-8" )
    public ResponseEntity<?> atualizarAnexo(@ModelAttribute FormWrapper model, @NotNull @PathVariable("idAnexo") Long idAnexo) throws IOException {
       
		this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);
		
		this.contratoBC.podeAlterarAnexoContratoConcluido(idAnexo, model.getTipoAnexo());

	   	InputStream inputStream = null;
	    if (model.getArquivo() != null) {
	    	inputStream = model.getArquivo().getInputStream();
	    }
		this.anexoBC.atualizarAnexo(idAnexo, model.getNomeArquivo(), model.getDescricao(), 
    			model.getTipoAnexo(), model.getVersao(), inputStream, model.getTamanhoArquivo()); 

       return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PutMapping(value = "/anexos/termo-aditivo/{idAnexo}", consumes = "multipart/form-data", 
			produces = "application/json; charset=utf-8" )
    public ResponseEntity<?> atualizarAnexoTermoAditivo(@ModelAttribute FormWrapper model, @NotNull @PathVariable("idAnexo") Long idAnexo) throws IOException {
       
		this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);
		
	   	InputStream inputStream = null;
	    if (model.getArquivo() != null) {
	    	inputStream = model.getArquivo().getInputStream();
	    }
		this.anexoBC.atualizarAnexo(idAnexo, model.getNomeArquivo(), model.getDescricao(), 
    			model.getTipoAnexo(), model.getVersao(), inputStream, model.getTamanhoArquivo()); 

       return new ResponseEntity<>(HttpStatus.OK);
    }


	/**
	 * {@code GET  /anexos/:id} : get the "id" anexo.
	 *
	 * @param id the id of the anexo to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the anexo, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/anexos/{id}")
	public ResponseEntity<AnexoBD> getAnexo(@PathVariable Long id) {
		log.debug("REST request to get Anexo : {}", id);
		Optional<AnexoBD> anexo = anexoBC.recuperarAnexoPorId(id);
		return ResponseUtil.wrapOrNotFound(anexo);
	}

	/**
	 * {@code DELETE  /anexos/:id} : delete the "id" anexo.
	 *
	 * @param id the id of the anexo to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/anexos/{id}")
	public ResponseEntity<Void> deleteAnexo(@PathVariable Long id) {
		log.debug("REST request to delete Anexo : {}", id);

		this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

		this.contratoBC.podeRemoverAnexoContratoConcluido(id);

		anexoBC.apagarAnexo(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
	
	/**
	 * {@code DELETE  /anexos/termo-aditivo/:id} : delete the "id" anexo.
	 *
	 * @param id the id of the anexo to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/anexos/termo-aditivo/{id}")
	public ResponseEntity<Void> deleteAnexoTermoAditivo(@PathVariable Long id) {

		this.verificaSeUsuarioLogadoTemPermissao(ENTITY_NAME);

		anexoBC.apagarAnexo(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
	
	/**
	 * {@code GET  /contratos/:id} : get the "id" contrato.
	 *
	 * @param id the id of the contrato to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the contrato, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/anexos/{idContrato}/{tipoAnexo}")
    public List<AnexoDTO> getAnexosContratoPorTipo(@PathVariable Long idContrato, @PathVariable String tipoAnexo){
        log.debug("REST request to get anexos do Contrato : {} {}", idContrato, tipoAnexo);
        List<AnexoDTO> anexos = anexoBC.listarAnexosPorTipo(idContrato, tipoAnexo);
        return anexos;
	}
	
	
	/**
	 * 
	 * @param idContrato
	 * @param tiposAnexos
	 * @return
	 */
	@GetMapping("/anexos/{idContrato}/portipos")
	public List<AnexoDTO> getAnexosContratoPorConjuntoTipos(@PathVariable Long idContrato, 
			@RequestParam("tipos") List<String> tipos) {
		
		log.debug("REST request to get anexos do Contrato : {} {}", idContrato, tipos);
		List<AnexoDTO> listaAnexoDTO = anexoBC.getAnexosContratoPorConjuntoTipos(idContrato, tipos);
		return listaAnexoDTO;
		
	}
	
	/**
	 * 
	 * @param idTermoAditivo
	 * @param tiposAnexos
	 * @return
	 */
	@GetMapping("/anexos/termo-aditivo/{idTermoAditivo}/portipos")
	public List<AnexoDTO> getAnexosTermoAditivoPorConjuntoTipos(@PathVariable Long idTermoAditivo, 
			@RequestParam("tipos") List<String> tipos) {
		
		List<AnexoDTO> listaAnexoDTO = anexoBC.getAnexosTermoAditivoPorConjuntoTipos(idTermoAditivo, tipos);
		return listaAnexoDTO;
		
	}
	
}
