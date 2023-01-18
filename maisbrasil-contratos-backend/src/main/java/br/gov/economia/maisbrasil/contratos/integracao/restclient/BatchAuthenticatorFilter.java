package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Fonte:
 * http://www.adam-bien.com/roller/abien/entry/client_side_http_basic_access
 */
@Slf4j
@Component
@RequestScope
public class BatchAuthenticatorFilter implements ClientRequestFilter {

	@Autowired
	private BasicAuthenticatorHeaderGenerator basicAuthenticatorHeaderGenerator;

	public static final String SISTEMA_BATCH = "maisbrasil-contratos-batch";
	
	/**
	 * Construtor Padrão para o CDI
	 */
	public BatchAuthenticatorFilter() {
		// noop
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		MultivaluedMap<String, Object> headers = requestContext.getHeaders();
		final String basicAuthentication = basicAuthenticatorHeaderGenerator.create(SISTEMA_BATCH);

		headers.add(HttpHeaders.AUTHORIZATION, basicAuthentication);

		log.debug(headers.get(HttpHeaders.AUTHORIZATION).toString());
	}



}
