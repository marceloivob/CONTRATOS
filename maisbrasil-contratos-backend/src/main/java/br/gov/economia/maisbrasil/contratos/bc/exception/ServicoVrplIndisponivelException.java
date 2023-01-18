package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ServicoVrplIndisponivelException  extends BusinessException {

	public ServicoVrplIndisponivelException() {
		super(ErrorInfo.SERVICO_VRPL_INDISPONIVEL);
	}

	private static final long serialVersionUID = -334814708541425600L;

}
