package br.gov.economia.maisbrasil.contratos.domain.bd;

import java.time.Instant;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import br.gov.economia.maisbrasil.contratos.domain.dto.DocComplementarDTO;
import lombok.Data;

@Data
public class DocComplementarBD {
    
    @NotNull
    private String tipo;

    @NotNull
    private String adtLogin;

    @NotNull
    private String orgaoEmissor;

    @NotNull
    private String numeroDocumento;

    private Long anexoId;

    @NotNull
    private String adtOperacao;

    @NotNull
    private LocalDate dataValidade;

    private Long id;

    private String tipoManifestoAmbiental;

    @NotNull
    private LocalDate dataEmissao;

    @NotNull
    private Long versao;


    
    private Instant dataHoraAuditoria;

    public DocComplementarDTO converterParaDTO() {
    	DocComplementarDTO dto = new DocComplementarDTO();
        dto.setAnexoId(this.anexoId);
        dto.setDataEmissao(this.dataEmissao);
        dto.setDataValidade(this.dataValidade);
        dto.setId(this.id);
        dto.setNumeroDocumento(this.numeroDocumento);
        dto.setOrgaoEmissor(this.orgaoEmissor);
        dto.setTipo(this.tipo);
        dto.setTipoManifestoAmbiental(this.tipoManifestoAmbiental);
        dto.setVersao(this.versao);
        return dto;
    }
}
