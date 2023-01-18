package br.gov.economia.maisbrasil.contratos.core.cache.dao;

import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.stringtemplate4.UseStringTemplateSqlLocator;

public interface CacheDAO {

	@SqlUpdate
	@UseStringTemplateSqlLocator
	void definirUsuarioLogado(@Define("cpf") String cpf);

}
