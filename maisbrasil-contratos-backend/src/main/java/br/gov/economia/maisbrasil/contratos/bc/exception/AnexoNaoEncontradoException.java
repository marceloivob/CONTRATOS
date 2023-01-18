package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class AnexoNaoEncontradoException extends BusinessException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 516282427277463526L;

	public AnexoNaoEncontradoException() {
        super(ErrorInfo.ANEXO_NAO_ENCONTRADO);
    }

}
