package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class LicitacaoAnexoNotFoundException extends BusinessException {

    private static final long serialVersionUID = -8454279209481968046L;

    public LicitacaoAnexoNotFoundException() {
        super(ErrorInfo.ERRO_LICITACAO_DO_ANEXO_NAO_ENCONTRADA);
    }

}
