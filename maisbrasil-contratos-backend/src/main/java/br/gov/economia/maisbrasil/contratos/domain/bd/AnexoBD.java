package br.gov.economia.maisbrasil.contratos.domain.bd;

import java.time.Instant;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnore;

import br.gov.economia.maisbrasil.contratos.domain.dto.AnexoDTO;
import lombok.Data;

@Data
public class AnexoBD {

	private Long contratoId;

	@NotNull
	private LocalDate dtUpload;

	@NotNull
	private String inPerfilUsuario;

	@NotNull
	private String nomeEnviadoPor;

	@NotNull
	private String bucket;

	@NotNull
	private String idCpfEnviadoPor;

	@NotNull
	private String inTipoAnexo;

	@NotNull
	@Size(max=100)
	private String txDescricao;

	@NotNull
	private String nmArquivo;

	private Long id;

	@NotNull
	private String caminho;

	private Long termoAditivoId;

	@NotNull
	private Long versao;

	@JsonIgnore
//	@NotNull
	@Size(max = 60)
	private String adtLogin;

	@JsonIgnore
//	@NotNull
	private Instant adtDataHora;

	@JsonIgnore
//	@NotNull
	@Size(max = 6)
	private String adtOperacao;
	
	
	
	public AnexoDTO converterParaDTO() {
    	AnexoDTO dto = new AnexoDTO();
        dto.setBucket(this.bucket);
        dto.setCaminho(this.caminho);
        dto.setContratoId(this.contratoId);
        dto.setDtUpload(this.dtUpload);
        dto.setId(this.id);
        dto.setIdCpfEnviadoPor(this.idCpfEnviadoPor);
        dto.setInPerfilUsuario(this.inPerfilUsuario);
        dto.setInTipoAnexo(this.inTipoAnexo);
        dto.setNmArquivo(this.nmArquivo);
        dto.setNomeEnviadoPor(this.nomeEnviadoPor);
        dto.setTermoAditivoId(this.termoAditivoId);
        dto.setTxDescricao(this.txDescricao);
        dto.setVersao(this.versao);
        
        if ("INSTRUMENTO_CONTRATUAL".equals(inTipoAnexo)) {
        	
        	dto.setTipoArquivoString("Instrumento Contratual");
        	
        } else if ("PUBLICACAO_EXTRATO".equals(inTipoAnexo)) {
        	
        	dto.setTipoArquivoString("Publicação do Extrato do Instrumento Contratual");
        	
        } else if ("OUTROS".equals(inTipoAnexo)) {
        	
        	dto.setTipoArquivoString("Outros");
        	
        } else if ("TERMO_ADITIVO".equals(inTipoAnexo)) {
        	
        	dto.setTipoArquivoString("Termo Aditivo");
        	
        } else if ("PUBLICACAO_TERMO_ADITIVO".equals(inTipoAnexo)) {
        	
        	dto.setTipoArquivoString("Publicação de Termo Aditivo");
        } 
        return dto;
    }
	
	
	

}
