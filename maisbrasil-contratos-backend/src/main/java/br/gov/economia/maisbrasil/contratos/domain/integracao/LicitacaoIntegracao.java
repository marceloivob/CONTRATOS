package br.gov.economia.maisbrasil.contratos.domain.integracao;

import java.time.Instant;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LicitacaoIntegracao {
	
	private Long id;
	
	private Long propostaFk;
    
    @NotNull
    private String numeroAno;
    
    @NotNull
    private String objeto;

    @NotNull
    private Long valorProcesso;

    @NotNull
    private String statusProcesso;

    @NotNull
    private Long idLicitacaoFk;
    
    @NotNull
    private String adtLogin;

    @NotNull
    private String adtOperacao;
    
    @NotNull
    private Instant adtDataHora;
        
    @NotNull
    private String inSituacao;
    
    private Long versaoNr;
    private String versaoId;
    private String versaoNmEvento;

    @NotNull
    private Long versao;
    
    private String modalidade;
    private String regimeContratacao;
    
    private LocalDate dataPublicacao;

    private LocalDate dataHomologacao;

    private String processoDeExecucao;
    
    @NotNull
    private Instant dataAceite;

    private String numeroProcesso;
    
    private String situacaoAceiteProcessoExecucao;

}
