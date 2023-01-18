package br.gov.serpro.siconv.contratos.grpc.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.gov.serpro.contratos.grpc.Lote;
import lombok.Data;

@Data
public class LoteDTO {
	
	
	private long Id;	
    private String numero;    
    private List<SubmetaDTO> submetas = new ArrayList();
    
    public LoteDTO(Lote loteGRPC) {
    	this.Id = loteGRPC.getId();
    	this.numero = loteGRPC.getNumero();
    	this.submetas = loteGRPC.getSubmetasList().stream().map(SubmetaDTO::new).collect(Collectors.toList());    	
    }
}
