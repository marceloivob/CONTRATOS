package br.gov.economia.maisbrasil.contratos.domain.bd;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import br.gov.economia.maisbrasil.contratos.domain.dto.TermoAditivoDTO;
import lombok.Data;

@Data
public class TermoAditivoBD {

    private Long id;

    @NotNull
    private Boolean inAlteracaoVigencia;

    @NotNull
    private Boolean inAlteracaoClausula;

    @NotNull
    private Boolean inAlteracaoObjeto;

    @NotNull
    private String numero;

    private LocalDate dtAssinatura;

    private LocalDate dtPublicacao;

    @NotNull
    private Long versao;
    
    @NotNull
    private String adtLogin;

    @NotNull
    private String adtOperacao;
    
    @NotNull
    private String inSituacao;

    private Instant dataHoraAuditoria;

    private Long contratoId;

    private String inAlteracaoSupressaoAcrescimo;

    private BigDecimal vlSupressaoAcrescimo;
    
    private LocalDate dtNovaDataFimVigencia;

    private String txJustificativa;

    private String txDescricaoAmpliacaoObjeto;
    

    public TermoAditivoDTO converterParaDTO() {
    	TermoAditivoDTO dto = new TermoAditivoDTO();
        dto.setContratoId(this.contratoId);
        dto.setDataAssinatura(this.dtAssinatura != null ? this.dtAssinatura.toString() : null);
        dto.setDataPublicacao(this.dtPublicacao != null ? this.dtPublicacao.toString() : null);
        dto.setId(this.id);
        dto.setInAlteracaoClausula(this.inAlteracaoClausula);
        dto.setInAlteracaoObjeto(this.inAlteracaoObjeto);
        dto.setInAlteracaoVigencia(this.inAlteracaoVigencia);
        dto.setInAlteracaoSupressaoAcrescimo(this.inAlteracaoSupressaoAcrescimo);
        dto.setJustificativa(this.txJustificativa);
        dto.setNovaDataFimVigencia(this.dtNovaDataFimVigencia != null ? this.dtNovaDataFimVigencia.toString() : null);
        dto.setNumero(this.numero);
        dto.setValorSupressaoAcrescimo(this.vlSupressaoAcrescimo);
        dto.setDescricaoAmpliacaoObjeto(this.txDescricaoAmpliacaoObjeto);
        dto.setVersao(this.versao);
        dto.setInSituacao(this.inSituacao);
        dto.montarTipoTermoAditivo();
        return dto;
    }
}
