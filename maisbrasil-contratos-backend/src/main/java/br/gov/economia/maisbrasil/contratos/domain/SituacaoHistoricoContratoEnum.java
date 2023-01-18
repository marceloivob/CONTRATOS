package br.gov.economia.maisbrasil.contratos.domain;

public enum SituacaoHistoricoContratoEnum {
	
	EM_RASCUNHO("RAS", "Em Rascunho"),
	CONCLUIDO("CON", "Concluído"),
	EXCLUIDO("EXC", "Excluído"),
	RASCUNHO_ADITIVACAO_EM_RASCUNHO("RAR", "Contrato em Rascunho e Aditivação em Rascunho"),
	RASCUNHO_ADITIVACAO_CONCLUIDA("RAC", "Contrato em Rascunho e Aditivação Concluída"),
	CONCLUIDO_ADITIVACAO_EM_RASCUNHO("ADR", "Contrato Concluído e Aditivação em Rascunho"),
	CONCLUIDO_ADITIVACAO_CONCLUIDA("ADC", "Contrato Concluído e Aditivação Concluída");
	
	private final String sigla;
	private final String descricao;
	
	SituacaoHistoricoContratoEnum(final String sigla, final String descricao) {
		this.sigla = sigla;
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public static SituacaoHistoricoContratoEnum fromSigla(final String sigla) {
		for (SituacaoHistoricoContratoEnum evento : SituacaoHistoricoContratoEnum.values()) {
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
