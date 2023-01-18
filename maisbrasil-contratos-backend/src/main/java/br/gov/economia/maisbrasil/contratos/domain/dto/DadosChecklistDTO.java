package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.util.List;

import lombok.Data;

@Data
public class DadosChecklistDTO {
	
	public String situacaoAIO;
	public List<CheckDTO> checklist;
}
