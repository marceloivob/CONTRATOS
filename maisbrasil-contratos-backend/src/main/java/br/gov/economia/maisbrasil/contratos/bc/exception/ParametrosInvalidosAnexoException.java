package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ParametrosInvalidosAnexoException extends BusinessException {

    private static final long serialVersionUID = 6205721339467937010L;

    public ParametrosInvalidosAnexoException() {
        super(ErrorInfo.ERRO_PARAMETROS_DO_ANEXO_INVALIDOS);
    }

}
