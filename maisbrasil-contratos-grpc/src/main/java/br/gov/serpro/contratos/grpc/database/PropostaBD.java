package br.gov.serpro.contratos.grpc.database;

import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import lombok.Data;

@Data
public class PropostaBD {

	@ColumnName("id")
	private Long id;
	
	@ColumnName("numero")
	private String numero;
	
	@ColumnName("ano")
	private String ano;
	
	@ColumnName("objeto")
	private String objeto;
	
	@ColumnName("uf")
	private String uf;
	
	@ColumnName("IdSiconv")
	private long idSiconv;
	
	@ColumnName("modalidade")
	private int modalidade;	
	
	@ColumnName("possuiInstituicaoMandataria")
	private Boolean possuiInstituicaoMandataria;
	
	@ColumnName("nomeProponente")
	private String nomeProponente;
	
	private List<ContratoBD> contratos = new ArrayList<>();
	
	public ContratoBD addContrato(ContratoBD contratoBD) {
		int pos = this.contratos.indexOf(contratoBD);
		if (pos == -1) {
			this.contratos.add(contratoBD);
			return contratoBD;
		}

		return this.contratos.get(pos);
	}

}
