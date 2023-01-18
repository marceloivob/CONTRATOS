package br.gov.economia.maisbrasil.contratos.security;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.gov.economia.maisbrasil.contratos.SiconvUserMocker;

@DisplayName(value = "Caso de Teste Permitir Emissão de AIO")
class ValidaPermissaoEmitirAIOTest {
	private static SiconvUserMocker siconvUserMocker;

	@BeforeAll
	static void setup() {
		siconvUserMocker = new SiconvUserMocker();
	}

	@Test
	@DisplayName(value = "Caso de Teste - Usuários que não podem emitir AIO")
	void naoPodemEmitirAIO() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioGuest());
		usuarios.add(siconvUserMocker.mockUsuarioProponente());

		// Act
		usuarios.forEach(usuario -> {
			ValidaPermissaoEmitirAIO validador = new ValidaPermissaoEmitirAIO(usuario);

			boolean resultadoEncontrado = validador.verificarSeUsuarioPodeEmitirAIO();

			// Assert
			assertFalse(resultadoEncontrado);
		});
	}

	@Test
	@DisplayName(value = "Caso de Teste - Usuários que podem emitir AIO")
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
			ValidaPermissaoEmitirAIO validador = new ValidaPermissaoEmitirAIO(usuario);

			boolean resultadoEncontrado = validador.verificarSeUsuarioPodeEmitirAIO();

			// Assert
			assertTrue(resultadoEncontrado);
		});
	}
}
