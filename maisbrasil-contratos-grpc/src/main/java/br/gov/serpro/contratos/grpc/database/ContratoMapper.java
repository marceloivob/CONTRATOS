package br.gov.serpro.contratos.grpc.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import br.gov.serpro.contratos.grpc.ContratoDetalhado;

public class ContratoMapper implements RowMapper<ContratoDetalhado>{
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
	
	@Override
	public ContratoDetalhado map(ResultSet rs, StatementContext ctx) throws SQLException {		
		
		long id = rs.getLong(1);
	    String numero = rs.getString(2);
	    String cnpjFornecedor = rs.getString(3);
	    boolean isTrabSocial = rs.getBoolean(4);
	    boolean isAcompEventos = rs.getBoolean(5);	    
	    String dtInicioVigencia  = sdf.format(rs.getDate(6));	    
	    String dtFimVigencia = sdf.format(rs.getDate(7)); 
	    String dtAssinatura = sdf.format(rs.getDate(8));
	    String objeto = rs.getString(9);
	    String valorTotal = rs.getString(10);
	    String valorTipoInstrumento = rs.getString(11);
	    long idSiconv = rs.getLong(12); 
	    String localidade = rs.getString(13);
	    int modalidade = rs.getInt(14);
	    long numeroTipoInstrumento = rs.getLong(15);
	    long anoTipoInstrumento = rs.getLong(16);
	    String objetoTipoInstrumento = rs.getString(17);
	    String dtAssinaturaTipoInstrumento = sdf.format(rs.getDate(18));
	    boolean possuiInstituicaoMandataria = rs.getBoolean(19);
	    String nomeProponente = rs.getString(20);
		
		
		return ContratoDetalhado.newBuilder().setId(id).setNumero(numero).setCnpjFornecedor(cnpjFornecedor)
				.setIsTrabSocial(isTrabSocial).setIsAcompEventos(isAcompEventos).setDtInicioVigencia(dtInicioVigencia)
				.setDtFimVigencia(dtFimVigencia).setDtAssinatura(dtAssinatura).setObjeto(objeto)
				.setValorTotal(valorTotal).setValotTipoInstrumento(valorTipoInstrumento)
				.setIdSiconv(idSiconv).setLocalidade(localidade)
				.setModalidade(modalidade).setNumeroTipoInstrumento(numeroTipoInstrumento)
				.setPossuiInstituicaoMandataria(possuiInstituicaoMandataria)
				.setAnoTipoInstrumento(anoTipoInstrumento).setObjetoTipoInstrumento(objetoTipoInstrumento)
				.setDtAssinaturaTipoInstrumento(dtAssinaturaTipoInstrumento)
				.setNomeProponente(nomeProponente)
				.build();
	}

}
