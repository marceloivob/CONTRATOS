package br.gov.economia.maisbrasil.contratos.domain.dto;

import br.gov.economia.maisbrasil.contratos.domain.bd.MetaBD;
import lombok.Data;

@Data
public class MetaDTO {
    
    public Long qtItens;

    public Long numero;

    public Long idMetaVrpl;

    public String txDescricao;

    public Long id;

    public Boolean inSocial;

    public Long versao;


    
    public MetaBD converterParaBD() {
    	MetaBD bd = new MetaBD();
        bd.setId(this.id);
        bd.setIdMetaVrpl(this.idMetaVrpl);
        bd.setInSocial(this.inSocial);
        bd.setNumero(this.numero);
        bd.setQtItens(this.qtItens);
        bd.setTxDescricao(this.txDescricao);
        bd.setVersao(this.versao);
        return bd;
    }
}
