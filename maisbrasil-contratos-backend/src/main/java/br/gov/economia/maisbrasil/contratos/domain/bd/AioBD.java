package br.gov.economia.maisbrasil.contratos.domain.bd;

import java.time.LocalDate;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import lombok.Data;

/**
 * Autorização de Início do Objeto
 */
@Data
public class AioBD {

	private Long id;

	private Long propostaFk;

	private Long contratoEmissorFk;

	@ColumnName(value = "dt_emissao_aio")
	private LocalDate dataEmissaoAio;

	private Long versao;

	public boolean isAIOEmitido() {
		return (this.getDataEmissaoAio() != null);
	}

	/**
	 * Construtor Padrão
	 */
	public AioBD() {
		// noop
	}

	public AioBD(Long contratoId, Long propostaID) {
		this.propostaFk = propostaID;
		this.contratoEmissorFk = contratoId;
	}

}
