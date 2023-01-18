package br.gov.economia.maisbrasil.contratos.domain;

public enum TipoAnexoEnum {
	
	INSTRUMENTO_CONTRATUAL,
	PUBLICACAO_EXTRATO,
	OUTROS,
	TERMO_ADITIVO,
	PUBLICACAO_TERMO_ADITIVO;
	
	@Override
	public String toString() {
		return this.name();
	}
}
