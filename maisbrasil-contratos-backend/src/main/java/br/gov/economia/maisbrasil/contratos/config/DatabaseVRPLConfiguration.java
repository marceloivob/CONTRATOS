package br.gov.economia.maisbrasil.contratos.config;

import javax.sql.DataSource;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.guava.GuavaPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.gov.economia.maisbrasil.contratos.domain.bd.AnexoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.ContratoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.DocComplementarBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.FornecedorBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.HistoricoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.LicitacaoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.LoteBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.MetaBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.PropostaBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.SubmetaBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.SubmetaDocComplementarBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.TermoAditivoBD;

@Configuration
@EnableTransactionManagement
public class DatabaseVRPLConfiguration {

	@Bean
	@ConfigurationProperties("vrpl.datasource")
	public DataSourceProperties vrplDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource dataSourceForVRPL() {
		DataSource ds = vrplDataSourceProperties().initializeDataSourceBuilder().build();

		return ds;
	}

	@Bean
	@Qualifier("jdbiForVRPL")
	@ConfigurationProperties("vrpl.datasource")
	public Jdbi jdbiForVRPL() {
		TransactionAwareDataSourceProxy dataSourceProxy = new TransactionAwareDataSourceProxy(dataSourceForVRPL());
		Jdbi jdbi = Jdbi.create(dataSourceProxy);
		jdbi.installPlugins();
		jdbi.installPlugin(new SqlObjectPlugin());
		jdbi.installPlugin(new GuavaPlugin());

		jdbi.registerRowMapper(AnexoBD.class, ConstructorMapper.of(AnexoBD.class));
		jdbi.registerRowMapper(ContratoBD.class, ConstructorMapper.of(ContratoBD.class));
		jdbi.registerRowMapper(DocComplementarBD.class, ConstructorMapper.of(DocComplementarBD.class));
		jdbi.registerRowMapper(FornecedorBD.class, ConstructorMapper.of(FornecedorBD.class));
		jdbi.registerRowMapper(HistoricoBD.class, ConstructorMapper.of(HistoricoBD.class));
		jdbi.registerRowMapper(LicitacaoBD.class, ConstructorMapper.of(LicitacaoBD.class));
		jdbi.registerRowMapper(LoteBD.class, ConstructorMapper.of(LoteBD.class));
		jdbi.registerRowMapper(MetaBD.class, ConstructorMapper.of(MetaBD.class));
		jdbi.registerRowMapper(PropostaBD.class, ConstructorMapper.of(PropostaBD.class));
		jdbi.registerRowMapper(SubmetaBD.class, ConstructorMapper.of(SubmetaBD.class));
		jdbi.registerRowMapper(SubmetaDocComplementarBD.class, ConstructorMapper.of(SubmetaDocComplementarBD.class));
		jdbi.registerRowMapper(TermoAditivoBD.class, ConstructorMapper.of(TermoAditivoBD.class));

		return jdbi;
	}

}
