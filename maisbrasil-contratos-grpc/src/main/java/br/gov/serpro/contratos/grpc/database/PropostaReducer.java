package br.gov.serpro.contratos.grpc.database;

import java.util.Map;

import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

public class PropostaReducer implements LinkedHashMapRowReducer<Long, PropostaBD> {

	@Override
	public void accumulate(Map<Long, PropostaBD> container, RowView rowView) {
		PropostaBD proposta = container.computeIfAbsent(rowView.getColumn("prop_id", Long.class),
				id -> rowView.getRow(PropostaBD.class));

		ContratoBD contrato = null;
		if (rowView.getColumn("contrato_id", Long.class) == null) {
			contrato = new ContratoBD();
		} else {
			contrato = rowView.getRow(ContratoBD.class);

			contrato = proposta.addContrato(contrato);
		}

		LoteBD lote = null;
		if (rowView.getColumn("lote_id", Long.class) == null) {
			lote = new LoteBD();
		} else {
			lote = rowView.getRow(LoteBD.class);

			lote = contrato.addLote(lote);
		}

		SubmetaBD submeta = null;
		if (rowView.getColumn("submeta_id", Long.class) == null) {
			submeta = new SubmetaBD();
		} else {
			submeta = rowView.getRow(SubmetaBD.class);

			submeta = lote.addSubmeta(submeta);
		}
	}

}
