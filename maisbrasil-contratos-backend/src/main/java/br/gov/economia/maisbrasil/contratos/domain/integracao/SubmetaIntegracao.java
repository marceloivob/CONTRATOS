package br.gov.economia.maisbrasil.contratos.domain.integracao;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Data;

@Data
public class SubmetaIntegracao {
    
	private Long id;
	private Long idSubmetaAnalise;
	private Long propostaFk;
	private Long vrplLoteLicitacaoFk;
	private Long metaFk;
	private BigDecimal vlRepasse;
	private BigDecimal vlOutros;
	private Long nrSubmetaAnalise;
	private String txDescricaoAnalise;
	private Long nrLoteAnalise;
	private BigDecimal vlRepasseAnalise;
	private BigDecimal vlContrapartidaAnalise;
	private BigDecimal vlOutrosAnalise;
	private BigDecimal vlTotalAnalise;
	private String inSituacaoAnalise;
	private String inRegimeExecucaoAnalise;
	private Long naturezaDespesaSubItFkAnalise;
	private String adtLogin;
	private Instant adtDataHora;
	private String adtOperacao;
	private Long versaoNr;
	private String versaoId;
	private String versaoNmEvento;
	private Long versao;
	private String inRegimeExecucao;
	private BigDecimal vlContrapartida;
	private BigDecimal vlTotalLicitado;
	private String inSituacao;
	
}
