package br.gov.serpro.siconv.contratos.grpc.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.gov.serpro.contratos.grpc.Contrato;
import lombok.Data;

@Data
public class ContratoDTO {

	private long id;	
	private String numero;
	private String cnpj;
	private boolean aptIniciar;
	private boolean acompEventos;
	private List<LoteDTO> lotes = new ArrayList();
	
	public ContratoDTO(Contrato contratoGRPC) {
	
		this.id = contratoGRPC.getId();	
		this.numero = contratoGRPC.getNumero();
		this.cnpj = contratoGRPC.getCnpj();
		this.aptIniciar = contratoGRPC.getAptIniciar();
		this.acompEventos = contratoGRPC.getIsAcompEventos();
		this.lotes = contratoGRPC.getLotesList().stream().map(LoteDTO::new).collect(Collectors.toList());
		
	}
	
}
