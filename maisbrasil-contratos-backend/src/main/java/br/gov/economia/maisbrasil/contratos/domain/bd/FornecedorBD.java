package br.gov.economia.maisbrasil.contratos.domain.bd;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import br.gov.economia.maisbrasil.contratos.domain.dto.FornecedorDTO;
import br.gov.economia.maisbrasil.contratos.domain.integracao.FornecedorIntegracao;
import lombok.Data;

@Data
public class FornecedorBD {
    
    @NotNull
    private String adtLogin;

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
    
    @Transient
    private Long licitacaoFk; // Informação para a migração


    
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

	public static FornecedorBD from(FornecedorIntegracao fornecedorIntegracao) {
		FornecedorBD f = new FornecedorBD();
		f.setIdentificacao(fornecedorIntegracao.getIdentificacao());
		f.setRazaoSocial(fornecedorIntegracao.getRazaoSocial());
		f.setTipoIdentificacao(fornecedorIntegracao.getTipoIdentificacao());
		f.setVersao(fornecedorIntegracao.getVersao());
		f.setLicitacaoFk(fornecedorIntegracao.getLicitacaoFk());
		return f;
	}
}
