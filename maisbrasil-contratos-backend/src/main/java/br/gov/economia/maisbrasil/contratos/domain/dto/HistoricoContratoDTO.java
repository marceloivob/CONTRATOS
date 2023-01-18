package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.util.ArrayList;
import java.util.List;

import br.gov.economia.maisbrasil.contratos.domain.bd.ContratoBD;
import lombok.Data;

@Data
public class HistoricoContratoDTO {

	public Long idContrato;
	
	public String numeroContrato;
	
	public List<RegistroHistoricoContratoDTO> listaRegistros;
	
	public static HistoricoContratoDTO from(ContratoBD contrato) {
		HistoricoContratoDTO historico = new HistoricoContratoDTO();
		
		historico.setIdContrato(contrato.getId());
		historico.setNumeroContrato(contrato.getNumero());
		historico.listaRegistros = new ArrayList<>();
		
		return historico;
	}
	
}
