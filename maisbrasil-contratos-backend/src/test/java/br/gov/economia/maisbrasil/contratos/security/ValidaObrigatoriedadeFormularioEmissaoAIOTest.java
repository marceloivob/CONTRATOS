package br.gov.economia.maisbrasil.contratos.security;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.gov.economia.maisbrasil.contratos.SiconvUserMocker;

@DisplayName(value = "Caso de Teste - Obrigatoriedade Preenchimento Formulário Emissão de AIO")
class ValidaObrigatoriedadeFormularioEmissaoAIOTest {
	private static SiconvUserMocker siconvUserMocker;

	@BeforeAll
	static void setup() {
		siconvUserMocker = new SiconvUserMocker();
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
			ValidaObrigatoriedadeFormularioEmissaoAIO validador = new ValidaObrigatoriedadeFormularioEmissaoAIO(usuario);

			boolean resultadoEncontrado = validador.verificarSeUsuarioPrecisaPreencherFormularioEmissaoAIO();

			// Assert
			assertFalse(resultadoEncontrado);
		});
	}

	@Test
	@DisplayName(value = "Caso de Teste - Usuários que podem emitir AIO")
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
			ValidaObrigatoriedadeFormularioEmissaoAIO validador = new ValidaObrigatoriedadeFormularioEmissaoAIO(usuario);

			boolean resultadoEncontrado = validador.verificarSeUsuarioPrecisaPreencherFormularioEmissaoAIO();

			// Assert
			assertTrue(resultadoEncontrado);
		});
	}
}
