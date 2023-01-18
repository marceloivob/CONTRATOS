package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ArquivoAnexoSemExtensaoException extends BusinessException {

    private static final long serialVersionUID = -6320356908245336900L;

    public ArquivoAnexoSemExtensaoException()  {
        super(ErrorInfo.ERRO_ANEXO_SEM_EXTENSAO);
    }

}
