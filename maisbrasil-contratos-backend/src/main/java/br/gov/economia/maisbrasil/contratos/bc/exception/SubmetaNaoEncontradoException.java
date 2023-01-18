package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class SubmetaNaoEncontradoException extends BusinessException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SubmetaNaoEncontradoException() {
        super(ErrorInfo.SUBMETA_NAO_ENCONTRADO);
    }

}
