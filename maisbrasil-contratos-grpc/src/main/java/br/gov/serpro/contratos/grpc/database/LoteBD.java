package br.gov.serpro.contratos.grpc.database;

import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import lombok.Data;

@Data
public class LoteBD {

	@ColumnName("id")
	private Long id;
	
	@ColumnName("numero")
    private String numero;
    
    private List<SubmetaBD> submetas = new ArrayList<>();
    
    public SubmetaBD addSubmeta(SubmetaBD submetaBD) {
		int pos = this.submetas.indexOf(submetaBD);
		if (pos == -1) {
			this.submetas.add(submetaBD);
			return submetaBD;
		}

		return this.submetas.get(pos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoteBD other = (LoteBD) obj;
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
