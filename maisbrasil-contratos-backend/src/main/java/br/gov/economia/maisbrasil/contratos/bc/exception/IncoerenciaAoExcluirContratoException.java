package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class IncoerenciaAoExcluirContratoException extends BusinessException {
	
	private static final long serialVersionUID = -1647875890109030985L;

	public IncoerenciaAoExcluirContratoException() {
		super(ErrorInfo.INCOERENCIA_AO_EXCLUIR_CONTRATO);
	}

}
