package br.gov.serpro.contratos.grpc.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import br.gov.serpro.contratos.grpc.Submeta;

public class SubmetaMapper implements RowMapper<Submeta> {

	@Override
	public Submeta map(ResultSet rs, StatementContext ctx) throws SQLException {
		// TODO Auto-generated method stub
		long id = rs.getLong(1);
		long id_vrpl = rs.getLong(2);
		String numero = rs.getString(3);
		String descricao = rs.getString(4);
		String valorTotal = rs.getString(5);
		
		return Submeta.newBuilder().setId(id).setIdVRPL(id_vrpl).setNumero(numero).setDescricao(descricao).setValor(valorTotal).build();
	}

	
	
}
