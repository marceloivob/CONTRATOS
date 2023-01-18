package br.gov.economia.maisbrasil.contratos.integracao.client;

import java.io.Serializable;

import javax.net.ssl.SSLException;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import br.gov.economia.maisbrasil.contratos.config.ApplicationContextProvider;
import br.gov.serpro.vrpl.grpc.client.VRPLGrpcClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VrplClientGRPCProducer implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -35470206989417202L;
	
	private static ApplicationContext CONTEXT = ApplicationContextProvider.getApplicationContext();

	private static VRPLGrpcClient vrplGrpcClient;
	
	public static VRPLGrpcClient create() throws SSLException {
		
		if(vrplGrpcClient != null) {
			return vrplGrpcClient;
		}
		
		Environment env = CONTEXT.getEnvironment();
		
		final String HOST_GRPC_VRPL = env.getProperty("integrations.PRIVATE.GRPC.VRPL.endpoint");
		final Integer PORTA_GRPC_VRPL = Integer.valueOf(env.getProperty("integrations.PRIVATE.GRPC.VRPL.port"));
		final boolean USE_SSL = Boolean.getBoolean(env.getProperty("integrations.PRIVATE.GRPC.VRPL.useSSL"));
		
		vrplGrpcClient = new VRPLGrpcClient(HOST_GRPC_VRPL, PORTA_GRPC_VRPL, USE_SSL);
		
		log.debug("Criando Client para o Siconv gRPC '{}' com o Host {} e Porta {} e SSL {}", vrplGrpcClient,
				HOST_GRPC_VRPL, PORTA_GRPC_VRPL, USE_SSL);
		
		return vrplGrpcClient;
		
	}
	
	public static void shutdown() throws Exception {
		if(vrplGrpcClient != null) {
			vrplGrpcClient.shutdown();
			vrplGrpcClient = null;
		}
	}
	
	
	
}
