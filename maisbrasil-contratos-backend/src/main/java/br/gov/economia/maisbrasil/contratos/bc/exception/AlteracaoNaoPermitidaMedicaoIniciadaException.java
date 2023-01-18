package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class AlteracaoNaoPermitidaMedicaoIniciadaException extends BusinessException {
	private static final long serialVersionUID = -822334743159139941L;

	public AlteracaoNaoPermitidaMedicaoIniciadaException() {
		super(ErrorInfo.ALTERACAO_NAO_PERMITIDA_MEDICAO_INICIADA);
	}

}
