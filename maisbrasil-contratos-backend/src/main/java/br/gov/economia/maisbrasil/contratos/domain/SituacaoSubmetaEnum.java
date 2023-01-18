package br.gov.economia.maisbrasil.contratos.domain;

public enum SituacaoSubmetaEnum {
	ACEITA_PELO_VRPL("ACT", "Aceita"),
	APTA_A_INICIAR("AAI", "Apta a Iniciar");
	
	private final String sigla;
	private final String descricao;
	
	SituacaoSubmetaEnum(final String sigla, final String descricao) {
		this.sigla = sigla;
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public static SituacaoContratoEnum fromSigla(final String sigla) {
		for (SituacaoContratoEnum evento : SituacaoContratoEnum.values()) {
			if (evento.getSigla().equalsIgnoreCase(sigla)) {
				return evento;
			}
		}

		throw new IllegalArgumentException("Não foi encontrado o Enum: " + sigla);
	}

	@Override
	public String toString() {
		return this.name();
	}
}
