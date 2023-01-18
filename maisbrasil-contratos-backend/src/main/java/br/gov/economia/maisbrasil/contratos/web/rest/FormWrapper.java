package br.gov.economia.maisbrasil.contratos.web.rest;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FormWrapper {
	public MultipartFile arquivo; 
	public String nomeArquivo; 
	public String descricao; 
	public String tipoAnexo; 
	public Long tamanhoArquivo; 
	public Long versao;
}
