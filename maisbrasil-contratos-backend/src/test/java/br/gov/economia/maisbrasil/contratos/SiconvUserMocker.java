package br.gov.economia.maisbrasil.contratos;

import org.mockito.Mockito;

import br.gov.economia.maisbrasil.contratos.security.Profile;
import br.gov.economia.maisbrasil.contratos.security.SiconvUser;

public class SiconvUserMocker {

	public SiconvUser mockUsuarioGuest() {
		SiconvUser usuario = Mockito.mock(SiconvUser.class);

		Mockito.when(usuario.getCpf()).thenReturn("guest");
		Mockito.when(usuario.isGuest()).thenReturn(true);
		Mockito.when(usuario.isApenasGuest()).thenReturn(true);

		return usuario;
	}

	public SiconvUser mockUsuarioConcedente() {
		SiconvUser usuario = Mockito.mock(SiconvUser.class);

		Mockito.when(usuario.getCpf()).thenReturn("0000000000");

		Mockito.when(usuario.hasProfile(Profile.CONCEDENTE)).thenReturn(true);
		Mockito.when(usuario.isConcedente()).thenReturn(true);

		Mockito.when(usuario.isConcedenteGCC()).thenReturn(false);
		Mockito.when(usuario.isAdmin()).thenReturn(false);

		return usuario;
	}

	public SiconvUser mockUsuarioConcedenteGCC() {
		SiconvUser usuario = Mockito.mock(SiconvUser.class);

		Mockito.when(usuario.getCpf()).thenReturn("0000000000");

		Mockito.when(usuario.hasProfile(Profile.CONCEDENTE)).thenReturn(true);
		Mockito.when(usuario.isConcedente()).thenReturn(true);
		Mockito.when(usuario.isConcedenteGCC()).thenReturn(true);

		Mockito.when(usuario.isAdmin()).thenReturn(false);

		return usuario;
	}

	public SiconvUser mockUsuarioProponente() {
		SiconvUser usuario = Mockito.mock(SiconvUser.class);

		Mockito.when(usuario.getCpf()).thenReturn("0000000000");

		Mockito.when(usuario.hasProfile(Profile.PROPONENTE)).thenReturn(true);
		Mockito.when(usuario.isProponente()).thenReturn(true);
		Mockito.when(usuario.isProponenteEditorContrato()).thenReturn(false);

		return usuario;
	}

	public SiconvUser mockUsuarioMandataria() {
		SiconvUser usuario = Mockito.mock(SiconvUser.class);

		Mockito.when(usuario.getCpf()).thenReturn("0000000000");

		Mockito.when(usuario.hasProfile(Profile.MANDATARIA)).thenReturn(true);
		Mockito.when(usuario.isMandataria()).thenReturn(true);
		Mockito.when(usuario.isMandatariaGCIM()).thenReturn(false);

		return usuario;
	}

	public SiconvUser mockUsuarioMandatariaGCIM() {
		SiconvUser usuario = Mockito.mock(SiconvUser.class);

		Mockito.when(usuario.getCpf()).thenReturn("0000000000");

		Mockito.when(usuario.hasProfile(Profile.MANDATARIA)).thenReturn(true);
		Mockito.when(usuario.isMandataria()).thenReturn(true);
		Mockito.when(usuario.isMandatariaGCIM()).thenReturn(true);

		return usuario;
	}

	public SiconvUser mockUsuarioAdministrador() {
		SiconvUser usuario = Mockito.mock(SiconvUser.class);

		Mockito.when(usuario.getCpf()).thenReturn("0000000000");

		Mockito.when(usuario.hasProfile(Profile.CONCEDENTE)).thenReturn(true);
		Mockito.when(usuario.isConcedente()).thenReturn(true);
		Mockito.when(usuario.isAdmin()).thenReturn(true);
		Mockito.when(usuario.isConcedenteGCC()).thenReturn(false);

		return usuario;
	}

	public SiconvUser mockUsuarioProponenteEditorContrato() {
		SiconvUser usuario = Mockito.mock(SiconvUser.class);

		Mockito.when(usuario.getCpf()).thenReturn("0000000000");

		Mockito.when(usuario.hasProfile(Profile.PROPONENTE)).thenReturn(true);
		Mockito.when(usuario.isProponente()).thenReturn(true);
		Mockito.when(usuario.isProponenteEditorContrato()).thenReturn(true);

		return usuario;
	}
}
