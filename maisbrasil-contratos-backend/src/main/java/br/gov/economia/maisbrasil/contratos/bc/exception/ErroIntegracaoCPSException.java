package br.gov.economia.maisbrasil.contratos.bc.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.economia.maisbrasil.contratos.core.exceptions.MandatariasException;

public class ErroIntegracaoCPSException extends MandatariasException {

	private static final long serialVersionUID = -442571650060089593L;
	private static final Logger log = LoggerFactory.getLogger(ErroIntegracaoCPSException.class.getName());

	public ErroIntegracaoCPSException(Exception e) {
		super("Erro ao tentar acessar o serviço GRPC do CPS para consulta do Nível da Proposta.");
		log.error("Erro ao tentar acessar o serviço GRPC do CPS para consulta do Nível da Proposta.", e);
	}

}
