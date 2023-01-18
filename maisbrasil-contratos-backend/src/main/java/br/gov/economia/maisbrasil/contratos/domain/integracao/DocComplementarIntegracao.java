package br.gov.economia.maisbrasil.contratos.domain.integracao;

import java.util.Date;

import lombok.Data;

@Data
public class DocComplementarIntegracao {
    
	private Long id;
	private String txOrgaoEmissor;
	private Date dtEmissao;
	private Date dtValidaAte;
	private String nrDocumento;
	private Long tpManifAmbientalFk;
	private Long tpDocComplementarFk;
	
	
	private Long nrVersaoHibernate;
	private Long anexoFk;
	private Long acffoFk;
	private Long versaoNr;
	private String versaoId;
	private String versaoNmEvento;

}
