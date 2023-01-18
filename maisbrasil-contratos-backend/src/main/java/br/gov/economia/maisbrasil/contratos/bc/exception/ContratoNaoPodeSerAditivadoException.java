
package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ContratoNaoPodeSerAditivadoException extends BusinessException {
	
	private static final long serialVersionUID = 1742718915988904441L;

	public ContratoNaoPodeSerAditivadoException() {
		super(ErrorInfo.CONTRATO_NAO_PODE_SER_ADITIVADO);
	}

}

