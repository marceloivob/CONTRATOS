package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ArquivoDuplicadoMesmoNomeException extends BusinessException {

    private static final long serialVersionUID = 7525895077418132260L;

    public ArquivoDuplicadoMesmoNomeException() {
        super(ErrorInfo.ERRO_EXISTE_ANEXO_COM_MESMO_NOME);
    }

}
