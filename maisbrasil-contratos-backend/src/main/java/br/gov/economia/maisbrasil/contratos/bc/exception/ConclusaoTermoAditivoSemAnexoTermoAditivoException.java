
package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ConclusaoTermoAditivoSemAnexoTermoAditivoException extends BusinessException {
	
	private static final long serialVersionUID = -3734936289911456928L;

	public ConclusaoTermoAditivoSemAnexoTermoAditivoException() {
		super(ErrorInfo.CONCLUSAO_TERMO_ADITIVO_SEM_ANEXO_TERMO_ADITIVO);
	}

}
