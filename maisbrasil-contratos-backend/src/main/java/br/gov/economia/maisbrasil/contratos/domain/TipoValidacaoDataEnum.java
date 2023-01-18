package br.gov.economia.maisbrasil.contratos.domain;

public enum TipoValidacaoDataEnum {
	PERIODO_ELEITORAL,
	RESTOS_A_PAGAR;
	
	@Override
	public String toString() {
		return this.name();
	}
}
