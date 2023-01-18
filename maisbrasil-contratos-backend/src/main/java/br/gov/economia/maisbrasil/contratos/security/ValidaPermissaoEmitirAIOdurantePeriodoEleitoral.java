package br.gov.economia.maisbrasil.contratos.security;

import java.util.Objects;

import javax.validation.constraints.NotNull;

public class ValidaPermissaoEmitirAIOdurantePeriodoEleitoral {

	private SiconvUser usuarioLogado;

	public ValidaPermissaoEmitirAIOdurantePeriodoEleitoral(@NotNull SiconvUser usuarioLogado) {
		Objects.requireNonNull(usuarioLogado);

		this.usuarioLogado = usuarioLogado;
	}

	public boolean verificarSeUsuarioPodeEmitirAIOdurantePeriodoEleitoral() {
		// RN 605665: SICONV-InstrumentosContratuais-ECU-ListarChecklist
    	// Replicar essa RN no frontend (UserAuthorizerService)

		if (usuarioLogado.getCpf().equals("guest")) {
			return false;
		}

		boolean podeEmitirAIOdurantePeriodoEleitoral = false;

		if (perfilPodeEmitirAIONoPeriodoEleitoral()) {
			podeEmitirAIOdurantePeriodoEleitoral = true;

		} else {
			podeEmitirAIOdurantePeriodoEleitoral = false;
		}

		return podeEmitirAIOdurantePeriodoEleitoral;
	}

	private boolean perfilPodeEmitirAIONoPeriodoEleitoral() {
		return usuarioLogado.isAdmin() || usuarioLogado.isConcedente() || usuarioLogado.isMandataria();
	}
}
