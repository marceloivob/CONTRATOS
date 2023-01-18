package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ContratoPossuiDatasInvalidasException extends BusinessException {

	private static final long serialVersionUID = -5269259915275458708L;

	public ContratoPossuiDatasInvalidasException(ErrorInfo errorInfo, String data1, String data2) {
		super(errorInfo, data1, data2);
	}


}


 