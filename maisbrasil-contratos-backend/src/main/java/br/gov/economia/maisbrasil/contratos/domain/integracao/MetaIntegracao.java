package br.gov.economia.maisbrasil.contratos.domain.integracao;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MetaIntegracao {
	
	private Long id;
	
	@NotNull
    private Long idMetaAnalise;

    @NotNull
    private String txDescricaoAnalise;
    
    private Long nrMetaAnalise;
    
    @NotNull
    private Long qtItensAnalise;
    
    @NotNull
    private Boolean inSocial;

    @NotNull
    private String adtLogin;
    
    @NotNull
    private String adtOperacao;
    

    private Instant adtDataHora;
    
    private Long subitemFk;
    
    private Long versaoNr;
    private String versaoId;
    private String versaoNmEvento;
    
    @NotNull
    private Long versao;

}
