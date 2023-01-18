package br.gov.economia.maisbrasil.contratos.domain.integracao;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public class LoteIntegracao {
	
	private Long id;
	private Long licitacaoFk;
	private Long fornecedorFk;
	
	@NotNull
	private Long numeroLote;
    
    private String adtLogin;
    private Instant adtDataHora;
    private String adtOperacao;
    
    private Long versaoNr;
    private String versaoId;
    private String versaoNmEvento;

    @NotNull
    private Long versao;

}
