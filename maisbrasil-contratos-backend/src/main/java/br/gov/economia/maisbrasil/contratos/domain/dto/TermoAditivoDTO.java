package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.gov.economia.maisbrasil.contratos.domain.bd.TermoAditivoBD;
import lombok.Data;

@Data
public class TermoAditivoDTO {
    
    public Long id;

    public Boolean inAlteracaoVigencia;

    public Boolean inAlteracaoClausula;

    public Boolean inAlteracaoObjeto;

    public String numero;

    public String dataAssinatura;

    public String dataPublicacao;

    public Long versao;
    
    public String adtLogin;

    public String adtOperacao;

    public Instant dataHoraAuditoria;

    public Long contratoId;

    public String inAlteracaoSupressaoAcrescimo;
    
    public String inSituacao;

    public BigDecimal valorSupressaoAcrescimo;
    
    public String novaDataFimVigencia;

    public String justificativa;

    public String descricaoAmpliacaoObjeto;
    
    public String tipoTermoAditivo;
    
    public List<ContratoAnexoDTO> contratoAnexos = new ArrayList<>();


    
    public TermoAditivoBD converterParaBD() {
    	
    	int endIndex = 10;
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	
    	TermoAditivoBD bd = new TermoAditivoBD();
        bd.setContratoId(this.contratoId);
        bd.setDtAssinatura(this.dataAssinatura != null ? LocalDate.parse(this.dataAssinatura.subSequence(0, endIndex), formatter) : null);
        bd.setDtPublicacao(this.dataPublicacao != null ? LocalDate.parse(this.dataPublicacao.subSequence(0, endIndex), formatter) : null);
        bd.setId(this.id);
        bd.setInAlteracaoClausula(this.inAlteracaoClausula);
        bd.setInAlteracaoObjeto(this.inAlteracaoObjeto);
        bd.setInAlteracaoVigencia(this.inAlteracaoVigencia);
        bd.setInAlteracaoSupressaoAcrescimo(this.inAlteracaoSupressaoAcrescimo);
        bd.setTxJustificativa(this.justificativa);
        bd.setDtNovaDataFimVigencia(this.novaDataFimVigencia != null ? LocalDate.parse(this.novaDataFimVigencia.subSequence(0, endIndex), formatter) : null);
        bd.setNumero(this.numero);
        bd.setVlSupressaoAcrescimo(this.valorSupressaoAcrescimo);
        bd.setTxDescricaoAmpliacaoObjeto(this.descricaoAmpliacaoObjeto);
        bd.setVersao(this.versao);
        bd.setInSituacao(this.inSituacao);
        return bd;
    }
    
    public void montarTipoTermoAditivo() {
    	String tipo = "";
    	
    	if (Boolean.TRUE.equals(this.inAlteracaoVigencia)) {
    		tipo += "Alteração de Vigência";
    	}
    	
    	if (Boolean.TRUE.equals(this.inAlteracaoClausula)) {
    		if (tipo.length() > 0) {
    			tipo += " / ";
    		}
    		
    		tipo += "Alteração de Cláusula";
    	}
    	
    	if (Boolean.TRUE.equals(this.inAlteracaoObjeto)) {
    		if (tipo.length() > 0) {
    			tipo += " / ";
    		}
    		
    		tipo += "Ampliação de Objeto";
    	}

    	this.setTipoTermoAditivo(tipo);
    }
}
