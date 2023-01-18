package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class SiconvRestConfig {

	@Getter
	@Value("${integrations.PRIVATE.REST.SICONV.endpoint}")
	private String endpoint;

	@Getter
	@Value("${integrations.PRIVATE.REST.SICONV.secretKey}")
	private String secretKey;

}
