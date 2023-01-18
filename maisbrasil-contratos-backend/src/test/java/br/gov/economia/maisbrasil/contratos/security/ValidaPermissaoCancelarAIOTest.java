package br.gov.economia.maisbrasil.contratos.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import br.gov.economia.maisbrasil.contratos.SiconvUserMocker;

@DisplayName(value = "Caso de Teste ao Cancelar Emissão de AIO")
class ValidaPermissaoCancelarAIOTest {

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
	@DisplayName(value = "Caso de Teste - Verifica se GUEST pode Cancelar Emissão de AIO")
	void verificaSeGuestPodeCancelarEmissaoAIO() {
		// Arrange
		SiconvUser usuarioLogado = siconvUserMocker.mockUsuarioGuest();

		// Act
		ValidaPermissaoCancelarAIO verificaPermissaoCancelarAIO = new ValidaPermissaoCancelarAIO(usuarioLogado);
		boolean resultadoEncontrado = verificaPermissaoCancelarAIO.verificarSeUsuarioPodeCancelarAIO();

		// Assert
		assertFalse(resultadoEncontrado);
	}

	@Test
	@DisplayName(value = "Caso de Teste - Verifica se Proponente pode Cancelar Emissão de AIO")
	void verificaSeProponentePodeCancelarEmissaoAIO() {
		// Arrange
		SiconvUser usuarioLogado = siconvUserMocker.mockUsuarioProponente();

		// Act
		ValidaPermissaoCancelarAIO verificaPermissaoCancelarAIO = new ValidaPermissaoCancelarAIO(usuarioLogado);
		boolean resultadoEncontrado = verificaPermissaoCancelarAIO.verificarSeUsuarioPodeCancelarAIO();

		// Assert
		assertFalse(resultadoEncontrado);
	}

	@Test
	@DisplayName(value = "Caso de Teste - Verifica se Concedente Não Administrador pode Cancelar Emissão de AIO")
	void verificaSeConcedenteNaoAdministradorPodeCancelarEmissaoAIO() {
		// Arrange
		SiconvUser usuarioLogado = siconvUserMocker.mockUsuarioConcedente();

		// Act
		ValidaPermissaoCancelarAIO verificaPermissaoCancelarAIO = new ValidaPermissaoCancelarAIO(usuarioLogado);
		boolean resultadoEncontrado = verificaPermissaoCancelarAIO.verificarSeUsuarioPodeCancelarAIO();

		// Assert
		assertFalse(resultadoEncontrado);
	}

	@Test
	@DisplayName(value = "Caso de Teste - Verifica se Concedente Administrador pode Cancelar Emissão de AIO")
	void verificaSeConcedenteAdministradorPodeCancelarEmissaoAIO() {

		// Arrange
		SiconvUser usuarioLogado = siconvUserMocker.mockUsuarioAdministrador();

		// Act
		ValidaPermissaoCancelarAIO verificaPermissaoCancelarAIO = new ValidaPermissaoCancelarAIO(usuarioLogado);
		boolean resultadoEncontrado = verificaPermissaoCancelarAIO.verificarSeUsuarioPodeCancelarAIO();

		// Assert
		assertTrue(resultadoEncontrado);
	}

	@Test
	@DisplayName(value = "Caso de Teste - Verifica se Mandataria Não Gestor de Convênio de Instituição Mandatária pode Cancelar Emissão de AIO")
	void verificaSeMandatariaNaoGestorPodeCancelarEmissaoAIO() {

		// Arrange
		SiconvUser usuarioLogado = siconvUserMocker.mockUsuarioMandataria();

		// Act
		ValidaPermissaoCancelarAIO verificaPermissaoCancelarAIO = new ValidaPermissaoCancelarAIO(usuarioLogado);
		boolean resultadoEncontrado = verificaPermissaoCancelarAIO.verificarSeUsuarioPodeCancelarAIO();

		// Assert
		assertFalse(resultadoEncontrado);
	}

	@Test
	@DisplayName(value = "Caso de Teste - Verifica se Mandataria Gestor de Convênio de Instituição Mandatária pode Cancelar Emissão de AIO")
	void verificaSeMandatariaGestorPodeCancelarEmissaoAIO() {

		// Arrange
		SiconvUser usuarioLogado = siconvUserMocker.mockUsuarioMandatariaGCIM();

		// Act
		ValidaPermissaoCancelarAIO verificaPermissaoCancelarAIO = new ValidaPermissaoCancelarAIO(usuarioLogado);
		boolean resultadoEncontrado = verificaPermissaoCancelarAIO.verificarSeUsuarioPodeCancelarAIO();

		// Assert
		assertTrue(resultadoEncontrado);
	}

}
