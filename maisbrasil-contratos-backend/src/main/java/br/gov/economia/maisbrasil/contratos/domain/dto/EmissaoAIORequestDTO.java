package br.gov.economia.maisbrasil.contratos.domain.dto;

import lombok.Data;

@Data
public class EmissaoAIORequestDTO {
	protected Long idContrato;

	protected String dataEmissaoAIO;

	protected String justificativa;
}
