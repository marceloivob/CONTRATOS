package br.gov.economia.maisbrasil.contratos.domain;

public enum StatusCheckEnum {
	OK,
	NOK,
	NA,
	INDISPONIVEL;
	
	@Override
	public String toString() {
		return this.name();
	}
}
