package br.gov.serpro.contratos.grpc;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.stop;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import br.gov.serpro.contratos.grpc.services.ContratosService;
import br.gov.serpro.contratos.grpc.services.HealthService;
import io.grpc.MethodDescriptor;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrpcServer {

	public static final int CONTRATOS_GRPC_PORT = Integer.parseInt(System.getProperty("CONTRATOS_GRPC_PORT", "50051"));

	private io.grpc.Server server;
	
	private HealthService hc;

	private void startGRPCServer() throws IOException {

		log.info("Servidor gRPC iniciado na porta " + CONTRATOS_GRPC_PORT);
		
		hc = new HealthService();
		
		ServerBuilder<?> sb = ServerBuilder.forPort(CONTRATOS_GRPC_PORT);
		sb.addService(new ContratosService());
		sb.addService(hc); 
		
		server = sb.build().start();

		printServicesOutput();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				log.info("*** Parando servidor gRPC");

				GrpcServer.this.stopGrpcServer();

				log.info("*** Servidor gRPC parado");
			}
		});

	}

	/**
	 * Exibe no console a lista de Serviços e Métodos levantadas pelo servidor
	 */
	private void printServicesOutput() {

		List<ServerServiceDefinition> servicos = server.getServices();		
		
		for (ServerServiceDefinition servico : servicos) {
			log.info("*** Serviços ****");
			log.info(servico.getServiceDescriptor().getName());
			log.info("*** Métodos ****");

			Collection<MethodDescriptor<?, ?>> metodos = servico.getServiceDescriptor().getMethods();

			for (MethodDescriptor<?, ?> metodo : metodos) {
				log.info(metodo.getFullMethodName());
			}
		}
	}

	private void startHTTPServer() {
		registerReadiness();
		registerLiveness();
	}

	private void registerReadiness() {
		get("/health/ready", (request, response) -> {
			log.info("Serviço Readiness OK");
			
			hc.httpReady(response);
			
			return response.body();			
			
		});
	}

	private void registerLiveness() {
		get("/health/live", (request, response) -> {
			log.info("Serviço Liveness OK");
			
			hc.httpCheck(response);
			
			return response.body();			
			
		}); 
	}

	private void registerEndpointToShuttdown() {
		get("/shutdown", (request, response) -> {
			log.info("Desligando o servidor HTTP");

			this.stopGrpcServer();
			stop();
			return "";
		});
	}

//	private void registerConsultarPermissoesDasLicitacoesDaProposta() {
//		get("/licitacao/:idProposta", (request, response) -> {
//
//			Integer idProposta = Integer.valueOf(request.params(":idProposta"));
//			log.info("Serviço consultarPermissoesDasLicitacoesDaProposta para a Proposta: {}", idProposta);
//
//			PropostaRequest propostaRequest = PropostaRequest.newBuilder().setIdProposta(idProposta).build();
//
////			PermissoesDasLicitacoesResponse resultado = new SiconvService()
////					.consultarPermissoesDasLicitacoesDaProposta(propostaRequest);
//
//			Map<String, Object> resultadoAsMap = new HashMap<>();
//
//			for (Licitacao licitacao : resultado.getLicitacaoList()) {
//
//				Map<String, Object> mapInner = new HashMap<>();
//				mapInner.put("idDaLicitacao", licitacao.getIdDaLicitacao());
//				mapInner.put("alterar", licitacao.getAlterar());
//				mapInner.put("excluir", licitacao.getExcluir());
//				mapInner.put("estado", licitacao.getEstado());
//
//				resultadoAsMap.put("licitacao", mapInner);
//
//			}
//
//			log.info(resultadoAsMap.toString());
//
//			return resultadoAsMap;
//		});
//	}

	public void stopGrpcServer() {
		if (server != null) {
			server.shutdown();
		}
	}

	public void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	public Boolean isShutdown() {
		if (server != null) {
			return server.isShutdown();
		}

		return null;
	}

	public List<ServerServiceDefinition> getImmutableServices() {
		if (server != null) {
			return server.getImmutableServices();
		}

		return null;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		port(8002);

		final GrpcServer services = new GrpcServer();

		services.startGRPCServer();
		services.startHTTPServer();
		services.blockUntilShutdown();
	}

}
