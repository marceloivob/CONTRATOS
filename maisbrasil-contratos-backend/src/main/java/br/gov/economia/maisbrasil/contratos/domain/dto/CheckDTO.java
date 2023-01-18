package br.gov.economia.maisbrasil.contratos.domain.dto;

import br.gov.economia.maisbrasil.contratos.domain.StatusCheckEnum;
import lombok.Data;

@Data
public class CheckDTO {

	private Long indice;
	private String label;
	private String status;
	private String mensagemErro;
	private ItemDeVerificacaoParaEnum itemDeVerificacaoPara;

	public CheckDTO(ItemDeVerificacaoParaEnum itemDeVerificacaoPara) {
		this.itemDeVerificacaoPara = itemDeVerificacaoPara;
	}

	public boolean isPendente() {
		return (this.getStatus().equals(StatusCheckEnum.NOK.toString())
				|| this.getStatus().equals(StatusCheckEnum.INDISPONIVEL.toString()));
	}

}
