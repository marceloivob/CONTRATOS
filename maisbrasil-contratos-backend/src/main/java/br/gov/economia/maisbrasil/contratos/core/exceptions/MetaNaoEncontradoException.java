package br.gov.economia.maisbrasil.contratos.core.exceptions;

public class MetaNaoEncontradoException extends BusinessException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MetaNaoEncontradoException() {
        super(ErrorInfo.META_NAO_ENCONTRADO);
    }

}
