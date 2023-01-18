package br.gov.economia.maisbrasil.contratos.domain.bd;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import br.gov.economia.maisbrasil.contratos.domain.dto.LoteDTO;
import br.gov.economia.maisbrasil.contratos.domain.integracao.LoteIntegracao;
import lombok.Data;

@Data
public class LoteBD {
    
    private Long contratoId;

    @NotNull
    private String adtLogin;

    @NotNull
    private Long numero;

    private Long fornecedorId;

    @NotNull
    private String adtOperacao;

    private Long id;

    @NotNull
    private Long versao;

    private Long licitacaoId;
    
    @Transient
    private Long fornecedorVrplFk; // para migracao


    
    private Instant dataHoraAuditoria;

    public LoteDTO converterParaDTO() {
    	LoteDTO dto = new LoteDTO();
        dto.setContratoId(this.contratoId);
        dto.setFornecedorId(this.fornecedorId);
        dto.setId(this.id);
        dto.setLicitacaoId(this.licitacaoId);
        dto.setNumero(this.numero);
        dto.setVersao(this.versao);
        return dto;
    }

	public static LoteBD from(LoteIntegracao loteIntegracao) {
		LoteBD l = new LoteBD();
		l.setNumero(loteIntegracao.getNumeroLote());
		l.setVersao(loteIntegracao.getVersao());
		l.setFornecedorVrplFk(loteIntegracao.getFornecedorFk());
		return l;
	}
}
