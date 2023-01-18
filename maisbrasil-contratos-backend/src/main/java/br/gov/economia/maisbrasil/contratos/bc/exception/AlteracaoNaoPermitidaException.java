package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class AlteracaoNaoPermitidaException extends BusinessException {
	private static final long serialVersionUID = 2593268803162846789L;

	public AlteracaoNaoPermitidaException(String labelItensPendentes) {
		super(ErrorInfo.ALTERACAO_NAO_PERMITIDA, labelItensPendentes);
	}

}
