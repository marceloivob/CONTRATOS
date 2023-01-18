package br.gov.economia.maisbrasil.contratos.domain.bd;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import br.gov.economia.maisbrasil.contratos.domain.dto.MetaDTO;
import br.gov.economia.maisbrasil.contratos.domain.integracao.MetaIntegracao;
import lombok.Data;

@Data
public class MetaBD {
    
    @NotNull
    private Long qtItens;

    @NotNull
    private String adtLogin;

    @NotNull
    private Long numero;

    @NotNull
    private Long idMetaVrpl;

    @NotNull
    private String txDescricao;

    @NotNull
    private String adtOperacao;

    private Long id;

    @NotNull
    private Boolean inSocial;

    @NotNull
    private Long versao;


    
    private Instant dataHoraAuditoria;

    public MetaDTO converterParaDTO() {
    	MetaDTO dto = new MetaDTO();
        dto.setId(this.id);
        dto.setIdMetaVrpl(this.idMetaVrpl);
        dto.setInSocial(this.inSocial);
        dto.setNumero(this.numero);
        dto.setQtItens(this.qtItens);
        dto.setTxDescricao(this.txDescricao);
        dto.setVersao(this.versao);
        return dto;
    }

	public static MetaBD from(MetaIntegracao metaIntegracao) {
		MetaBD m = new MetaBD();
		m.setIdMetaVrpl(metaIntegracao.getId());
		m.setInSocial(metaIntegracao.getInSocial());
		m.setNumero(metaIntegracao.getNrMetaAnalise());
		m.setQtItens(metaIntegracao.getQtItensAnalise());
		m.setTxDescricao(metaIntegracao.getTxDescricaoAnalise());
		m.setVersao(metaIntegracao.getVersao());
		return m;
	}
}
