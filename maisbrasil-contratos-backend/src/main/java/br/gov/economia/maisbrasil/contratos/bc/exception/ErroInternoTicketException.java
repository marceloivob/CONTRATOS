package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ErroInternoTicketException extends BusinessException {
	
	private static final long serialVersionUID = 8126395806081476456L;
	
	public ErroInternoTicketException(String ticket) {
		super(ErrorInfo.ERRO_INTERNO_TICKET, ticket);
	}

}
