package br.gov.serpro.contratos.grpc.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import br.gov.serpro.contratos.grpc.FornecedorContratos;

public class FornecedorContratosMapper implements RowMapper<FornecedorContratos> {

	@Override
	public FornecedorContratos map(ResultSet rs, StatementContext ctx) throws SQLException {
		String cnpj = rs.getString("cnpj");
		int quantidade = rs.getInt("quantidade");

		return FornecedorContratos.newBuilder().setCnpj(cnpj).setQtd(quantidade).build();
	}
}