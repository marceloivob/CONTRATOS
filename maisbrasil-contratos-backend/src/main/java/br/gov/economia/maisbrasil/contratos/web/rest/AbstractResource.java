package br.gov.economia.maisbrasil.contratos.web.rest;

import org.springframework.beans.factory.annotation.Autowired;

import br.gov.economia.maisbrasil.contratos.security.PermissionController;
import br.gov.economia.maisbrasil.contratos.web.rest.errors.BadRequestAlertException;

public abstract class AbstractResource {

	@Autowired
	protected PermissionController controladorPermissoes;

	public void verificaSeUsuarioLogadoTemPermissao(String entityName) {
    	if (!this.controladorPermissoes.usuarioLogadoTemPermissaoParaEditarContrato()) {
			throw new BadRequestAlertException("Usuário não possui permissão.", entityName, "idnull");
		}
    }

	public void verificaSeUsuarioLogadoTemPermissaoParaCancelarAIO(String entityName) {
    	if (!this.controladorPermissoes.usuarioLogadoTemPermissaoParaCancelarAIO()) {
			throw new BadRequestAlertException("Usuário não possui permissão.", entityName, "idnull");
		}
    }

	public void verificaSeUsuarioLogadoTemPermissaoParaEmitirAIO(String entityName) {
    	if (!this.controladorPermissoes.usuarioLogadoTemPermissaoParaEmitirAIO()) {
			throw new BadRequestAlertException("Usuário não possui permissão.", entityName, "idnull");
		}
    }
}
