package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.util.Date;

import br.gov.economia.maisbrasil.contratos.domain.bd.PoBD;
import lombok.Data;

@Data
public class PoDTO {
    
	public Long id;
	public Long idPoVrpl;
	public Long submetaId;
	public Date dtPrevisaoInicioObra;
	public Long qtMesesDuracaoObra;
	public Boolean inAcompanhamentoEventos;
	public Boolean inDesonerado;
	public String sgLocalidade;
	public Date dtBaseAnalise;
	public String referencia;
	public Date dtBaseVrpl;
	public Date dtPrevisaoInicioObraAnalise;

	public Long versao;


    
    public PoBD converterParaBD() {
    	PoBD bd = new PoBD();
        bd.setId(this.id);
        bd.setIdPoVrpl(this.idPoVrpl);
        bd.setSubmetaId(submetaId);
        bd.setDtPrevisaoInicioObra(dtPrevisaoInicioObra);
        bd.setQtMesesDuracaoObra(qtMesesDuracaoObra);
        bd.setInAcompanhamentoEventos(inAcompanhamentoEventos);
        bd.setInDesonerado(inDesonerado);
        bd.setSgLocalidade(sgLocalidade);
        bd.setDtBaseAnalise(dtBaseAnalise);
        bd.setReferencia(referencia);
        bd.setDtBaseVrpl(dtBaseVrpl);
        bd.setDtPrevisaoInicioObraAnalise(dtPrevisaoInicioObraAnalise);
        bd.setVersao(versao);
        return bd;
    }
}
