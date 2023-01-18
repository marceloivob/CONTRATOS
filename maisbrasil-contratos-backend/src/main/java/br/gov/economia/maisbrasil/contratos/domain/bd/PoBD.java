package br.gov.economia.maisbrasil.contratos.domain.bd;

import java.time.Instant;
import java.util.Date;

import javax.validation.constraints.NotNull;

import br.gov.economia.maisbrasil.contratos.domain.dto.PoDTO;
import br.gov.economia.maisbrasil.contratos.domain.integracao.PoIntegracao;
import lombok.Data;

@Data
public class PoBD {
    
	@NotNull
	private Long id;
	
	@NotNull
	private Long idPoVrpl;
	
	@NotNull
	private Long submetaId;
	
	@NotNull
	private Date dtPrevisaoInicioObra;
	
	@NotNull
	private Long qtMesesDuracaoObra;
	private Boolean inAcompanhamentoEventos;
	private Boolean inDesonerado;
	private String sgLocalidade;
	
	@NotNull
	private Date dtBaseAnalise;
	private String referencia;
	private Date dtBaseVrpl;
	
	@NotNull
	private Date dtPrevisaoInicioObraAnalise;
    
    
    
    @NotNull
    private String adtLogin;

    @NotNull
    private String adtOperacao;

    @NotNull
    private Long versao;

    private Instant dataHoraAuditoria;

    public PoDTO converterParaDTO() {
    	PoDTO dto = new PoDTO();
        dto.setId(this.id);
        dto.setIdPoVrpl(this.idPoVrpl);
        dto.setSubmetaId(this.submetaId);
        dto.setDtPrevisaoInicioObra(this.dtPrevisaoInicioObra);
        dto.setQtMesesDuracaoObra(this.qtMesesDuracaoObra);
        dto.setInAcompanhamentoEventos(this.inAcompanhamentoEventos);
        dto.setInDesonerado(this.inDesonerado);
        dto.setSgLocalidade(this.sgLocalidade);
        dto.setDtBaseAnalise(this.dtBaseAnalise);
        dto.setReferencia(this.referencia);
        dto.setDtBaseVrpl(this.dtBaseVrpl);
        dto.setDtPrevisaoInicioObraAnalise(this.dtPrevisaoInicioObraAnalise);
        dto.setVersao(this.versao);
        return dto;
    }

	public static PoBD from(PoIntegracao poIntegracao) {
		PoBD p = new PoBD();
		p.setDtBaseAnalise(poIntegracao.getDtBaseAnalise());
		p.setDtBaseVrpl(poIntegracao.getDtBaseVrpl());
		p.setDtPrevisaoInicioObra(poIntegracao.getDtPrevisaoInicioObra());
		p.setDtPrevisaoInicioObraAnalise(poIntegracao.getDtPrevisaoInicioObraAnalise());
		p.setIdPoVrpl(poIntegracao.getId());
		p.setInAcompanhamentoEventos(poIntegracao.getInAcompanhamentoEventos());
		p.setInDesonerado(poIntegracao.getInDesonerado());
		p.setQtMesesDuracaoObra(poIntegracao.getQtMesesDuracaoObra());
		p.setReferencia(poIntegracao.getReferencia());
		p.setSgLocalidade(poIntegracao.getSgLocalidade());
		p.setVersao(poIntegracao.getVersao());
		return p;
	}
}
