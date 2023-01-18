package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class JaExisteTermoAditivoComONumeroException extends BusinessException {

	private static final long serialVersionUID = 5184665719445530950L;

	public JaExisteTermoAditivoComONumeroException(String numero) {
		super(ErrorInfo.JA_EXISTE_TERMO_ADITIVO_COM_O_NUMERO, numero);
	}


}
