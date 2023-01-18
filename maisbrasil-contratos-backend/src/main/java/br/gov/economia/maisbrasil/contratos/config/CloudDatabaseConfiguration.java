package br.gov.economia.maisbrasil.contratos.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.gov.economia.maisbrasil.contratos.core.database.ForContratos;
import br.gov.economia.maisbrasil.contratos.core.database.ForVRPL;
import io.github.jhipster.config.JHipsterConstants;


@Configuration
@Profile(JHipsterConstants.SPRING_PROFILE_CLOUD)
public class CloudDatabaseConfiguration extends AbstractCloudConfig {

    private final Logger log = LoggerFactory.getLogger(CloudDatabaseConfiguration.class);
    private static final String CLOUD_CONFIGURATION_VRPL_PREFIX = "vrpl.datasource";
    private static final String CLOUD_CONFIGURATION_CONTRATO_PREFIX = "spring.datasource";

    @Bean
	@ForVRPL
    @ConfigurationProperties(CLOUD_CONFIGURATION_VRPL_PREFIX)
    public DataSource dataSourceVRPL() {
        log.info("Configuring JDBC VRPL datasource from a cloud provider");
        return connectionFactory().dataSource();
    }
    
    @Bean
	@ForContratos
    @ConfigurationProperties(CLOUD_CONFIGURATION_CONTRATO_PREFIX)
    public DataSource dataSourceContrato() {
        log.info("Configuring JDBC Contrato datasource from a cloud provider");
        return connectionFactory().dataSource();
    }
}
