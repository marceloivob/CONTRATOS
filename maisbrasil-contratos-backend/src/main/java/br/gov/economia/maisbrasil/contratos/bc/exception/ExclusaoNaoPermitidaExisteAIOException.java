package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ExclusaoNaoPermitidaExisteAIOException extends BusinessException  {

	private static final long serialVersionUID = 6367854341743414137L;

	public ExclusaoNaoPermitidaExisteAIOException() {
		super(ErrorInfo.EXCLUSAO_NAO_PERMITIDA_EXISTE_AIO);		
	}

}
