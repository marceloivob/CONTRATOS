package br.gov.economia.maisbrasil.contratos.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApplicationInformation {

	@Autowired
	private Environment env;

	@GetMapping("/integrations")
	public Map<String, Object> getEndpoints() {
		final String keyToIDP = "maisbrasil.contratos.integracao.idp.uri-base";
		String valueToIDP = env.getProperty(keyToIDP);

		if ((valueToIDP == null) || (valueToIDP.isEmpty())) {
			throw new IllegalArgumentException(
					String.format("Configuração do IDP inválida: '%s' - '%s'", keyToIDP, valueToIDP));
		}

		final String keyToSICONV = "maisbrasil.contratos.integracao.siconv.uri-base";
		String valueToSICONV = env.getProperty(keyToSICONV);

		if ((valueToSICONV == null) || (valueToSICONV.isEmpty())) {
			throw new IllegalArgumentException(
					String.format("Configuração do SICONV inválida: '%s' - '%s'", valueToSICONV, valueToSICONV));
		}

		final String keyInativarEmissaoAIOperiodoEleitoral = "maisbrasil.contratos.inativar-emissao-aio-periodo-eleitoral";
		Boolean valueInativarEmissaoAIOperiodoEleitoral = Boolean.valueOf(env.getProperty(keyInativarEmissaoAIOperiodoEleitoral));

		Map<String, Object> mm = new HashMap<>();
		mm.put("IDP", valueToIDP);
		mm.put("SICONV", valueToSICONV);
		mm.put("INATIVAR_EMISSAO_AIO_PERIODO_ELEITORAL", valueInativarEmissaoAIOperiodoEleitoral);

		return mm;
	}

}
