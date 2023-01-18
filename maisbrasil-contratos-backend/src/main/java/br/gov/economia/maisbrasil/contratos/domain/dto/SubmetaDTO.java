package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.math.BigDecimal;

import br.gov.economia.maisbrasil.contratos.domain.bd.SubmetaBD;
import lombok.Data;

@Data
public class SubmetaDTO {
    
    public Long numero;

    public Long metaId;

    public String inRegimeExecucao;

    public String inSituacao;

    public BigDecimal vlContrapartida;

    public BigDecimal vlTotalLicitado;

    public Long propostaId;

    public BigDecimal vlRepasse;

    public BigDecimal vlOutros;

    public Long loteId;

    public Long id;

    public Long versao;

    public PoDTO po;
    
    public MetaDTO meta;
    
    public String descricao;
    
    public String descricaoDetalhada;
    
    public String descricaoMeta;
    
    private Long idSubmetaVrpl;
    
    public SubmetaBD converterParaBD() {
    	SubmetaBD bd = new SubmetaBD();
        bd.setId(this.id);
        bd.setInRegimeExecucao(this.inRegimeExecucao);
        bd.setInSituacao(this.inSituacao);
        bd.setLoteId(this.loteId);
        bd.setMetaId(this.metaId);
        bd.setNumero(this.numero);
        bd.setPropostaId(this.propostaId);
        bd.setVersao(this.versao);
        bd.setVlContrapartida(this.vlContrapartida);
        bd.setVlOutros(this.vlOutros);
        bd.setVlRepasse(this.vlRepasse);
        bd.setVlTotalLicitado(this.vlTotalLicitado);
        bd.setDescricao(this.descricao);
        bd.setIdSubmetaVrpl(this.idSubmetaVrpl);
        return bd;
    }
    
    public void setMeta(MetaDTO meta) {
    	this.meta = meta;
    	this.descricaoMeta = meta.getNumero() + " - " + meta.getTxDescricao();
    	this.descricaoDetalhada = meta.getNumero() + "." + this.getNumero() + 
    			" - " + this.getDescricao();
    }
}
