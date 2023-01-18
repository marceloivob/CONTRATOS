package br.gov.economia.maisbrasil.contratos.domain.integracao;

import java.time.Instant;
import java.util.Date;

import lombok.Data;

@Data
public class PoIntegracao {
    
	private Long id;
	private Long idPoAnalise;
	private Long submetaFk;
	private Date dtPrevisaoInicioObra;
	private Long qtMesesDuracaoObra;
	private Boolean inAcompanhamentoEventos;
	private Boolean inDesonerado;
	private String sgLocalidade;
	private Date dtBaseAnalise;
	private String referencia;
	private Date dtBaseVrpl;
	private Date dtPrevisaoInicioObraAnalise;
	
	private String adtLogin;
	private Instant adtDataHora;
	private String adtOperacao;
	private Long versaoNr;
	private String versaoId;
	private String versaoNmEvento;
	private Long versao;

}
