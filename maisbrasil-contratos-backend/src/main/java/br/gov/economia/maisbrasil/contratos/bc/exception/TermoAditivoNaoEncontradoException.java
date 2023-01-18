package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class TermoAditivoNaoEncontradoException extends BusinessException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TermoAditivoNaoEncontradoException() {
        super(ErrorInfo.TERMOADITIVO_NAO_ENCONTRADO);
    }

}
