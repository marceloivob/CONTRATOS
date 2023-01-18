package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.gov.economia.maisbrasil.contratos.security.SecurityUtils;
import br.gov.economia.maisbrasil.contratos.security.SiconvUser;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * Fonte:
 * http://www.adam-bien.com/roller/abien/entry/client_side_http_basic_access
 */
@Slf4j
@Component
@RequestScope
public class CancelamentoEmissaoAIOAuthenticatorFilter implements ClientRequestFilter {

	@Autowired
	private CancelamentoEmissaoAIOAuthenticatorHeaderGenerator cancelamentoEmissaoAIOAuthenticatorHeaderGenerator;

	/**
	 * Construtor Padrão para o CDI
	 */
	public CancelamentoEmissaoAIOAuthenticatorFilter() {
		// noop
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		MultivaluedMap<String, Object> headers = requestContext.getHeaders();

		Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();

		final String basicAuthentication = cancelamentoEmissaoAIOAuthenticatorHeaderGenerator.create(usuarioLogado);

		headers.add(HttpHeaders.AUTHORIZATION, basicAuthentication);

		log.debug(headers.get(HttpHeaders.AUTHORIZATION).toString());
	}



}
