package br.gov.economia.maisbrasil.contratos.core.database;

import static br.gov.economia.maisbrasil.contratos.core.database.DataSourceType.CONTRATO;
import static br.gov.economia.maisbrasil.contratos.core.database.DataSourceType.VRPL;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlLogger;
import org.jdbi.v3.guava.GuavaPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import br.gov.economia.maisbrasil.contratos.config.CloudDatabaseConfiguration;;

@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JDBIProducer {

	@Autowired
	private CloudDatabaseConfiguration config;

	@Autowired
	private SqlLogger sqlLogger;

	@DataSourceFor(VRPL)
	public Jdbi createJdbiForVRPL() {
		Jdbi jdbi = Jdbi.create(config.dataSourceVRPL());
		jdbi.installPlugin(new SqlObjectPlugin());
		jdbi.installPlugin(new GuavaPlugin());
		jdbi.setSqlLogger(sqlLogger);

		return jdbi;
	}

	@DataSourceFor(CONTRATO)
	public Jdbi createJdbiForContrato() {
		Jdbi jdbi = Jdbi.create(config.dataSourceContrato());
		jdbi.installPlugin(new SqlObjectPlugin());
		jdbi.installPlugin(new GuavaPlugin());
		jdbi.setSqlLogger(sqlLogger);

		return jdbi;
	}

	public SqlLogger getSqlLogger() {
		return sqlLogger;
	}

	public void setSqlLogger(SqlLogger sqlLogger) {
		this.sqlLogger = sqlLogger;
	}

}
