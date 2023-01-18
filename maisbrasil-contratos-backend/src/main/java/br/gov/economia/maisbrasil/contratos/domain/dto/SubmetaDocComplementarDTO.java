package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.time.LocalDate;

import br.gov.economia.maisbrasil.contratos.domain.bd.SubmetaDocComplementarBD;
import lombok.Data;

@Data
public class SubmetaDocComplementarDTO {
    
    public LocalDate dataAssociacao;

    public Long id;

    public Long submetaId;

    public Long docComplementarId;


    
    public SubmetaDocComplementarBD converterParaBD() {
    	SubmetaDocComplementarBD bd = new SubmetaDocComplementarBD();
        bd.setDataAssociacao(this.dataAssociacao);
        bd.setDocComplementarId(this.docComplementarId);
        bd.setId(this.id);
        bd.setSubmetaId(this.submetaId);
        return bd;
    }
}
