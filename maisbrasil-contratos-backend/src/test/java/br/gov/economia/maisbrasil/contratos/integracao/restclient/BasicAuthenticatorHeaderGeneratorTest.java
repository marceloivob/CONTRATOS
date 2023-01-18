package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class BasicAuthenticatorHeaderGeneratorTest {

	@InjectMocks
	private BasicAuthenticatorHeaderGenerator basicAuthenticatorHeaderGenerator;

	@Spy
	private SiconvRestConfig siconvRestConfig;

	private final String secretKey = "aW50ZWdyYWNhb192cnBsX3NpY29udg==";

	private final String cpfGenerico = "15327205487";
	private final String cpfEmBase64Generico = "w79hw7UENMKWQMKpIGIZGHfDijnClA==";
	private final String variavelDeControleGenerica = "Basic " + cpfGenerico + ":" + cpfEmBase64Generico;

	private final String cpf_0009 = "00009248145";
	private final String cpf0009EmBase64 = "Xh0Bw4kfG8KcHsO4wr8TwpIpF8OrSA==";
	private final String variavelDeControle_0009 = "Basic " + cpf_0009 + ":" + cpf0009EmBase64;

	private final String usuarioContratosBatch = "maisbrasil-contratos-batch";
	private final String usuarioContratosBatchEmBase64 = "DcKSRU4OwrMmV8KeSGcyw7PCsUAY";
	private final String variavelDeControle_usuarioContratosBatch = "Basic " + usuarioContratosBatch + ":"
			+ usuarioContratosBatchEmBase64;

	private final String usuarioContratosBackend = "maisbrasil-contratos-backend";
	private final String usuarioContratosBackendEmBase64 = "w70TR8OxBcO2woTClQkuwojDssKXdiEf";
	private final String variavelDeControle_usuarioContratosBackend = "Basic " + usuarioContratosBackend + ":"
			+ usuarioContratosBackendEmBase64;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(siconvRestConfig.getSecretKey()).thenReturn(secretKey);
	}

	@DisplayName("Testes usados para geração de chave usada na integração entre Contratos Backend e Contratos Batch e Siconv Rest")
	@Nested
	class Encode {

		@DisplayName("Geração de Encode para o CPF 15327205487")
		@Test
		public void encodeCPFGenerico() {
			String resultado = basicAuthenticatorHeaderGenerator.create(cpfGenerico);
			assertEquals(variavelDeControleGenerica, resultado);
		}

		@DisplayName("Geração de Encode para o CPF 00009248145")
		@Test
		public void encodeCPF0009() {
			String resultado = basicAuthenticatorHeaderGenerator.create(cpf_0009);
			assertEquals(variavelDeControle_0009, resultado);
		}

		@DisplayName("Geração de Encode para a chave maisbrasil-contratos-batch")
		@Test
		public void encodeBatch() {
			String resultado = basicAuthenticatorHeaderGenerator.create(usuarioContratosBatch);
			assertEquals(variavelDeControle_usuarioContratosBatch, resultado);
		}

		@DisplayName("Geração de Encode para a chave maisbrasil-contratos-backend")
		@Test
		public void encodeBackend() {
			String resultado = basicAuthenticatorHeaderGenerator.create(usuarioContratosBackend);
			assertEquals(variavelDeControle_usuarioContratosBackend, resultado);
		}

	}

	@DisplayName("Testes usados para decodificação da chave usada na integração entre Contratos Backend e Contratos Batch e Siconv Rest")
	@Nested
	class Decode {
		@Test
		public void decodeCPFGenericoEmBase64() throws UnsupportedEncodingException {

			try {

				byte[] decoded = Base64.getDecoder().decode(cpfEmBase64Generico.getBytes("UTF-8"));
				String cpfEmMD5 = new String(decoded, "UTF-8");
				String novoMD5Calculado = basicAuthenticatorHeaderGenerator.getMD5(cpfGenerico.concat(secretKey));
				assertEquals(novoMD5Calculado, cpfEmMD5);

			} catch (NoSuchAlgorithmException algorithmException) {
				throw new IllegalArgumentException("Não foi possível gerar o HASH.", algorithmException);
			}

		}

		@Test
		public void decodeCPF0009EmBase64() throws UnsupportedEncodingException {

			try {

				byte[] decoded = Base64.getDecoder().decode(cpf0009EmBase64.getBytes("UTF-8"));
				String cpfEmMD5 = new String(decoded, "UTF-8");
				String novoMD5Calculado = basicAuthenticatorHeaderGenerator.getMD5(cpf_0009.concat(secretKey));
				assertEquals(novoMD5Calculado, cpfEmMD5);

			} catch (NoSuchAlgorithmException algorithmException) {
				throw new IllegalArgumentException("Não foi possível gerar o HASH.", algorithmException);
			}

		}

		@Test
		public void decodeContratosBackendEmBase64() throws UnsupportedEncodingException {

			try {

				byte[] decoded = Base64.getDecoder().decode(usuarioContratosBackendEmBase64.getBytes("UTF-8"));
				String cpfEmMD5 = new String(decoded, "UTF-8");
				String novoMD5Calculado = basicAuthenticatorHeaderGenerator
						.getMD5(usuarioContratosBackend.concat(secretKey));
				assertEquals(novoMD5Calculado, cpfEmMD5);

			} catch (NoSuchAlgorithmException algorithmException) {
				throw new IllegalArgumentException("Não foi possível gerar o HASH.", algorithmException);
			}

		}

		@Test
		public void decodeContratosBatchEmBase64() throws UnsupportedEncodingException {

			try {

				byte[] decoded = Base64.getDecoder().decode(usuarioContratosBatchEmBase64.getBytes("UTF-8"));
				String cpfEmMD5 = new String(decoded, "UTF-8");
				String novoMD5Calculado = basicAuthenticatorHeaderGenerator
						.getMD5(usuarioContratosBatch.concat(secretKey));
				assertEquals(novoMD5Calculado, cpfEmMD5);

			} catch (NoSuchAlgorithmException algorithmException) {
				throw new IllegalArgumentException("Não foi possível gerar o HASH.", algorithmException);
			}

		}
	}
}
