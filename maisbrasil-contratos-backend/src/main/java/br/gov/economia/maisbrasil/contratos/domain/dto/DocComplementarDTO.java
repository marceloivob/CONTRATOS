package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.time.LocalDate;

import br.gov.economia.maisbrasil.contratos.domain.bd.DocComplementarBD;
import lombok.Data;

@Data
public class DocComplementarDTO {
    
    public String tipo;

    public String adtLogin;

    public String orgaoEmissor;

    public String numeroDocumento;

    public Long anexoId;

    public String adtOperacao;

    public LocalDate dataValidade;

    public Long id;

    public String tipoManifestoAmbiental;

    public LocalDate dataEmissao;

    public Long versao;


    
    public DocComplementarBD converterParaBD() {
    	DocComplementarBD bd = new DocComplementarBD();
        bd.setAnexoId(this.anexoId);
        bd.setDataEmissao(this.dataEmissao);
        bd.setDataValidade(this.dataValidade);
        bd.setId(this.id);
        bd.setNumeroDocumento(this.numeroDocumento);
        bd.setOrgaoEmissor(this.orgaoEmissor);
        bd.setTipo(this.tipo);
        bd.setTipoManifestoAmbiental(this.tipoManifestoAmbiental);
        bd.setVersao(this.versao);
        return bd;
    }
}
