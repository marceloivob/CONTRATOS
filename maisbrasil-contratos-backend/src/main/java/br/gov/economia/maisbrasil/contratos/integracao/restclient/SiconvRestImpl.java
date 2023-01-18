package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import javax.annotation.PreDestroy;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.extern.slf4j.Slf4j;

@Primary
@Component
@RequestScope
@Slf4j
public class SiconvRestImpl implements SiconvRest {

	@Autowired
	private BasicAuthenticatorFilter basicAuthenticatorFilter;

	@Autowired
	private BatchAuthenticatorFilter batchAuthenticatorFilter;

	@Autowired
	private URLSiconvRestGenerator urlSiconvRestGenerator;

	@Autowired
	private Client client;

	@Override
	public AtualizarHistoricoResponse atualizarHistorico(Long propostaFk, String justificativa, boolean batch) {

		final String url = urlSiconvRestGenerator.getEndpointAtualizarHistorico();

		AtualizarHistoricoIntegracao ahi = new AtualizarHistoricoIntegracao(propostaFk, justificativa);

		if (batch) {
			client.register(batchAuthenticatorFilter);
		} else {
			client.register(basicAuthenticatorFilter);
		}

		try (Response response = client //
				.register(AtualizarHistoricoResponseMessageBodyReaderProvider.class) //
				.target(url) //
				.request() //
				.accept(MediaType.TEXT_PLAIN) //
				.acceptLanguage("pt-BR")//
				.post(Entity.json(ahi))) {

			if (respostaComSucesso(response)) {
				return new AtualizarHistoricoResponse(Boolean.TRUE);
			} else {
				throw new AcionarServicoSiconvException("ATUALIZAR HISTORICO", ahi, response);
			}
		} finally {
			client.close();
		}
	}

	@PreDestroy
	public void destroy() {
		log.debug("Destruindo o client: " + client.toString());

		this.client.close();
		this.client = null;
	}

	////////////////////////////////////////////////////////////////////////
	// Métodos Auxiliares
	////////////////////////////////////////////////////////////////////////

	protected boolean respostaComSucesso(Response response) {
		return response.getStatus() == Status.OK.getStatusCode();
	}

	public void setBasicAuthenticatorFilter(BasicAuthenticatorFilter basicAuthenticatorFilter) {
		this.basicAuthenticatorFilter = basicAuthenticatorFilter;
	}

	public void setBatchAuthenticatorFilter(BatchAuthenticatorFilter batchAuthenticatorFilter) {
		this.batchAuthenticatorFilter = batchAuthenticatorFilter;
	}

	public void setUrlSiconvRestGenerator(URLSiconvRestGenerator urlSiconvRestGenerator) {
		this.urlSiconvRestGenerator = urlSiconvRestGenerator;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
