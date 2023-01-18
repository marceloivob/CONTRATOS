package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class DocComplementarNaoEncontradoException extends BusinessException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DocComplementarNaoEncontradoException() {
        super(ErrorInfo.DOCCOMPLEMENTAR_NAO_ENCONTRADO);
    }

}
