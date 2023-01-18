//package br.gov.serpro.contrato.grpc;
package br.gov.serpro.siconv.contratos.grpc.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.net.ssl.SSLException;

import br.gov.serpro.contratos.grpc.ContratoResponse;
import br.gov.serpro.contratos.grpc.ContratosGrpc;
import br.gov.serpro.contratos.grpc.ContratosGrpc.ContratosBlockingStub;
import br.gov.serpro.contratos.grpc.FornecedorContratos;
import br.gov.serpro.contratos.grpc.FornecedorContratosResponse;
import br.gov.serpro.contratos.grpc.FornecedorRequest;
import br.gov.serpro.contratos.grpc.IdContratoRequest;
import br.gov.serpro.contratos.grpc.IdPropostaRequest;
import br.gov.serpro.contratos.grpc.LicitacaoRequest;
import br.gov.serpro.contratos.grpc.LicitacaoResponse;
import br.gov.serpro.contratos.grpc.ListaContratosResponse;
import br.gov.serpro.contratos.grpc.ListaFornecedoresRequest;
import br.gov.serpro.contratos.grpc.ListaSubmetasResponse;
import br.gov.serpro.contratos.grpc.NumeroContratoLicitacao;
import br.gov.serpro.contratos.grpc.PropostaResponse;
import br.gov.serpro.siconv.contratos.grpc.dto.ContratoDetalhadoDTO;
import br.gov.serpro.siconv.contratos.grpc.dto.PropostaDTO;
import br.gov.serpro.siconv.contratos.grpc.dto.SubmetaDTO;
import br.gov.serpro.siconv.med.grpc.HealthCheckRequest;
import br.gov.serpro.siconv.med.grpc.HealthCheckResponse;
import br.gov.serpro.siconv.med.grpc.HealthGrpc;
import br.gov.serpro.siconv.med.grpc.HealthGrpc.HealthBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContratosGRPCClient {

	// ESTALEIRO
	public static final String HOST = System.getProperty("host", "localhost");

	public static final int PORT = Integer.parseInt(System.getProperty("CONTRATOS_GRPC_PORT", "50051"));

//	 public static final String HOST = System.getProperty("host",
//	 "nodes.estaleiro.serpro");
//	 public static final int PORT = Integer.parseInt(System.getProperty("port",
//	 "30287"));

//	 public static final String HOST = System.getProperty("host",
//	 "des-siconv-grpc.estaleiro.serpro.gov.br");
//	 public static final int PORT = Integer.parseInt(System.getProperty("port",
//	 "443"));

	public static final Boolean useSSLGRPC = Boolean.valueOf(System.getProperty("useSSLGRPC", "true"));

	private final ManagedChannel channel;

	private final ContratosBlockingStub blockingContratosStub;
	
	private final HealthBlockingStub blockingHealthCheckStub;

	/**
	 * Construindo um cliente para se conectar ao servidor em {@code host:port}. SSL
	 * ativo por padrao, sendo possivel configurar via variavel de execucao
	 * useSSLGRPC
	 * 
	 * @param host
	 * @param port
	 * @throws SSLException
	 */
	public ContratosGRPCClient(String host, int port) throws SSLException {

		this(useSSLGRPC ? ManagedChannelBuilder.forAddress(host, port).useTransportSecurity().build()
				: ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build());

		log.info("[CONTRATOS GRPC Client]: **Channel criado com sucesso na porta {} e no host {}", port, host);
	}

	/**
	 * Construindo um cliente para se conectar ao servidor em {@code host:port}. SSL
	 * configurado via parametro useSSL;
	 * 
	 * @param host
	 * @param port
	 * @param useSSL (true = Canal de conexao com SSL)
	 * @throws SSLException
	 */
	public ContratosGRPCClient(String host, int port, Boolean useSSL) throws SSLException {

		this(useSSL ? ManagedChannelBuilder.forAddress(host, port).useTransportSecurity().build()
				: ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build());

		log.info("[CONTRATOS GRPC Client Construtor SSL]: **Channel criado com sucesso na porta {} e no host {}", port,
				host);
	}

	ContratosGRPCClient(ManagedChannel channel) {
		this.channel = channel;
		blockingContratosStub = ContratosGrpc.newBlockingStub(channel);
		blockingHealthCheckStub = HealthGrpc.newBlockingStub(channel);
	}

	/**
	 * Finaliza execuÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o
	 * 
	 * @throws Exception
	 */
	public void shutdown() throws Exception {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public Optional<PropostaDTO> getPropostaPorId(long idProposta) {

		IdPropostaRequest request = IdPropostaRequest.newBuilder().setIdProposta(idProposta).build();

		try {
			PropostaResponse proposta = blockingContratosStub.consultarPropostaPorId(request);
			PropostaDTO propostaDTO = new PropostaDTO(proposta.getProposta());
			
			return Optional.of(propostaDTO);
		} catch (StatusRuntimeException sre) {
			if (sre.getStatus().getCode() == Status.NOT_FOUND.getCode()) {
				return Optional.empty();
			} else {
				throw sre;
			}
		}
	}

	public Optional<ContratoDetalhadoDTO> getContratoPorId(long idContrato) {

		IdContratoRequest request = IdContratoRequest.newBuilder().setIdContrato(idContrato).build();

		try {

			ContratoResponse contrato = blockingContratosStub.consultarContratoPorId(request);
			ContratoDetalhadoDTO contratoDTO = new ContratoDetalhadoDTO(contrato.getContrato());

			return Optional.of(contratoDTO);

		} catch (StatusRuntimeException sre) {
			if (sre.getStatus().getCode() == Status.NOT_FOUND.getCode()) {
				return Optional.empty();
			} else {
				throw sre;
			}
		}
	}

	public List<SubmetaDTO> getSubmetasPorIdContrato(long idContrato) {

		IdContratoRequest request = IdContratoRequest.newBuilder().setIdContrato(idContrato).build();
		
		List<SubmetaDTO> listaSubmetasDTO = new ArrayList<SubmetaDTO>();

		try {
			ListaSubmetasResponse listaSubmetas = blockingContratosStub.consultarSubmetasPorIdContrato(request);

			listaSubmetasDTO = listaSubmetas.getSubmetaList().stream().map(SubmetaDTO::new)
					.collect(Collectors.toList());

			return listaSubmetasDTO;
		} catch (StatusRuntimeException sre) {
			if (sre.getStatus().getCode() == Status.NOT_FOUND.getCode()) {
				return listaSubmetasDTO;
			} else {
				throw sre;
			}
		}
	}

	public List<ContratoDetalhadoDTO> getContratos(String cnpj) {

		FornecedorRequest request = FornecedorRequest.newBuilder().setCnpj(cnpj).build();
		
		List<ContratoDetalhadoDTO> listaContratosDTO = new ArrayList<ContratoDetalhadoDTO>();
		
		try {
		
			ListaContratosResponse listaContratos = blockingContratosStub.consultarContratosPorFornecedor(request);

			listaContratosDTO = listaContratos.getContratosList().stream()
					.map(ContratoDetalhadoDTO::new).collect(Collectors.toList());

			return listaContratosDTO;
			
		} catch (StatusRuntimeException sre) {
			if (sre.getStatus().getCode() == Status.NOT_FOUND.getCode()) {
				return listaContratosDTO;
			} else {
				throw sre;
			}			
		}
	}

	public List<String> listaContratosPorIdLicitacao(long idSiconv, String numeroAnoLicitacao) {

		boolean isExisteContrato = false;
		
		LicitacaoRequest request = LicitacaoRequest.newBuilder().setIdSiconv(idSiconv).setNumeroAnoLicitacao(numeroAnoLicitacao).build();
		
		List<String> listaNumerosContratos = new ArrayList<String>();			
			
		try {
			
			LicitacaoResponse listaContratos = blockingContratosStub.listaContratosLicitacao(request);

			for(NumeroContratoLicitacao numeroContrato:  listaContratos.getNumeroContratoLicitacaoList()) {
				listaNumerosContratos.add(numeroContrato.getNumeroContrato());
			}			

			return listaNumerosContratos;
			
		} catch (StatusRuntimeException sre) {
			if (sre.getStatus().getCode() == Status.NOT_FOUND.getCode()) {
				return listaNumerosContratos;
			} else {
				throw sre;
			}			
		}
	}	
	
	public Map<String, Integer> getQTDContratos(List<String> lista) {

		ListaFornecedoresRequest request = ListaFornecedoresRequest.newBuilder().addAllCnpj(lista).build();

		FornecedorContratosResponse listaFornecedoresQTD = blockingContratosStub.consultarQTDContratos(request);

		Map<String, Integer> mapaRetorno = new HashMap<String, Integer>();

		// Adicionando os valores no mapa de retorno
		if (listaFornecedoresQTD.getMapList().isEmpty()) {

			for (String cnpj : lista) {
				mapaRetorno.put(cnpj, 0);
			}

		} else {			
			
			int index;
			
			for(FornecedorContratos fornecedor :  listaFornecedoresQTD.getMapList()) {
				mapaRetorno.put(fornecedor.getCnpj(), fornecedor.getQtd());
			}
			
			for (String cnpj : lista) {
				if (!mapaRetorno.containsKey(cnpj)) {
					mapaRetorno.put(cnpj, 0);
				}
			}
		}
		return mapaRetorno;
	}

	
	/**
	 * Como chamar o gRPC a partir do Estaleiro (kubernets)
	 * 
	 * https://cloud.google.com/blog/topics/developers-practitioners/health-checking-your-grpc-servers-gke
	 * 
	 * Liveness Health Check Service
	 * 
	 * @return
	 */
	public HealthCheckResponse liveness() {
		
		HealthCheckRequest request = HealthCheckRequest.newBuilder().build();
		
		HealthCheckResponse healthCheck = blockingHealthCheckStub.check(request);

		return healthCheck;		
		
	}	

	/**
	 * Readiness Health Check Service
	 * 
	 * @return
	 */
	public HealthCheckResponse readiness() {
		
		HealthCheckRequest request = HealthCheckRequest.newBuilder().build();
		
		HealthCheckResponse healthCheck = blockingHealthCheckStub.ready(request);

		return healthCheck;
		
	}		
	
	
	
	
	public static void main(String[] args) throws Exception {
		String host = HOST;
		Integer porta = PORT;

		if (args.length > 0) {
			host = args[0];
			porta = Integer.parseInt(args[1]);
		}

		
		ContratosGRPCClient client = null;
		if (host != null && porta != null) {

			client = new ContratosGRPCClient(host, porta, false);


			System.out.println(client.liveness());
 
			System.out.println(client.readiness());		
			
//			List<String> isExisteContratos = client.listaContratosPorIdLicitacao(1291130L, "0032019");
//			System.out.println("ExisteContrato = " + isExisteContratos);
			
			// montar uma lista
//			List<String> lista = new ArrayList();
//			lista.add("02486144000125");
//			lista.add("07988367000104");
//			lista.add("07301160000110");
//			lista.add("21092400000144");
//			lista.add("04550949000116");
//			lista.add("18330394000101");	
//
//			System.out.println(" 1 " + lista.size());
//			
//			Map<String, Integer> contratosQtd = client.getQTDContratos(lista);
////			
//			System.out.println(" 2 " + contratosQtd.size());
//
//			for (String key : contratosQtd.keySet()) {
//				// Capturamos o valor a partir da chave
//				System.out.println(key + " = " + contratosQtd.get(key));
//			}

			/*
			 * getContratos - Listar os contratos de um fornecedor aptos para iniciar a
			 * medicao *
			 */
//			List<ContratoDetalhadoDTO> listaContratos = client.getContratos("02730635000170");
//			for (ContratoDetalhadoDTO contrato : listaContratos) {
//				System.out.println("Contrato = " + contrato.getNumero());
//				System.out.println("data de assinatura = " + contrato.getDtAssinatura());
//				System.out.println("valor total = " + contrato.getValorTotal());
//				System.out.println("tem mandataria = " + contrato.getPossuiInstituicaoMandataria());
//			}

//			Optional<ContratoDetalhadoDTO> contrato = client.getContratoPorId(5);
//			System.out.println("Contrato tem mandataria = " + contrato.map(ContratoDetalhadoDTO::getPossuiInstituicaoMandataria));
//			System.out.println("Contrato = " + contrato.map(ContratoDetalhadoDTO::getId));
//			System.out.println("data de assinatura = " + contrato.map(ContratoDetalhadoDTO::getDtAssinatura));
//			System.out.println("valor total = " + contrato.map(ContratoDetalhadoDTO::getValorTotal));

//			List<SubmetaDTO> lista = client.getSubmetasPorIdContrato(31);
//			for (SubmetaDTO submeta : lista) {
//				System.out.println("submeta nÃƒÆ’Ã‚Âºmero = " + submeta.getNumero());
//				System.out.println("submeta nÃƒÆ’Ã‚Âºmero = " + submeta.getId_vrpl());
//				System.out.println("descriÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o = " + submeta.getDescricao());
//				System.out.println("valor total = " + submeta.getValorTotal());
//			}

//			Optional<PropostaDTO> proposta = client.getPropostaPorId(1394279);
//			System.out.println("Proposta = " + proposta.map(PropostaDTO::getId));
//			System.out.println("Proposta tem Mandatária = " + proposta.map(PropostaDTO::getPossuiInstituicaoMandataria));

//			Optional<ContratoDetalhadoDTO> contrato = client.getContratoPorId(14);
//			System.out.println("Nome Proponente = " + contrato.map(ContratoDetalhadoDTO::getNomeProponente));

//			Optional<PropostaDTO> proposta = client.getPropostaPorId(1639607);
//			System.out.println("Proposta = " + proposta.map(PropostaDTO::getId));
//			System.out.println("Nome Proponente = " + proposta.map(PropostaDTO::getNomeProponente));
 
			List<ContratoDetalhadoDTO> listaContratos = client.getContratos("15688081000131");
			for (ContratoDetalhadoDTO contrato : listaContratos) {
				System.out.println("Teste de Contrato = " + contrato.getNomeProponente());
			}

		}
	}

}
