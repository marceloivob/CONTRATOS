package br.gov.serpro.contratos.grpc.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import br.gov.serpro.contratos.grpc.NumeroContratoLicitacao;
 
public class NumeroContratoLicitacaoMapper implements RowMapper<NumeroContratoLicitacao>{
	@Override
	public NumeroContratoLicitacao map(ResultSet rs, StatementContext ctx) throws SQLException {
		String numeroContrato = rs.getString("numero");
 
		return NumeroContratoLicitacao.newBuilder().setNumeroContrato(numeroContrato).build();
	}
}
