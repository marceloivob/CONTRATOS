package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import java.io.Serializable;

import lombok.Data;

@Data
public class AtualizarHistoricoResponse implements Serializable {

	private static final long serialVersionUID = -1050743901823460402L;
	
	public Boolean sucesso;
	
	@Override
	public String toString() {
		return "AtualizarHistoricoResponse [sucesso=" + sucesso + "]";
	}

	/**
	 * Construtor Padrão
	 */
	public AtualizarHistoricoResponse() {}

	public AtualizarHistoricoResponse(Boolean sucesso) {
		if (sucesso == null) {
			sucesso = Boolean.FALSE;
		}

		this.sucesso = sucesso;
	}

}
