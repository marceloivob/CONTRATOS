package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.gov.economia.maisbrasil.contratos.domain.bd.AnexoBD;
import lombok.Data;

@Data
public class AnexoDTO {
	
	// Usado para gerar o link para Download
	@JsonIgnore
	private String key;
	
	private String linkToDownload;
	
    public Long contratoId;

    public LocalDate dtUpload;

    public String inPerfilUsuario;

    public String nomeEnviadoPor;

    public String bucket;

    public String idCpfEnviadoPor;

    public String inTipoAnexo;

    public String txDescricao;

    public String nmArquivo;

    public Long id;

    public String caminho;

    public Long termoAditivoId;

    public Long versao;
    
    private String tipoArquivoString;


    public AnexoBD converterParaBD() {
    	AnexoBD bd = new AnexoBD();
        bd.setBucket(this.bucket);
        bd.setCaminho(this.caminho);
        bd.setContratoId(this.contratoId);
        bd.setDtUpload(this.dtUpload);
        bd.setId(this.id);
        bd.setIdCpfEnviadoPor(this.idCpfEnviadoPor);
        bd.setInPerfilUsuario(this.inPerfilUsuario);
        bd.setInTipoAnexo(this.inTipoAnexo);
        bd.setNmArquivo(this.nmArquivo);
        bd.setNomeEnviadoPor(this.nomeEnviadoPor);
        bd.setTermoAditivoId(this.termoAditivoId);
        bd.setTxDescricao(this.txDescricao);
        bd.setVersao(this.versao);
        return bd;
    }
}
