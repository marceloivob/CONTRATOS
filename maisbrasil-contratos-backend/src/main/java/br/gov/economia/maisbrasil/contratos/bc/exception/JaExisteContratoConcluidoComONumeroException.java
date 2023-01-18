package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class JaExisteContratoConcluidoComONumeroException extends BusinessException {

	private static final long serialVersionUID = -2524682853221702661L;
	
	public JaExisteContratoConcluidoComONumeroException(String numero) {
		super(ErrorInfo.JA_EXISTE_CONTRATO_CONCLUIDO_COM_O_NUMERO, numero);
	}


}
