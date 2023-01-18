package br.gov.economia.maisbrasil.contratos.domain.bd;

import java.time.Instant;
import java.time.LocalDate;

import br.gov.economia.maisbrasil.contratos.domain.dto.SubmetaDocComplementarDTO;
import lombok.Data;

@Data
public class SubmetaDocComplementarBD {
    
    private LocalDate dataAssociacao;

    private Long id;

    private Long submetaId;

    private Long docComplementarId;


    
    private Instant dataHoraAuditoria;

    public SubmetaDocComplementarDTO converterParaDTO() {
    	SubmetaDocComplementarDTO dto = new SubmetaDocComplementarDTO();
        dto.setDataAssociacao(this.dataAssociacao);
        dto.setDocComplementarId(this.docComplementarId);
        dto.setId(this.id);
        dto.setSubmetaId(this.submetaId);
        return dto;
    }
}
