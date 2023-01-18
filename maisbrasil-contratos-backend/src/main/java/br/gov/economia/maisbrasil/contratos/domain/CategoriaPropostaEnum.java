package br.gov.economia.maisbrasil.contratos.domain;

public enum CategoriaPropostaEnum {
	
	OBRAS_SERVICOS_ENGENHARIA(1, "OBRAS_SERVICOS_ENGENHARIA"),
	EQUIPAMENTOS(2, "EQUIPAMENTOS");
	
	private final Integer referencia;
	private final String descricao;
	
	CategoriaPropostaEnum(final Integer referencia, final String descricao) {
		this.referencia = referencia;
		this.descricao = descricao;
	}

	public Integer getReferencia() {
		return referencia;
	}

	public String getDescricao() {
		return descricao;
	}

	public static CategoriaPropostaEnum fromDescricao(final String descricao) {
		for (CategoriaPropostaEnum evento : CategoriaPropostaEnum.values()) {
			if (evento.getDescricao().equalsIgnoreCase(descricao)) {
				return evento;
			}
		}

		throw new IllegalArgumentException("Não foi encontrado o Enum: " + descricao);
	}

	@Override
	public String toString() {
		return this.name();
	}

}
