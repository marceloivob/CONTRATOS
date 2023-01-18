package br.gov.economia.maisbrasil.contratos.domain.dto;

import lombok.Data;

@Data
public class MensagemDTO {
	public String texto;
	public String severidade;
	
	public MensagemDTO(String texto, String severidade) {
		this.texto = texto;
		this.severidade = severidade;
	}
}
