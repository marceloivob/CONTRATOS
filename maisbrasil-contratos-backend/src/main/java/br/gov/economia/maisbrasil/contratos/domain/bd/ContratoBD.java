package br.gov.economia.maisbrasil.contratos.domain.bd;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnore;

import br.gov.economia.maisbrasil.contratos.domain.SituacaoContratoEnum;
import br.gov.economia.maisbrasil.contratos.domain.dto.ContratoDTO;
import lombok.Data;

@Data
public class ContratoBD {
    
    @NotNull
    private String numero;

    @NotNull
    private String inSituacao;

    @NotNull
    private String objeto;

    @NotNull
    private LocalDate fimVigencia;

    @NotNull
    private BigDecimal valorTotal;

    @NotNull
    private LocalDate dataAssinatura;

    private Long propostaId;

    private Long id;

    @NotNull
    private LocalDate dataPublicacao;

    @NotNull
    private LocalDate inicioVigencia;

    @NotNull
    private Long versao;
    
    @NotNull
    private LocalDate dtFimVigenciaOriginal;
    
	@JsonIgnore
//	@NotNull
	@Size(max = 60)
	private String adtLogin;

	@JsonIgnore
//	@NotNull
	private Instant adtDataHora;

	@JsonIgnore
//	@NotNull
	@Size(max = 6)
	private String adtOperacao;
	
	private Long propostaIdSiconv;

    public ContratoDTO converterParaDTO(){
    	
    	ContratoDTO contrato = new ContratoDTO();
    	contrato.setDataAssinatura(this.dataAssinatura.toString());
    	contrato.setDataPublicacao(this.dataPublicacao.toString());
    	contrato.setFimVigencia(this.fimVigencia.toString());
    	contrato.setInicioVigencia(this.inicioVigencia.toString());
    	contrato.setId(this.id);
    	contrato.setInSituacao(this.inSituacao);
    	contrato.setInSituacaoDescricao(SituacaoContratoEnum.fromSigla(this.inSituacao).getDescricao());
    	contrato.setNumero(this.numero);
    	contrato.setObjeto(this.objeto);
    	contrato.setPropostaId(this.propostaId);
    	contrato.setValorTotal(this.valorTotal);
    	contrato.setVersao(this.versao);
    	contrato.setPropostaIdSiconv(this.propostaIdSiconv);
    	contrato.setFimVigenciaOriginal(this.dtFimVigenciaOriginal.toString());
    	
    	return contrato;
    }
    
    
    public Long getDuracaoVigenciaEmMeses() {
    	
    	int mesInicioVigencia = this.getInicioVigencia().getMonthValue();
    	int anoInicioVigencia = this.getInicioVigencia().getYear();
    	
    	int mesFimVigencia = this.getFimVigencia().getMonthValue();
    	int anoFimVigencia = this.getFimVigencia().getYear();
    	
    	int retorno = (anoFimVigencia - anoInicioVigencia)*12 + (mesFimVigencia - mesInicioVigencia) + 1;
    	
    	return (long)retorno;
    }
}
