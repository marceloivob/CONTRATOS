package br.gov.economia.maisbrasil.contratos.domain;

import br.gov.economia.maisbrasil.contratos.security.TipoOrgao;

public enum ModalidadeInstrumentoEnum {
	
	CONVENIO(1L, "Convênio") {
		@Override
		public TipoOrgao getTipoOrgao() {
			return TipoOrgao.CONCEDENTE;
		}
	},
	CONTRATO_DE_REPASSE(2L, "Contrato de Repasse") {
		@Override
		public TipoOrgao getTipoOrgao() {
			return TipoOrgao.INST_MANDATARIA;
		}
	},
	CONVENIO_CONTRATO_DE_REPASSE(6L, "Convênio ou Contrato de Repasse") {
		@Override
		public TipoOrgao getTipoOrgao() {
			return TipoOrgao.CONCEDENTE;
		}
	},
	TERMO_DE_COMPROMISSO_MANDATARIA (11L, "Termo de Compromisso", true) {
		@Override
		public TipoOrgao getTipoOrgao() {
			return TipoOrgao.INST_MANDATARIA;
		}
	},
	TERMO_DE_COMPROMISSO_CONCEDENTE (11L, "Termo de Compromisso", false) {
		@Override
		public TipoOrgao getTipoOrgao() {
			return TipoOrgao.CONCEDENTE;
		}
	};;
	
	private final long valor;
	private final String descricao;
	private boolean possuiMandataria = false;
	
	ModalidadeInstrumentoEnum(final long valor, final String descricao) {
		this.valor = valor;
		this.descricao = descricao;
	}
	
	ModalidadeInstrumentoEnum(final long valor, final String descricao, boolean possuiMandataria) {
		this.valor = valor;
		this.descricao = descricao;
		this.possuiMandataria = possuiMandataria;
	}
	
	public abstract TipoOrgao getTipoOrgao();
	
	public long getValor() {
		return valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public static ModalidadeInstrumentoEnum fromValor(Long valor, Boolean possuiMandataria) {
		
		if(valor == null) {
			throw new IllegalArgumentException("Não foi encontrado o Enum: " + valor);
		}
		
		for (ModalidadeInstrumentoEnum modalidade : ModalidadeInstrumentoEnum.values()) {
			
			if(valor == ModalidadeInstrumentoEnum.TERMO_DE_COMPROMISSO_CONCEDENTE.getValor()) {
				if(possuiMandataria == null || !possuiMandataria) {
					return ModalidadeInstrumentoEnum.TERMO_DE_COMPROMISSO_CONCEDENTE;
				} else {
					return ModalidadeInstrumentoEnum.TERMO_DE_COMPROMISSO_MANDATARIA;
				}
			}
			
			if (modalidade.getValor() == valor) {
				return modalidade;
			}
		}
		throw new IllegalArgumentException("Não foi encontrado o Enum: " + valor);
	}

	@Override
	public String toString() {
		return this.name();
	}
	
	public boolean isPossuiMandataria() {
		return possuiMandataria;
	}

	public void setPossuiMandataria(boolean possuiMandataria) {
		this.possuiMandataria = possuiMandataria;
	}
}
