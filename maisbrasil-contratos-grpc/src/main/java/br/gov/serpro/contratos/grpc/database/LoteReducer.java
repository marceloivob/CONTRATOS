package br.gov.serpro.contratos.grpc.database;

import java.util.List;
import java.util.Map;

import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

public class LoteReducer implements LinkedHashMapRowReducer<Long, LoteBD> {

	@Override
	public void accumulate(Map<Long, LoteBD> container, RowView rowView) {
		LoteBD loteBD = container.computeIfAbsent(rowView.getColumn("lote_id", Long.class),
				id -> rowView.getRow(LoteBD.class));

		SubmetaBD submetaBD = null;
		if (rowView.getColumn("submeta_id", Long.class) == null) {
			submetaBD = new SubmetaBD();
		} else {
			submetaBD = rowView.getRow(SubmetaBD.class);

			submetaBD = loteBD.addSubmeta(submetaBD);
		}

	}

}
