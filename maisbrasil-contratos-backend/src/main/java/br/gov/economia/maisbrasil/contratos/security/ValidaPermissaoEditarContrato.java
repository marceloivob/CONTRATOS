package br.gov.economia.maisbrasil.contratos.security;

import java.util.Objects;

import javax.validation.constraints.NotNull;

public class ValidaPermissaoEditarContrato {
	private SiconvUser usuarioLogado;

	public ValidaPermissaoEditarContrato(@NotNull SiconvUser usuarioLogado) {
		Objects.requireNonNull(usuarioLogado);

		this.usuarioLogado = usuarioLogado;
	}

	public boolean verificarSeUsuarioPodeEditarContrato() {
		if (usuarioLogado.getCpf().equals("guest")) {
			return false;
		}

		boolean podeEditarContrato = false;

		if (usuarioLogado.isProponenteEditorContrato()) {
			podeEditarContrato = true;

		} else {
			podeEditarContrato = false;
		}

		return podeEditarContrato;
	}
}
