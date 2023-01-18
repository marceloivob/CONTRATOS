package br.gov.economia.maisbrasil.contratos.integracao.mail;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import br.gov.economia.maisbrasil.contratos.config.ApplicationContextProvider;

@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SenderEmailProducer {
	
	private final Logger log = LoggerFactory.getLogger(SenderEmailProducer.class);

	private SenderEmailMock fakeMail = new SenderEmailMock();

	private ConcreteSenderEmail realMail = new ConcreteSenderEmail();

	public SenderEmail create() {
		
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		List<String> perfis = Arrays.asList(context.getEnvironment().getDefaultProfiles());
		
		if(perfis.contains("dev")) {
			log.debug("Criando SenderEmail Mockado");
			return fakeMail;
		}

		log.debug("Criando SenderEmail concreto");
		return realMail;
	}
}
