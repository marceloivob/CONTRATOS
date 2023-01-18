package br.gov.economia.maisbrasil.contratos.repository.jdbi;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.stringtemplate4.UseStringTemplateSqlLocator;

import br.gov.economia.maisbrasil.contratos.domain.integracao.DocComplementarIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.HistoricoAcffoIntegracao;


public interface AcffoDAO {

	@SqlQuery
	@UseStringTemplateSqlLocator
	@RegisterFieldMapper(DocComplementarIntegracao.class)
	List<DocComplementarIntegracao> recuperarDocComplementarAmbiental(@Bind("idProp") Long idProp);
	
	@SqlQuery
	@UseStringTemplateSqlLocator
	@RegisterFieldMapper(HistoricoAcffoIntegracao.class)
	Optional<HistoricoAcffoIntegracao> recuperarHistoricoDeAceite(@Bind("idProp") Long idProp);
	
	
}
