package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class URLSiconvRestGenerator {

	@Autowired
	private SiconvRestConfig siconvRestConfig;

	public String getEndpointAtualizarHistorico() {
		final String urlBase = this.siconvRestConfig.getEndpoint();
        final String servico = "/api/licitacao/atualizarhistorico";
        final String urlTarget = urlBase.concat(servico);

		return urlTarget;
	}

}
