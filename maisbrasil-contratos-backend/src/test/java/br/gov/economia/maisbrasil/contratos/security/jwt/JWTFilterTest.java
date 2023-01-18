package br.gov.economia.maisbrasil.contratos.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import br.gov.economia.maisbrasil.contratos.security.PublicKeyIDP;
import io.github.jhipster.config.JHipsterProperties;

@Disabled
@SpringBootTest
public class JWTFilterTest {

	private TokenProvider tokenProvider;

	private JWTFilter jwtFilter;

	@Autowired
	private JHipsterProperties jHipsterProperties;

	@BeforeEach
	public void beforeEach() throws Exception {

		PublicKeyIDP keyIDP = new PublicKeyIDP(jHipsterProperties);

		tokenProvider = new TokenProvider(jHipsterProperties, keyIDP);
		tokenProvider.afterPropertiesSet();

		ReflectionTestUtils.setField(tokenProvider, "tokenValidityInMilliseconds", 60000);

		jwtFilter = new JWTFilter(tokenProvider);

		SecurityContextHolder.getContext().setAuthentication(null);
	}

	@Test
	public void testJWTFilter() throws Exception {
		String jwt = DataFactory.CONCEDENTE;

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		request.setRequestURI("/api/test");
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		jwtFilter.doFilter(request, response, filterChain);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(SecurityContextHolder.getContext().getAuthentication().getName())
				.isEqualTo("JOSE ANTONIO DE AGUIAR NETO");
		assertThat(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString()).isEqualTo(jwt);
	}

//	Corrigir Teste
//	@Test
//	public void testJWTFilterInvalidToken() throws Exception {
//		String jwt = DataFactory.TOKEN_JWT_ASSINATURA_INVALIDA;
//
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//		request.setRequestURI("/api/test");
//		MockHttpServletResponse response = new MockHttpServletResponse();
//		MockFilterChain filterChain = new MockFilterChain();
//		jwtFilter.doFilter(request, response, filterChain);
//		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//		assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
//	}

	@Test
	public void testJWTFilterMissingAuthorization() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/api/test");
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		jwtFilter.doFilter(request, response, filterChain);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
	}

	@Test
	public void testJWTFilterMissingToken() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer ");
		request.setRequestURI("/api/test");
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		jwtFilter.doFilter(request, response, filterChain);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
	}

	@Test
	public void testJWTFilterWrongScheme() throws Exception {
		String jwt = DataFactory.CONCEDENTE;

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Basic " + jwt);
		request.setRequestURI("/api/test");
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		jwtFilter.doFilter(request, response, filterChain);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
	}

}
