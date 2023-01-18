package br.gov.serpro.grpc.services;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import br.gov.serpro.contratos.grpc.ListaFornecedoresRequest;
import br.gov.serpro.contratos.grpc.FornecedorContratosResponse;
import br.gov.serpro.contratos.grpc.FornecedorRequest;
import br.gov.serpro.contratos.grpc.services.ContratosService;

public class ContratosServiceTest {

	private ContratosService siconvService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		siconvService = new ContratosService();
	}

	@DisplayName("Consultar quantidade de contratos")
	@Test
	public void consultarQuantidadeContratos() {
		
//		ListaFornecedoresRequest request = ListaFornecedoresRequest.newBuilder().addCnpj("88256979000104").build();

		//FornecedorContratosResponse resultado = siconvService.consultarQTDContratos(request, responseObserver);

//		assertNotNull(resultado);
	}
	
	@DisplayName("Consultar contratos")
	@Test
	@Disabled
	public void consultarContratos() {
		
//		FornecedorRequest request = FornecedorRequest.newBuilder().setCnpj("88256979000104").build();

		//ContratosResponse resultado = siconvService.consultarContratos(request);

		//assertNotNull(resultado);
	}

}
