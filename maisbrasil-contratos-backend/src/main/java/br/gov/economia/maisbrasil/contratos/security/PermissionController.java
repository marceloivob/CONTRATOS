package br.gov.economia.maisbrasil.contratos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PermissionController {

	@Autowired
	private SecurityHelperService securityHelper;

    public boolean usuarioLogadoTemPermissaoParaEditarContrato() {
    	SiconvUser usuarioLogado = securityHelper.getUsuarioLogadoContexto();

		ValidaPermissaoEditarContrato validador = new ValidaPermissaoEditarContrato(usuarioLogado);

		return validador.verificarSeUsuarioPodeEditarContrato();
    }

    public boolean usuarioLogadoTemPermissaoParaCancelarAIO() {
    	SiconvUser usuarioLogado = securityHelper.getUsuarioLogadoContexto();

		ValidaPermissaoCancelarAIO validador = new ValidaPermissaoCancelarAIO(usuarioLogado);

		return validador.verificarSeUsuarioPodeCancelarAIO();
	}

    public boolean usuarioLogadoTemPermissaoParaEmitirAIOdurantePeriodoEleitoral() {
    	SiconvUser usuarioLogado = securityHelper.getUsuarioLogadoContexto();

		ValidaPermissaoEmitirAIOdurantePeriodoEleitoral validador = new ValidaPermissaoEmitirAIOdurantePeriodoEleitoral(usuarioLogado);

		return validador.verificarSeUsuarioPodeEmitirAIOdurantePeriodoEleitoral();
    }

	public boolean usuarioLogadoTemPermissaoParaEmitirAIO() {
		SiconvUser usuarioLogado = securityHelper.getUsuarioLogadoContexto();

		ValidaPermissaoEmitirAIO validador = new ValidaPermissaoEmitirAIO(usuarioLogado);

		return validador.verificarSeUsuarioPodeEmitirAIO();
	}

	public boolean usuarioLogadoPrecisaPreencherFormularioEmissaoAIO() {
		SiconvUser usuarioLogado = securityHelper.getUsuarioLogadoContexto();

		ValidaObrigatoriedadeFormularioEmissaoAIO validador = new ValidaObrigatoriedadeFormularioEmissaoAIO(usuarioLogado);

		return validador.verificarSeUsuarioPrecisaPreencherFormularioEmissaoAIO();
	}
}
