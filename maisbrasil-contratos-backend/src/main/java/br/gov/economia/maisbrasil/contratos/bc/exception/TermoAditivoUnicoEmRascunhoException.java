
package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class TermoAditivoUnicoEmRascunhoException extends BusinessException {
	
	private static final long serialVersionUID = 6945216220491908869L;
	
	public TermoAditivoUnicoEmRascunhoException() {
		super(ErrorInfo.TERMO_ADITIVO_UNICO_EM_RASCUNHO);
	}

}
