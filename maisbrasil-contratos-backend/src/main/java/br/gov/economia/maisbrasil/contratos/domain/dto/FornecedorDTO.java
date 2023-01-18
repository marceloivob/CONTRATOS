package br.gov.economia.maisbrasil.contratos.domain.dto;

import br.gov.economia.maisbrasil.contratos.domain.bd.FornecedorBD;
import lombok.Data;

@Data
public class FornecedorDTO {
    

    public String identificacao;

    public String tipoIdentificacao;

    public String razaoSocial;

    public Long id;

    public Long versao;

    public FornecedorBD converterParaBD() {
    	FornecedorBD bd = new FornecedorBD();
        bd.setId(this.id);
        bd.setIdentificacao(this.identificacao);
        bd.setRazaoSocial(this.razaoSocial);
        bd.setTipoIdentificacao(this.tipoIdentificacao);
        bd.setVersao(this.versao);
        return bd;
    }
}
