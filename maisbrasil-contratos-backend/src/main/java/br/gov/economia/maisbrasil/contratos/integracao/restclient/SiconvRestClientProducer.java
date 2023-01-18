package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import javax.net.ssl.HostnameVerifier;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Feature;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.logging.LoggingFeature.Verbosity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.context.annotation.RequestScope;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SiconvRestClientProducer {

	@Value("${SSL_TRUSTSTORE_PASS}")
	private String trustStorePass;

	@Value("${SSL_TRUSTSTORE_PATH}")
	private String trustStorePath;

	@Value("${SSL_KEYSTORE_PASS}")
	private String keyStorePass;

	@Value("${SSL_KEYSTORE_PATH}")
	private String keyStorePath;

	// Fonte original (código com adaptações):
	// https://stackoverflow.com/questions/40171273/how-to-print-server-responses-using-loggingfeature-in-dropwizard-1-0-2
	private Feature loggingFeature = new LoggingFeature(getLogger(), Level.ALL, Verbosity.PAYLOAD_ANY, null);

	@Bean
	@RequestScope
	public Client createHttpClient(Environment env) throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException, KeyManagementException, UnrecoverableKeyException {
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		log.info("Criando SiconvRestClient para o ambiente: {}", activeProfiles);

		log.debug("SSL_TRUSTSTORE_PASS: {}", trustStorePass == null ? "Valor não definido" : "***********");
		log.info("SSL_TRUSTSTORE_PATH: {}", trustStorePath);
		log.debug("SSL_KEYSTORE_PASS: {}", keyStorePass == null ? "Valor não definido" : "***********");
		log.info("SSL_KEYSTORE_PATH: {}", keyStorePath);

		KeyStore trustStore = KeyStore.getInstance("JKS");

		SSLContextBuilder sslContext = new SSLContextBuilder();
		HostnameVerifier hostnameVerifier = new NoopHostnameVerifier();

		try (InputStream is = new FileInputStream(trustStorePath)) {
			trustStore.load(is, trustStorePass != null ? trustStorePass.toCharArray() : null);
			sslContext.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy());
			log.info("TrustStore carregada com sucesso.");

			setSSLContext(keyStorePass, keyStorePath, sslContext);

			Client client = ClientBuilder.newBuilder() //
					.register(loggingFeature) //
					.sslContext(sslContext.build()) //
					.hostnameVerifier(hostnameVerifier) //
					.build();

			log.info("SiconvRestClient criado com sucesso." + client.toString());

			return client;
		}

	}

	private static Logger getLogger() {
		// Configure Logger to log it all
		Logger logger = Logger.getLogger(SiconvRestClientProducer.class.getName());
		logger.setLevel(Level.ALL);
		logger.setUseParentHandlers(false);

		Handler[] handlers = logger.getHandlers();

		for (Handler h : handlers) {
			logger.removeHandler(h);
		}

		logger.addHandler(buildseh());

		return logger;
	}

	public static StreamHandler buildseh() {
		final StreamHandler seh = new StreamHandler(System.err, new SimpleFormatter()) {
			@Override
			public synchronized void publish(final LogRecord record) {
				super.publish(record);
				flush();
			}
		};

		seh.setLevel(Level.ALL); // Default StdErr Setting

		return seh;
	}

	/**
	 * Insere o contexto SSL (keystore) no request
	 *
	 * @throws IOException
	 * @throws CertificateException
	 * @throws UnrecoverableKeyException
	 */
	private static void setSSLContext(String keyStorePass, String keyStorePath, SSLContextBuilder sslContext)
			throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException,
			UnrecoverableKeyException {

		KeyStore keyStore = KeyStore.getInstance("JKS");

		try (InputStream is = new FileInputStream(keyStorePath)) {

			keyStore.load(is, keyStorePass != null ? keyStorePass.toCharArray() : null);
			sslContext.loadKeyMaterial(keyStore, keyStorePass != null ?keyStorePass.toCharArray() : null);

			log.info("KeyStore carregada com sucesso.");
		}

	}

}
