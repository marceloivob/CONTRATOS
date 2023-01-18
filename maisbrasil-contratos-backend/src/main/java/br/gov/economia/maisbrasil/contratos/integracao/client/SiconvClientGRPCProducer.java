package br.gov.economia.maisbrasil.contratos.integracao.client;

import java.io.Serializable;

import javax.net.ssl.SSLException;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import br.gov.economia.maisbrasil.contratos.config.ApplicationContextProvider;
import br.gov.planejamento.siconv.grpc.SiconvGRPCClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SiconvClientGRPCProducer implements Serializable {

	private static final long serialVersionUID = -1722251162976069804L;
	
	private static ApplicationContext CONTEXT = ApplicationContextProvider.getApplicationContext();

	private static SiconvGRPCClient siconvGRPCCLient;
	
	public static SiconvGRPCClient create() throws SSLException {
		
		if(siconvGRPCCLient != null) {
			return siconvGRPCCLient;
		}
		
		Environment env = CONTEXT.getEnvironment();
		
		final String HOST_GRPC_CPS = env.getProperty("integrations.PRIVATE.GRPC.SICONV.endpoint");
		final Integer PORTA_GRPC_CPS = Integer.valueOf(env.getProperty("integrations.PRIVATE.GRPC.SICONV.port"));
		final boolean USE_SSL = Boolean.getBoolean(env.getProperty("integrations.PRIVATE.GRPC.SICONV.useSSL"));
		
		siconvGRPCCLient = new SiconvGRPCClient(HOST_GRPC_CPS, PORTA_GRPC_CPS, USE_SSL);
		
		log.debug("Criando Client para o Siconv gRPC '{}' com o Host {} e Porta {} e SSL {}", siconvGRPCCLient,
				HOST_GRPC_CPS, PORTA_GRPC_CPS, USE_SSL);
		
		return siconvGRPCCLient;
		
	}
	
	public static void shutdown() throws Exception {
		if(siconvGRPCCLient != null) {
			siconvGRPCCLient.shutdown();
			siconvGRPCCLient = null;
		}
	}

}
