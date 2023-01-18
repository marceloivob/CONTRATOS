package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AtualizarHistoricoIntegracao implements Serializable {

	private static final long serialVersionUID = 2578628081597427405L;

	@NotNull
	private String proposta;

	@NotNull
	private String justificativa;

	/**
	 * Construtor Padrão
	 */
	public AtualizarHistoricoIntegracao() {
		// Necessário para o Jersey
		// Sem esse construtor, os testes unitários geram o erro:
		// SEVERE: MessageBodyWriter not found for media type=application/json
		// A solução foi encontrada nesse link:
		// https://stackoverflow.com/a/44153502/6644646
	}

	public AtualizarHistoricoIntegracao(Long proposta, String justificativa) {
		Objects.requireNonNull(proposta);
		Objects.requireNonNull(justificativa);

		this.proposta = proposta.toString();
		this.justificativa = justificativa;
	}

}
