package br.gov.economia.maisbrasil.contratos.security;

import java.util.Objects;

import javax.validation.constraints.NotNull;

public class ValidaPermissaoCancelarAIO {

	private SiconvUser usuarioLogado;

	public ValidaPermissaoCancelarAIO(@NotNull SiconvUser usuarioLogado) {
		Objects.requireNonNull(usuarioLogado);

		this.usuarioLogado = usuarioLogado;
	}

	public boolean verificarSeUsuarioPodeCancelarAIO() {
		if ("guest".equals(usuarioLogado.getCpf())) {
			return false;
		}

		boolean podeCancelarAIO = false;

		if (usuarioLogado.isAdmin()) {
			podeCancelarAIO = true;

		} else if (usuarioLogado.isConcedenteGCC()) {
			podeCancelarAIO = true;

		} else if (usuarioLogado.isMandatariaGCIM()) {
			podeCancelarAIO = true;

		} else {
			podeCancelarAIO = false;
		}

		return podeCancelarAIO;
	}

}
