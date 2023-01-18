package br.gov.economia.maisbrasil.contratos.domain.bd;

import java.math.BigDecimal;
import java.time.Instant;

import javax.validation.constraints.NotNull;

import br.gov.economia.maisbrasil.contratos.domain.dto.SubmetaDTO;
import br.gov.economia.maisbrasil.contratos.domain.integracao.SubmetaIntegracao;
import lombok.Data;

@Data
public class SubmetaBD {
    
    @NotNull
    private String adtLogin;

    @NotNull
    private Long numero;

    private Long metaId;

    @NotNull
    private String inRegimeExecucao;

    @NotNull
    private String inSituacao;

    @NotNull
    private BigDecimal vlContrapartida;

    @NotNull
    private BigDecimal vlTotalLicitado;

    private Long propostaId;

    @NotNull
    private String adtOperacao;

    @NotNull
    private BigDecimal vlRepasse;

    @NotNull
    private BigDecimal vlOutros;

    private Long loteId;

    private Long id;

    @NotNull
    private Long versao;

    @NotNull
    private String descricao;

    
    private Instant dataHoraAuditoria;
    
    private Long idSubmetaVrpl;

    public SubmetaDTO converterParaDTO() {
    	SubmetaDTO dto = new SubmetaDTO();
        dto.setId(this.id);
        dto.setInRegimeExecucao(this.inRegimeExecucao);
        dto.setInSituacao(this.inSituacao);
        dto.setLoteId(this.loteId);
        dto.setMetaId(this.metaId);
        dto.setNumero(this.numero);
        dto.setPropostaId(this.propostaId);
        dto.setVersao(this.versao);
        dto.setVlContrapartida(this.vlContrapartida);
        dto.setVlOutros(this.vlOutros);
        dto.setVlRepasse(this.vlRepasse);
        dto.setVlTotalLicitado(this.vlTotalLicitado);
        dto.setDescricao(this.descricao);
        dto.setIdSubmetaVrpl(this.idSubmetaVrpl);
        
        return dto;
    }

	public static SubmetaBD from(SubmetaIntegracao submetaIntegracao) {
		SubmetaBD s = new SubmetaBD();
		s.setInRegimeExecucao(submetaIntegracao.getInRegimeExecucao());
		s.setInSituacao(submetaIntegracao.getInSituacao());
		s.setNumero(submetaIntegracao.getNrSubmetaAnalise());
		s.setVersao(submetaIntegracao.getVersao());
		s.setVlContrapartida(submetaIntegracao.getVlContrapartida());
		s.setVlOutros(submetaIntegracao.getVlOutros());
		s.setVlRepasse(submetaIntegracao.getVlRepasse());
		s.setVlTotalLicitado(submetaIntegracao.getVlTotalLicitado());
		s.setDescricao(submetaIntegracao.getTxDescricaoAnalise());
		s.setIdSubmetaVrpl(submetaIntegracao.getId());
		return s;
	}
}
