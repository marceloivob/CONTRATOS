package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ContratoNaoEncontradoException extends BusinessException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -1045586413933606200L;

	public ContratoNaoEncontradoException() {
        super(ErrorInfo.CONTRATO_NAO_ENCONTRADO);
    }

}
