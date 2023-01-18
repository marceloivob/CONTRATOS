package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ServicoMedicaoIndisponivelException extends BusinessException {
	private static final long serialVersionUID = -6132843309085443029L;

	public ServicoMedicaoIndisponivelException() {
		super(ErrorInfo.SERVICO_MEDICAO_INDISPONIVEL);
	}

}
