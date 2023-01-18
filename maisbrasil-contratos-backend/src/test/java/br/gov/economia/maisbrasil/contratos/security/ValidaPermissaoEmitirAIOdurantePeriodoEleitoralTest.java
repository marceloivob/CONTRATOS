package br.gov.economia.maisbrasil.contratos.security;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import br.gov.economia.maisbrasil.contratos.SiconvUserMocker;

@DisplayName(value = "Caso de Teste Permitir Emissão de AIO durante Período Eleitoral")
class ValidaPermissaoEmitirAIOdurantePeriodoEleitoralTest {

	private static SiconvUserMocker siconvUserMocker;

	@BeforeAll
	static void setup() {
		siconvUserMocker = new SiconvUserMocker();
	}

	@Test
	@DisplayName(value = "Caso de Teste - Usuários que não podem emitir AIO durante período eleitoral")
	void naoPodemEmitirAIOdurantePeriodoEleitoral() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioGuest());
		usuarios.add(siconvUserMocker.mockUsuarioProponente());
		usuarios.add(siconvUserMocker.mockUsuarioProponenteEditorContrato());

		// Act
		usuarios.forEach(usuario -> {
			ValidaPermissaoEmitirAIOdurantePeriodoEleitoral validador = new ValidaPermissaoEmitirAIOdurantePeriodoEleitoral(usuario);

			boolean resultadoEncontrado = validador.verificarSeUsuarioPodeEmitirAIOdurantePeriodoEleitoral();

			// Assert
			assertFalse(resultadoEncontrado);
		});
	}

	@Test
	@DisplayName(value = "Caso de Teste - Usuários que podem emitir AIO durante período eleitoral")
	void podemEmitirAIOdurantePeriodoEleitoral() {
		// Arrange
		List<SiconvUser> usuarios = new ArrayList<>();

		usuarios.add(siconvUserMocker.mockUsuarioAdministrador());
		usuarios.add(siconvUserMocker.mockUsuarioConcedente());
		usuarios.add(siconvUserMocker.mockUsuarioConcedenteGCC());
		usuarios.add(siconvUserMocker.mockUsuarioMandataria());
		usuarios.add(siconvUserMocker.mockUsuarioMandatariaGCIM());
		
		// Act
		usuarios.forEach(usuario -> {
			ValidaPermissaoEmitirAIOdurantePeriodoEleitoral validador = new ValidaPermissaoEmitirAIOdurantePeriodoEleitoral(usuario);

			boolean resultadoEncontrado = validador.verificarSeUsuarioPodeEmitirAIOdurantePeriodoEleitoral();

			// Assert
			assertTrue(resultadoEncontrado);
		});
	}
}
