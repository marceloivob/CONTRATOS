package br.gov.economia.maisbrasil.contratos.security;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.gov.economia.maisbrasil.contratos.SiconvUserMocker;

@DisplayName(value = "Caso de Teste PermissionController")
class PermissionControllerTest {

	@Mock
	private SecurityHelperService securityHelper;

	@InjectMocks
	private PermissionController controller;

	private static SiconvUserMocker siconvUserMocker;

	@BeforeAll
	static void setup() {
		siconvUserMocker = new SiconvUserMocker();
	}

	@BeforeEach
	public void setupBefore() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName(value = "Caso de Teste PermissionController - Usuários que não podem emitir AIO")
	void naoPodemEmitirAIO() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioGuest());
		usuarios.add(siconvUserMocker.mockUsuarioProponente());

		// Act
		usuarios.forEach(usuario -> {
			Mockito.when(securityHelper.getUsuarioLogadoContexto()).thenReturn(usuario);

			boolean resultadoEncontrado = controller.usuarioLogadoTemPermissaoParaEmitirAIO();

			// Assert
			assertFalse(resultadoEncontrado);
		});
	}

	@Test
	@DisplayName(value = "Caso de Teste PermissionController - Usuários que podem emitir AIO")
	void podemEmitirAIO() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioProponenteEditorContrato());
		usuarios.add(siconvUserMocker.mockUsuarioAdministrador());
		usuarios.add(siconvUserMocker.mockUsuarioConcedente());
		usuarios.add(siconvUserMocker.mockUsuarioMandataria());
		usuarios.add(siconvUserMocker.mockUsuarioConcedenteGCC());
		usuarios.add(siconvUserMocker.mockUsuarioMandatariaGCIM());

		// Act
		usuarios.forEach(usuario -> {
			Mockito.when(securityHelper.getUsuarioLogadoContexto()).thenReturn(usuario);

			boolean resultadoEncontrado = controller.usuarioLogadoTemPermissaoParaEmitirAIO();

			// Assert
			assertTrue(resultadoEncontrado);
		});
	}

	@Test
	@DisplayName(value = "Caso de Teste PermissionController - Usuários que não podem emitir AIO durante período eleitoral")
	void naoPodemEmitirAIOdurantePeriodoEleitoral() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioGuest());
		usuarios.add(siconvUserMocker.mockUsuarioProponente());
		usuarios.add(siconvUserMocker.mockUsuarioProponenteEditorContrato());

		// Act
		usuarios.forEach(usuario -> {
			Mockito.when(securityHelper.getUsuarioLogadoContexto()).thenReturn(usuario);

			boolean resultadoEncontrado = controller.usuarioLogadoTemPermissaoParaEmitirAIOdurantePeriodoEleitoral();

			// Assert
			assertFalse(resultadoEncontrado);
		});
	}

	@Test
	@DisplayName(value = "Caso de Teste PermissionController - Usuários que podem emitir AIO durante período eleitoral")
	void podemEmitirAIOdurantePeriodoEleitoral() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioAdministrador());
		usuarios.add(siconvUserMocker.mockUsuarioConcedente());
		usuarios.add(siconvUserMocker.mockUsuarioMandataria());
		usuarios.add(siconvUserMocker.mockUsuarioConcedenteGCC());
		usuarios.add(siconvUserMocker.mockUsuarioMandatariaGCIM());

		// Act
		usuarios.forEach(usuario -> {
			Mockito.when(securityHelper.getUsuarioLogadoContexto()).thenReturn(usuario);

			boolean resultadoEncontrado = controller.usuarioLogadoTemPermissaoParaEmitirAIOdurantePeriodoEleitoral();

			// Assert
			assertTrue(resultadoEncontrado);
		});
	}

	@Test
	@DisplayName(value = "Caso de Teste PermissionController - Usuários que não podem editar contrato")
	void naoPodemEditarContrato() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioGuest());
		usuarios.add(siconvUserMocker.mockUsuarioProponente());
		usuarios.add(siconvUserMocker.mockUsuarioConcedente());
		usuarios.add(siconvUserMocker.mockUsuarioMandataria());
		usuarios.add(siconvUserMocker.mockUsuarioAdministrador());
		usuarios.add(siconvUserMocker.mockUsuarioConcedenteGCC());
		usuarios.add(siconvUserMocker.mockUsuarioMandatariaGCIM());

		// Act
		usuarios.forEach(usuario -> {
			Mockito.when(securityHelper.getUsuarioLogadoContexto()).thenReturn(usuario);

			boolean resultadoEncontrado = controller.usuarioLogadoTemPermissaoParaEditarContrato();

			// Assert
			assertFalse(resultadoEncontrado);
		});
	}

	@Test
	@DisplayName(value = "Caso de Teste PermissionController - Usuários que podem editar contrato")
	void podemEditarContrato() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioProponenteEditorContrato());

		// Act
		usuarios.forEach(usuario -> {
			Mockito.when(securityHelper.getUsuarioLogadoContexto()).thenReturn(usuario);

			boolean resultadoEncontrado = controller.usuarioLogadoTemPermissaoParaEditarContrato();

			// Assert
			assertTrue(resultadoEncontrado);
		});
	}

	@Test
	@DisplayName(value = "Caso de Teste PermissionController - Usuários que não podem cancelar AIO")
	void naoPodemCancelarAIO() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioGuest());
		usuarios.add(siconvUserMocker.mockUsuarioProponente());
		usuarios.add(siconvUserMocker.mockUsuarioProponenteEditorContrato());
		usuarios.add(siconvUserMocker.mockUsuarioConcedente());
		usuarios.add(siconvUserMocker.mockUsuarioMandataria());

		// Act
		usuarios.forEach(usuario -> {
			Mockito.when(securityHelper.getUsuarioLogadoContexto()).thenReturn(usuario);

			boolean resultadoEncontrado = controller.usuarioLogadoTemPermissaoParaCancelarAIO();

			// Assert
			assertFalse(resultadoEncontrado);
		});
	}

	@Test
	@DisplayName(value = "Caso de Teste PermissionController - Usuários que podem cancelar AIO")
	void podemCancelarAIO() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioAdministrador());
		usuarios.add(siconvUserMocker.mockUsuarioConcedenteGCC());
		usuarios.add(siconvUserMocker.mockUsuarioMandatariaGCIM());

		// Act
		usuarios.forEach(usuario -> {
			Mockito.when(securityHelper.getUsuarioLogadoContexto()).thenReturn(usuario);

			boolean resultadoEncontrado = controller.usuarioLogadoTemPermissaoParaCancelarAIO();

			// Assert
			assertTrue(resultadoEncontrado);
		});
	}

	@Test
	@DisplayName(value = "Caso de Teste - Usuários que não precisam preencher o formulário da AIO")
	void naoPrecisamPreencherFormulario() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioGuest());
		usuarios.add(siconvUserMocker.mockUsuarioProponente());
		usuarios.add(siconvUserMocker.mockUsuarioProponenteEditorContrato());

		// Act
		usuarios.forEach(usuario -> {
			Mockito.when(securityHelper.getUsuarioLogadoContexto()).thenReturn(usuario);

			boolean resultadoEncontrado = controller.usuarioLogadoPrecisaPreencherFormularioEmissaoAIO();

			// Assert
			assertFalse(resultadoEncontrado);
		});
	}

	@Test
	@DisplayName(value = "Caso de Teste - Usuários que precisam preencher o formulário da AIO")
	void precisamPreencherFormulario() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioAdministrador());
		usuarios.add(siconvUserMocker.mockUsuarioConcedente());
		usuarios.add(siconvUserMocker.mockUsuarioMandataria());
		usuarios.add(siconvUserMocker.mockUsuarioConcedenteGCC());
		usuarios.add(siconvUserMocker.mockUsuarioMandatariaGCIM());

		// Act
		usuarios.forEach(usuario -> {
			Mockito.when(securityHelper.getUsuarioLogadoContexto()).thenReturn(usuario);

			boolean resultadoEncontrado = controller.usuarioLogadoPrecisaPreencherFormularioEmissaoAIO();

			// Assert
			assertTrue(resultadoEncontrado);
		});
	}
}
