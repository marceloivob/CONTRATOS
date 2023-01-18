package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class AlteracaoParcialMedicaoIniciadaException extends BusinessException {

	private static final long serialVersionUID = -4785140606605927312L;
	
	public AlteracaoParcialMedicaoIniciadaException() {
		super(ErrorInfo.ALTERACAO_PARCIAL_MEDICAO_INICIADA);
	}


}
