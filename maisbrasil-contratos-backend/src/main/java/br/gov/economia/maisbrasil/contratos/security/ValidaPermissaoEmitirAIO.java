package br.gov.economia.maisbrasil.contratos.security;

import java.util.Objects;

import javax.validation.constraints.NotNull;

public class ValidaPermissaoEmitirAIO {
	private SiconvUser usuarioLogado;

	public ValidaPermissaoEmitirAIO(@NotNull SiconvUser usuarioLogado) {
		Objects.requireNonNull(usuarioLogado);

		this.usuarioLogado = usuarioLogado;
	}

	public boolean verificarSeUsuarioPodeEmitirAIO() {
		// RN 605665: SICONV-InstrumentosContratuais-ECU-ListarChecklist
    	// Replicar essa RN no frontend (UserAuthorizerService)

		if (usuarioLogado.getCpf().equals("guest")) {
			return false;
		}

		boolean podeEmitirAIO = false;

		if (usuarioLogado.isProponenteEditorContrato()) {
			podeEmitirAIO = true;

		} else if (usuarioLogado.isAdmin() || usuarioLogado.isConcedente() || usuarioLogado.isMandataria()) {
			podeEmitirAIO = true;

		} else {
			podeEmitirAIO = false;
		}

		return podeEmitirAIO;
	}
}
