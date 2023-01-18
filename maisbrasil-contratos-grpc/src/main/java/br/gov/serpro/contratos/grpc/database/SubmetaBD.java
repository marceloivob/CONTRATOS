package br.gov.serpro.contratos.grpc.database;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import lombok.Data;

@Data
public class SubmetaBD {

	@ColumnName("id")
    private Long id;
	
	@ColumnName("idVRPL")
	private Long idVRPL;
    
	@ColumnName("numero")
    private String numero;
	
	@ColumnName("descricao")
    private String descricao;
	
	@ColumnName("valorTotal")
    private String valorTotal;
	
	@ColumnName("situacao")
    private String situacao;

	@ColumnName("regimeExecucao")
	private RegimeExecucaoEnum regimeExecucao;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubmetaBD other = (SubmetaBD) obj;
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


