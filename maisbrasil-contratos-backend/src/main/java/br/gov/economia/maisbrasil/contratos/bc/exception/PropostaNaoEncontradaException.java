package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class PropostaNaoEncontradaException extends BusinessException {

	private static final long serialVersionUID = -1045586413933606200L;

	public PropostaNaoEncontradaException() {
		super(ErrorInfo.PROPOSTA_NAO_ENCONTRADA);
	}

}
