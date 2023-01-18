package br.gov.serpro.siconv.contratos.grpc.dto;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import br.gov.serpro.contratos.grpc.Submeta;
import lombok.Data;

@Data
public class SubmetaDTO {

	private Long id;
	private Long id_vrpl;
	private String numero;
	private String descricao;
	private BigDecimal valorTotal;
	@Nullable
	private String situacao;
	private String regimeExecucao;
	
	public SubmetaDTO(Submeta submetaGRPC) {		
		this.id = submetaGRPC.getId();
		this.id_vrpl = submetaGRPC.getIdVRPL();
		this.numero = submetaGRPC.getNumero();
		this.descricao = submetaGRPC.getDescricao();
		this.valorTotal = new BigDecimal(submetaGRPC.getValor());
		this.situacao = submetaGRPC.getSituacao();
		this.regimeExecucao = submetaGRPC.getRegimeExecucao();
	}
}
