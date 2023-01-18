package br.gov.economia.maisbrasil.contratos.domain.dto;

import lombok.Data;

@Data
public class ContratoAnexoDTO {
	public String arquivo; 
	public String nomeArquivo; 
	public String descricao; 
	public String tipoAnexo; 
	public Long tamanhoArquivo; 
	public Long versao;
}
