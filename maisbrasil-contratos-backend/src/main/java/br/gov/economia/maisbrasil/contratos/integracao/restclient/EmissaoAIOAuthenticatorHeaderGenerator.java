package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.gov.economia.maisbrasil.contratos.security.Profile;
import br.gov.economia.maisbrasil.contratos.security.Role;
import br.gov.economia.maisbrasil.contratos.security.SiconvUser;

@Component
@RequestScope
public class EmissaoAIOAuthenticatorHeaderGenerator {

	@Autowired
	private SiconvRestConfig siconvRestConfig;

	public String create(SiconvUser usuario) {

		Objects.requireNonNull(usuario);

		try {
			String secretKeyEmBase64 = siconvRestConfig.getSecretKey();

			String cpf = "maisbrasil-contratos-backend"; // Usuário será SISTEMA

			boolean usuarioEhConcedente = usuario.hasProfile(Profile.CONCEDENTE);

			List<Role> perfisPermitidosParaConcedente = Arrays.asList(Role.ADMINISTRADOR_SISTEMA,
					Role.ADMINISTRADOR_SISTEMA_ORGAO_EXTERNO);

			boolean usuarioEhAdministrador = usuarioEhConcedente && usuario.hasAnyRoleOfList(perfisPermitidosParaConcedente);

			if (!usuarioEhAdministrador) {
				cpf = usuario.getCpf();
			}

			String cpfESecretKeyEmMD5 = getMD5(cpf.concat(secretKeyEmBase64));

			String cpfEmTextoLivreESecretKeyEmBase64 = transformInBase64(cpfESecretKeyEmMD5);

			return String.format("Basic %s:%s", cpf, cpfEmTextoLivreESecretKeyEmBase64);

		} catch (NoSuchAlgorithmException algorithmException) {
			throw new IllegalArgumentException("Não foi possível gerar o Header.", algorithmException);
		}
	}

	@SuppressWarnings("findsecbugs:WEAK_MESSAGE_DIGEST_MD5") // O Siconv suporta apenas digest com MD5
	protected String getMD5(String text) throws NoSuchAlgorithmException {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();

			md.update(text.getBytes("ISO-8859-1"));
			byte[] digest = md.digest();

			return new String(digest, "ISO-8859-1");
		} catch (UnsupportedEncodingException ex) {
			throw new IllegalStateException("Cannot generate MD5 with ISO-8859-1", ex);
		}
	}

	protected String transformInBase64(String text) {
		try {
			return Base64.getEncoder().encodeToString(text.getBytes("UTF-8"));

		} catch (UnsupportedEncodingException ex) {
			throw new IllegalStateException("Cannot encode with UTF-8", ex);
		}
	}

}
