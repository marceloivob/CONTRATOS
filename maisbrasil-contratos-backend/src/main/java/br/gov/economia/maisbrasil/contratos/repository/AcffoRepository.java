package br.gov.economia.maisbrasil.contratos.repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.integracao.DocComplementarIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.HistoricoAcffoIntegracao;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.AcffoDAO;

@Repository
@Transactional
public class AcffoRepository {

	@Autowired
	@Qualifier("jdbiForVRPL")
	private Jdbi jdbi;

	public List<DocComplementarIntegracao> recuperarDocComplementarAmbiental(Long idProp) {
		return jdbi.withHandle(transaction -> {
			List<DocComplementarIntegracao> docs = transaction.attach(AcffoDAO.class).recuperarDocComplementarAmbiental(idProp);
			return docs;
		});
	}
	
	public Optional<HistoricoAcffoIntegracao> recuperarHistoricoDeAceite(Long idProp) {
		return jdbi.withHandle(transaction -> {
			Optional<HistoricoAcffoIntegracao> hist = transaction.attach(AcffoDAO.class).recuperarHistoricoDeAceite(idProp);
			return hist;
		});
	}

}
