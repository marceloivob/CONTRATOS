package br.gov.economia.maisbrasil.contratos.domain.integracao;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioIntegracao {

	private LocalDate dataAssinatura;
	private Integer sequencial;
	private Integer ano;

}
