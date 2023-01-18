package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ExclusaoNaoPermitidaMedicaoIniciadaException extends BusinessException {

	private static final long serialVersionUID = 6035357195819661609L;

	public ExclusaoNaoPermitidaMedicaoIniciadaException() {
		super(ErrorInfo.EXCLUSAO_NAO_PERMITIDA_MEDICAO_INICIADA);
	}

}
