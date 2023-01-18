package br.gov.economia.maisbrasil.contratos.security.jwt;

import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import br.gov.economia.maisbrasil.contratos.security.PublicKeyIDP;
import br.gov.economia.maisbrasil.contratos.security.SiconvPrincipal;
import br.gov.economia.maisbrasil.contratos.security.SiconvUser;
import io.github.jhipster.config.JHipsterProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenProvider implements InitializingBean {

	private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

	private static final String AUTHORITIES_KEY = "auth";

	private Key key;

	private long tokenValidityInMilliseconds;

	private long tokenValidityInMillisecondsForRememberMe;

	private final JHipsterProperties jHipsterProperties;

	private final PublicKeyIDP keyIDP;

	public TokenProvider(JHipsterProperties jHipsterProperties, PublicKeyIDP keyIDP) {
		this.jHipsterProperties = jHipsterProperties;
		this.keyIDP = keyIDP;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		PublicKey publicKey = keyIDP.readPublicKey();
		KeyPair kp = new KeyPair(publicKey, null);

		this.key = kp.getPublic();

		this.tokenValidityInMilliseconds = 1000
				* jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
		this.tokenValidityInMillisecondsForRememberMe = 1000 * jHipsterProperties.getSecurity().getAuthentication()
				.getJwt().getTokenValidityInSecondsForRememberMe();
	}

	public String createToken(Authentication authentication, boolean rememberMe) {
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		Date validity;
		if (rememberMe) {
			validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
		} else {
			validity = new Date(now + this.tokenValidityInMilliseconds);
		}

		return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
				.signWith(key, SignatureAlgorithm.RS256).setExpiration(validity).compact();
	}

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

		Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
		
		if (claims.get("roles") != null) {
			authorities = Arrays.stream(claims.get("roles").toString().replace(" ", "").replace("[", "").replace("]", "").split(","))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		}

		SiconvPrincipal principal = new SiconvUser(claims, authorities);

		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, token, authorities);

		return authentication;
	}

	public void validateToken(String authToken) {
		try {

			Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);

		} catch (SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT signature.");
			log.trace("Invalid JWT signature trace: {}", e);
			throw e;
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token.");
			log.trace("Expired JWT token trace: {}", e);
			throw e;
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token.");
			log.trace("Unsupported JWT token trace: {}", e);
			throw e;
		} catch (IllegalArgumentException e) {
			log.info("JWT token compact of handler are invalid.");
			log.trace("JWT token compact of handler are invalid trace: {}", e);
			throw e;
		}

	}

}
