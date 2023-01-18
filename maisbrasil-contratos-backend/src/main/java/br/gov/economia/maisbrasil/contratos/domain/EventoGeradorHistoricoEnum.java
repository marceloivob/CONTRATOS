package br.gov.economia.maisbrasil.contratos.domain;

public enum EventoGeradorHistoricoEnum {
	
	SALVAR_RASCUNHO("RAS", "Rascunho salvo do instrumento contratual", ""),
	CONCLUSAO_CONTRATO("CON", "Conclusão do instrumento contratual", ""),
	EMISSAO_AIO_CONCLUSAO("EAC", "AIO emitida de forma indireta, a partir da conclusão de um instrumento contratual pelo usuário", 
			"AIO emitida a partir da conclusão do instrumento contratual "),
	EMISSAO_AIO_VERIFICACAO("EAV", "AIO emitida de forma direta, a partir da verificação da emissão da AIO pelo usuário", 
			"AIO emitida pelo usuário a partir da opção de verificação da emissão da AIO."),
	EMISSAO_AIO_ROTINA_AUTOMATICA("EAR", "AIO emitida pela rotina automática de execução diária (batch)", 
			"AIO emitida a partir da rotina de geração automática."),
	CANCELAMENTO_EMISSAO_AIO("CEA", "Cancelamento da emissão da AIO", "Cancelamento da emissão da AIO"),
	EXCLUSAO_CONTRATO("EXC", "Exclusão do instrumento contratual", ""),
	TERMO_ADITIVO_SALVAR_RASCUNHO("ADR", " Rascunho salvo do termo aditivo", ""),
	TERMO_ADITIVO_CONCLUSAO("ADC", "Conclusão do termo aditivo", ""),
	TERMO_ADITIVO_EXCLUSAO("ADE", "Exclusão do termo aditivo", "");
	
	private final String sigla;
	private final String descricao;
	private final String mensagemSICONV;
	
	EventoGeradorHistoricoEnum(final String sigla, final String descricao, final String mensagemSICONV) {
		this.sigla = sigla;
		this.descricao = descricao;
		this.mensagemSICONV = mensagemSICONV;
	}

	public String getSigla() {
		return sigla;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public String getMensagemSICONV() {
		return mensagemSICONV;
	}

	public static EventoGeradorHistoricoEnum fromSigla(final String sigla) {
		for (EventoGeradorHistoricoEnum evento : EventoGeradorHistoricoEnum.values()) {
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
