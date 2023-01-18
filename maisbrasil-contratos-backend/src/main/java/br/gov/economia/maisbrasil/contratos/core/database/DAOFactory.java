package br.gov.economia.maisbrasil.contratos.core.database;

import org.jdbi.v3.core.Jdbi;

public interface DAOFactory {

	<T> T get(Class<T> clazz);

	Jdbi getJdbi();

	void setJdbi(Jdbi jdbi);

}