package br.gov.economia.maisbrasil.contratos.security;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.gov.economia.maisbrasil.contratos.SiconvUserMocker;

@DisplayName(value = "Caso de Teste Permitir Editar Contrato")
class ValidaPermissaoEditarContratoTest {
	private static SiconvUserMocker siconvUserMocker;

	@BeforeAll
	static void setup() {
		siconvUserMocker = new SiconvUserMocker();
	}

	@Test
	@DisplayName(value = "Caso de Teste - Usuários que não podem editar contrato")
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
			ValidaPermissaoEditarContrato validador = new ValidaPermissaoEditarContrato(usuario);

			boolean resultadoEncontrado = validador.verificarSeUsuarioPodeEditarContrato();

			// Assert
			assertFalse(resultadoEncontrado);
		});
	}

	@Test
	@DisplayName(value = "Caso de Teste - Usuários que podem editar contrato")
	void podemEditarContrato() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioProponenteEditorContrato());

		// Act
		usuarios.forEach(usuario -> {
			ValidaPermissaoEditarContrato validador = new ValidaPermissaoEditarContrato(usuario);

			boolean resultadoEncontrado = validador.verificarSeUsuarioPodeEditarContrato();

			// Assert
			assertTrue(resultadoEncontrado);
		});
	}
}
