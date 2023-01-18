package br.gov.economia.maisbrasil.contratos.security;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.github.jhipster.config.JHipsterProperties;

@Component
public class PublicKeyIDP {

	private final JHipsterProperties jHipsterProperties;

	public PublicKeyIDP(JHipsterProperties jHipsterProperties) {
		this.jHipsterProperties = jHipsterProperties;
	}

	@Bean
	public RSAPublicKey readPublicKey() {
		String secretKey = jHipsterProperties.getSecurity().getAuthentication().getJwt().getBase64Secret();

		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(secretKey));

		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");

			return (RSAPublicKey) kf.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

}
