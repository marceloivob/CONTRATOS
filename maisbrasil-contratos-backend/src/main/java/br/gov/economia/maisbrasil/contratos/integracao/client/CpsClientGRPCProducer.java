package br.gov.economia.maisbrasil.contratos.integracao.client;

import java.io.Serializable;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import br.gov.economia.maisbrasil.contratos.config.ApplicationContextProvider;
import br.gov.planejamento.siconv.grpc.CpsGRPCClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CpsClientGRPCProducer implements Serializable {

	private static final long serialVersionUID = -1116612315068129007L;
	
	private static ApplicationContext CONTEXT = ApplicationContextProvider.getApplicationContext();
	
	private static CpsGRPCClient clientCpsGRPC;
	
	public static CpsGRPCClient create() throws SSLException {
		
		if(clientCpsGRPC != null) {
			return clientCpsGRPC;
		}
		
		Environment env = CONTEXT.getEnvironment();
		
		final String HOST_GRPC_CPS = env.getProperty("integrations.PRIVATE.GRPC.CPS.endpoint");
		final Integer PORTA_GRPC_CPS = Integer.valueOf(env.getProperty("integrations.PRIVATE.GRPC.CPS.port"));
		final boolean USE_SSL = Boolean.getBoolean(env.getProperty("integrations.PRIVATE.GRPC.CPS.useSSL"));
		
		clientCpsGRPC = new CpsGRPCClient(HOST_GRPC_CPS, PORTA_GRPC_CPS, USE_SSL);
		
		log.debug("Criando Client para o CPS gRPC '{}' com o Host {} e Porta {} e SSL {}", clientCpsGRPC,
				HOST_GRPC_CPS, PORTA_GRPC_CPS, USE_SSL);
		
		return clientCpsGRPC;
		
	}
	
	public static void shutdown() throws Exception {
		if(clientCpsGRPC != null) {
			clientCpsGRPC.shutdown();
			clientCpsGRPC = null;
		}
	}

}
