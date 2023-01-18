package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

@Disabled
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SiconvRestImplIT {

	@Spy
	private SiconvRestImpl siconvRest;

	@Mock
	private URLSiconvRestGenerator urlSiconvRestGenerator;

	private static String url;

	@BeforeAll
	public static void setup() {
		final String path = "src/test/resources";
		String filename = "/siconvdesrest.jks";
		url = "https://10.139.67.53:8443/voluntarias/api/licitacao/atualizarhistorico";

//		String filename = "/siconvhomrest.jks";
//		url = "https://hom4.voluntarias.plataformamaisbrasil.gov.br/voluntarias/api/licitacao/atualizarhistorico";

		String password = "changeit";

		File file = new File(path + filename);
		String absolutePath = file.getAbsolutePath();

		System.setProperty("javax.net.ssl.trustStore", absolutePath);
		System.setProperty("javax.net.ssl.trustStorePassword", password);

	}

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.siconvRest.setUrlSiconvRestGenerator(urlSiconvRestGenerator);

		HostnameVerifier noopHostnameVerifier = new NoopHostnameVerifier();

		Client client = ClientBuilder //
				.newBuilder() //
				.hostnameVerifier(noopHostnameVerifier) //
				.build();

		this.siconvRest.setClient(client);
	}

	@DisplayName("Teste de Integração Contratos Backend com CPF x Siconv Rest")
	@Test
	public void propostaNaoEncontradaAoAtualizarHistoricoComCPF() {

		// Setup do Teste
		Long propostaInexistente = -1L;
		String justificativa = "Nenhuma";
		Boolean batch = false;

		this.siconvRest.setBasicAuthenticatorFilter(new MockBasicAuthenticatorFilterForCPF());

		Mockito.when(urlSiconvRestGenerator.getEndpointAtualizarHistorico()).thenReturn(url);

		// Execução do Teste
		AcionarServicoSiconvException e = assertThrows(AcionarServicoSiconvException.class,
				() -> siconvRest.atualizarHistorico(propostaInexistente, justificativa, batch));

		// Asserts do resultado
		assertTrue(e.getMotivo().contains("Mensagem: [ERRO_AO_ACIONAR_SERVICO_SICONV]"));
		assertTrue(e.getMotivo().contains("Serviço: ATUALIZAR HISTORICO"));
		assertTrue(e.getMotivo().contains("Parâmetros: {\"proposta\":\"-1\",\"justificativa\":\"Nenhuma\"}"));
		assertTrue(e.getMotivo().contains(
				"Status Code: 500, Motivo: Erro Interno do Servidor (RuntimeException) - Proposta não encontrada."));
		assertTrue(e.getMotivo().contains("Ticket: "));
	}

	@DisplayName("Teste de Integração Contratos Backend com chave combinada x Siconv Rest")
	@Test
	public void propostaNaoEncontradaAoAtualizarHistoricoParaContratosBackend() {

		// Setup do Teste
		Long propostaInexistente = -1L;
		String justificativa = "Nenhuma";
		Boolean batch = false;

		this.siconvRest.setBasicAuthenticatorFilter(new MockBasicAuthenticatorFilterForContratosBackend());

		Mockito.when(urlSiconvRestGenerator.getEndpointAtualizarHistorico()).thenReturn(url);

		// Execução do Teste
		AcionarServicoSiconvException e = assertThrows(AcionarServicoSiconvException.class,
				() -> siconvRest.atualizarHistorico(propostaInexistente, justificativa, batch));

		// Asserts do resultado
		assertTrue(e.getMotivo().contains("Mensagem: [ERRO_AO_ACIONAR_SERVICO_SICONV]"));
		assertTrue(e.getMotivo().contains("Serviço: ATUALIZAR HISTORICO"));
		assertTrue(e.getMotivo().contains("Parâmetros: {\"proposta\":\"-1\",\"justificativa\":\"Nenhuma\"}"));
		assertTrue(e.getMotivo().contains(
				"Status Code: 500, Motivo: Erro Interno do Servidor (RuntimeException) - Proposta não encontrada."));
		assertTrue(e.getMotivo().contains("Ticket: "));
	}

	@DisplayName("Teste de Integração Contratos Batch x Siconv Rest")
	@Test
	public void propostaNaoEncontradaAoAtualizarHistoricoParaContratosBatch() {

		// Setup do Teste
		Long propostaInexistente = -1L;
		String justificativa = "Nenhuma";
		Boolean batch = false;

		this.siconvRest.setBasicAuthenticatorFilter(new MockBasicAuthenticatorFilterForContratosBatch());

		Mockito.when(urlSiconvRestGenerator.getEndpointAtualizarHistorico()).thenReturn(url);

		// Execução do Teste
		AcionarServicoSiconvException e = assertThrows(AcionarServicoSiconvException.class,
				() -> siconvRest.atualizarHistorico(propostaInexistente, justificativa, batch));

		// Asserts do resultado
		assertTrue(e.getMotivo().contains("Mensagem: [ERRO_AO_ACIONAR_SERVICO_SICONV]"));
		assertTrue(e.getMotivo().contains("Serviço: ATUALIZAR HISTORICO"));
		assertTrue(e.getMotivo().contains("Parâmetros: {\"proposta\":\"-1\",\"justificativa\":\"Nenhuma\"}"));
		assertTrue(e.getMotivo().contains(
				"Status Code: 500, Motivo: Erro Interno do Servidor (RuntimeException) - Proposta não encontrada."));
		assertTrue(e.getMotivo().contains("Ticket: "));
	}

}

class MockBasicAuthenticatorFilterForCPF extends BasicAuthenticatorFilter {

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		MultivaluedMap<String, Object> headers = requestContext.getHeaders();

		String usuario = "47711752920";
		String cpfEmTextoLivreESecretKeyEmBase64 = "G8OIUsO/wpxPLMKJw6AXw4fDi8OPAMORwqg=";
		final String basicAuthentication = String.format("Basic %s:%s", usuario, cpfEmTextoLivreESecretKeyEmBase64);

		headers.add(HttpHeaders.AUTHORIZATION, basicAuthentication);

	}

}

class MockBasicAuthenticatorFilterForContratosBackend extends BasicAuthenticatorFilter {

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		MultivaluedMap<String, Object> headers = requestContext.getHeaders();

		String usuario = "maisbrasil-contratos-backend";
		String cpfEmTextoLivreESecretKeyEmBase64 = "w70TR8OxBcO2woTClQkuwojDssKXdiEf";
		final String basicAuthentication = String.format("Basic %s:%s", usuario, cpfEmTextoLivreESecretKeyEmBase64);

		headers.add(HttpHeaders.AUTHORIZATION, basicAuthentication);

	}

}

class MockBasicAuthenticatorFilterForContratosBatch extends BasicAuthenticatorFilter {

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		MultivaluedMap<String, Object> headers = requestContext.getHeaders();

		String usuario = "maisbrasil-contratos-batch";
		String cpfEmTextoLivreESecretKeyEmBase64 = "DcKSRU4OwrMmV8KeSGcyw7PCsUAY";
		final String basicAuthentication = String.format("Basic %s:%s", usuario, cpfEmTextoLivreESecretKeyEmBase64);

		headers.add(HttpHeaders.AUTHORIZATION, basicAuthentication);

	}

}