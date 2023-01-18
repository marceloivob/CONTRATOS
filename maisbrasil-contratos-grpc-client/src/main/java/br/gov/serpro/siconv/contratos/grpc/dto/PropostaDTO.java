package br.gov.serpro.siconv.contratos.grpc.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.gov.serpro.contratos.grpc.Proposta;
import lombok.Data;

@Data
public class PropostaDTO {

	private long id;	
	private String numeroConvenioRepasse;	
	private String anoConvenioRepasse;	
	private String objeto;	
	private String uf;	
	private long idSiconv;	
	private int modalidade;
	private Boolean possuiInstituicaoMandataria;
	private List<ContratoDTO> contratos = new ArrayList();
//	private List<LoteDTO> lotes = new ArrayList();
	private String nomeProponente;
	
	public PropostaDTO (Proposta propostaGRPC) {
		this.id = propostaGRPC.getId();
		this.numeroConvenioRepasse = propostaGRPC.getNumero();
		this.anoConvenioRepasse = propostaGRPC.getAno();
		this.objeto = propostaGRPC.getObjeto();
		this.uf = propostaGRPC.getUf();
		this.idSiconv = propostaGRPC.getIdSiconv();
		this.modalidade = propostaGRPC.getModalidade();
		this.possuiInstituicaoMandataria = propostaGRPC.getPossuiInstituicaoMandataria();
		this.contratos = propostaGRPC.getContratosList().stream().map(ContratoDTO::new).collect(Collectors.toList());
//		this.lotes = propostaGRPC.getLotesList().stream().map(LoteDTO::new).collect(Collectors.toList());
		this.nomeProponente = propostaGRPC.getNomeProponente();
	}

}
