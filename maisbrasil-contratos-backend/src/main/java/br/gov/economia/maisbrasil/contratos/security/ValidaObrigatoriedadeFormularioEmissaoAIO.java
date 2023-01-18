package br.gov.economia.maisbrasil.contratos.security;

import java.util.Objects;

import javax.validation.constraints.NotNull;

public class ValidaObrigatoriedadeFormularioEmissaoAIO {
	private SiconvUser usuarioLogado;

	public ValidaObrigatoriedadeFormularioEmissaoAIO(@NotNull SiconvUser usuarioLogado) {
		Objects.requireNonNull(usuarioLogado);

		this.usuarioLogado = usuarioLogado;
	}

	public boolean verificarSeUsuarioPrecisaPreencherFormularioEmissaoAIO() {
		// 605665: SICONV-InstrumentosContratuais-ECU-ListarChecklist - [A3.1.3]
		// 772989: SICONV-InstrumentosContratuais-RN-FormularioEmissaoAIOPeloAdministrador
    	// Replicar essa RN no frontend (UserAuthorizerService)

		if (usuarioLogado.getCpf().equals("guest")) {
			return false;
		}

		boolean precisaPreencher = false;

		if (usuarioLogado.isAdmin() || usuarioLogado.isConcedente() || usuarioLogado.isMandataria()) {
			precisaPreencher = true;

		} else {
			precisaPreencher = false;
		}

		return precisaPreencher;
	}
}
