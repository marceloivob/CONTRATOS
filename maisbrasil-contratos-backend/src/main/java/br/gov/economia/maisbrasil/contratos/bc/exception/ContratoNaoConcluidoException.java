package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ContratoNaoConcluidoException extends BusinessException {

	private static final long serialVersionUID = -6066753243196719944L;
	
	public ContratoNaoConcluidoException() {
		super(ErrorInfo.CONTRATO_NAO_CONCLUIDO);	
	}


}
