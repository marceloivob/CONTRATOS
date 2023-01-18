package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ChecklistNaoOkException extends BusinessException {
    
	private static final long serialVersionUID = 2708094068561645285L;

	public ChecklistNaoOkException() {
        super(ErrorInfo.CHECKLIST_NAO_OK);
    }

}
