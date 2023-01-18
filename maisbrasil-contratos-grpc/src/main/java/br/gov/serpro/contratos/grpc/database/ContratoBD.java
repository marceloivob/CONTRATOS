package br.gov.serpro.contratos.grpc.database;

import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import lombok.Data;

@Data
public class ContratoBD {
	
	@ColumnName("id")
	private Long id;
	
	@ColumnName("numero")
	private String numero;
	
	@ColumnName("cnpjFornecedor")
	private String cnpj;
	
	@ColumnName("aptIniciar")
	private boolean aptIniciar;
	
	@ColumnName("porEventos")
	private boolean acompEventos;
	
	private List<LoteBD> lotes = new ArrayList<>();
	
	public LoteBD addLote(LoteBD loteBD) {
		int pos = this.lotes.indexOf(loteBD);
		if (pos == -1) {
			this.lotes.add(loteBD);
			return loteBD;
		}

		return this.lotes.get(pos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContratoBD other = (ContratoBD) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
