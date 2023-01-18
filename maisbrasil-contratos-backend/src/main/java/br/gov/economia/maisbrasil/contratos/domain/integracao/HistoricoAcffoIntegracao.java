package br.gov.economia.maisbrasil.contratos.domain.integracao;

import java.util.Date;

import lombok.Data;

@Data
public class HistoricoAcffoIntegracao {
    
	private String in_evento;
	private String in_situacao;
	private Date dt_registro;
	
}
