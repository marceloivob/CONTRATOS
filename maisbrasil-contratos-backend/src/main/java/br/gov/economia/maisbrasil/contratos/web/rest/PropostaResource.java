package br.gov.economia.maisbrasil.contratos.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.economia.maisbrasil.contratos.bc.ContratoBC;
import br.gov.economia.maisbrasil.contratos.bc.HistoricoBC;
import br.gov.economia.maisbrasil.contratos.bc.PropostaBC;
import br.gov.economia.maisbrasil.contratos.domain.dto.ContratoDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.HistoricoContratoDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.MensagemDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.PropostaDTO;
import br.gov.economia.maisbrasil.contratos.repository.VrplRepository;
import br.gov.economia.maisbrasil.contratos.security.SecurityUtils;
import br.gov.economia.maisbrasil.contratos.security.SiconvUser;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link br.gov.economia.maisbrasil.contratos.domain.Proposta}.
 */
@RestController
@RequestMapping("/api")
public class PropostaResource extends AbstractResource {

	private final Logger log = LoggerFactory.getLogger(PropostaResource.class);

	private static final String ENTITY_NAME = "maisbrasilContratosBackendProposta";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PropostaBC propostaBC;

	private final ContratoBC contratoBC;
	
	private final HistoricoBC historicoBC;

	private final VrplRepository vrplRepository;

	public PropostaResource(PropostaBC propostaBC, ContratoBC contratoBC, HistoricoBC historicoBC, VrplRepository vrplRepository) {
		this.propostaBC = propostaBC;
		this.contratoBC = contratoBC;
		this.historicoBC = historicoBC;
		this.vrplRepository = vrplRepository;
	}

	/**
	 * {@code GET  /propostas/:id} : get the "id" proposta.
	 *
	 * @param id the id of the proposta to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the proposta, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/propostas")
	public ResponseEntity<PropostaDTO> getProposta() {
		Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();

		Long idProposta = usuarioLogado.getIdProposta();

		log.debug("REST request to get Proposta : {}", idProposta);
		Optional<PropostaDTO> proposta = propostaBC.recuperarPropostaPorIdSiconv(idProposta);
		return ResponseUtil.wrapOrNotFound(proposta);
	}

	@GetMapping("/proposta/{idProposta}/contratos")
	public List<ContratoDTO> loadContratosPorProposta(@PathVariable Long idProposta) {
		List<ContratoDTO> contratos = contratoBC.loadContratosPorIdSiconvProposta(idProposta);

		return contratos;
	}
 
	@GetMapping("/proposta/verifica-inicio-contratos")	
	public ResponseEntity<Boolean> verificaInicioContratos(){
		
		Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();

		Long idProposta = usuarioLogado.getIdProposta();
		
		boolean retorno = propostaBC.verificaInicioContratos(idProposta);
		
	    return ResponseEntity.ok(retorno);
	}
	
	@GetMapping("/proposta/{idProposta}/verifica-cancelamento-aio")	
	public ResponseEntity<List<MensagemDTO>> verificarCancelarEmissaoAIO(@PathVariable Long idProposta){
			
		log.debug("REST request to verificar permissao para cancelar emissao de AIO");
		
		List<MensagemDTO> retorno = contratoBC.verificarCancelarEmissaoAIO(idProposta); 

	    return ResponseEntity.ok(retorno);
	}
	
	@PutMapping("/proposta/{idProposta}/cancela-aio")
	public ResponseEntity<List<MensagemDTO>> cancelarEmissaoAIO(@PathVariable Long idProposta) {

		log.debug("REST request to cancelar emissao de AIO");
        
        this.verificaSeUsuarioLogadoTemPermissaoParaCancelarAIO(ENTITY_NAME);
        
		List<MensagemDTO> retorno = contratoBC.cancelarEmissaoAIOTransaction(idProposta);
        
        return ResponseEntity.ok(retorno);
    }
	
	/**
	 * {@code GET  /propostas/:id/checklist} : get resolved checklist the "id" proposta.
	 *
	 * @param id the id of the proposta to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the proposta, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/proposta/{id}/checklist")
	public Boolean resolveChecklistProposta(@PathVariable Long id) {

		log.debug("REST request to get checklist Proposta : {}", id);
		return this.propostaBC.validarChecklistProposta(id);
	}
	
	/**
	 * {@code GET  /propostas/:id/checklist} : get resolved checklist the "id" proposta.
	 *
	 * @param id the id of the proposta to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the proposta, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/proposta/{id}/emissaoaio")
	public Boolean informarEmissaoAIO(@PathVariable Long id) {

		log.debug("REST request to get checklist Proposta : {}");
		return this.propostaBC.informarEmissaoAIOBatch(id);
	}
	
	@GetMapping("/proposta/{idProposta}/historico-contratos")
	public List<HistoricoContratoDTO> loadHistoricoContratosDaProposta(@PathVariable Long idProposta) {
		List<HistoricoContratoDTO>  historico = historicoBC.recuperarHistoricoContratosDaProposta(idProposta);

		return historico;
	}
	
	@GetMapping("/proposta/{idProposta}/historico-contratos-excluidos")
	public List<HistoricoContratoDTO> loadHistoricoContratosExcluidosDaProposta(@PathVariable Long idProposta) {
		List<HistoricoContratoDTO>  historico = historicoBC.recuperarHistoricoContratosExcluidosDaProposta(idProposta);

		return historico;
	}



}
