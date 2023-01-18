package br.gov.economia.maisbrasil.contratos.domain.integracao;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import br.gov.economia.maisbrasil.contratos.domain.dto.FornecedorDTO;
import lombok.Data;

@Data
public class FornecedorIntegracao {
    
    @NotNull
    private String adtLogin;
    
    @NotNull
    private Long licitacaoFk;

    @NotNull
    private String identificacao;

    @NotNull
    private String tipoIdentificacao;

    @NotNull
    private String adtOperacao;

    @NotNull
    private String razaoSocial;

    private Long id;

    @NotNull
    private Long versao;


    
    private Instant dataHoraAuditoria;

    public FornecedorDTO converterParaDTO() {
    	FornecedorDTO dto = new FornecedorDTO();
        dto.setId(this.id);
        dto.setIdentificacao(this.identificacao);
        dto.setRazaoSocial(this.razaoSocial);
        dto.setTipoIdentificacao(this.tipoIdentificacao);
        dto.setVersao(this.versao);
        return dto;
    }
}
