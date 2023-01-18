package br.gov.economia.maisbrasil.contratos.domain;

public enum SituacaoContratoEnum {
	
	EM_RASCUNHO("RAS", "Em Rascunho"),
	CONCLUIDO("CON", "Concluído"),
	EXCLUIDO("EXC", "Excluído");
	
	private final String sigla;
	private final String descricao;
	
	SituacaoContratoEnum(final String sigla, final String descricao) {
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
