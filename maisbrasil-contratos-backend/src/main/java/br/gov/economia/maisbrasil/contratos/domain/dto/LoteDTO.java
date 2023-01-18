package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.gov.economia.maisbrasil.contratos.domain.bd.LoteBD;
import lombok.Data;

@Data
public class LoteDTO {
    
    public Long contratoId;

    public Long numero;

    public Long fornecedorId;

    public Long id;

    public Long versao;

    public Long licitacaoId;
    
    public BigDecimal somatorioSubmetas;
    
    public List<SubmetaDTO> submetas = new ArrayList<>();

    public LoteBD converterParaBD() {
    	LoteBD bd = new LoteBD();
        bd.setContratoId(this.contratoId);
        bd.setFornecedorId(this.fornecedorId);
        bd.setId(this.id);
        bd.setLicitacaoId(this.licitacaoId);
        bd.setNumero(this.numero);
        bd.setVersao(this.versao);
        return bd;
    }
    
    public boolean possuiMesmoFornecedorELicitacao(LoteDTO outro) {
        return this.getFornecedorId().equals(outro.getFornecedorId()) &&
    			this.getLicitacaoId().equals(outro.getLicitacaoId());
    }
    
}
