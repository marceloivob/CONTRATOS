package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class HistoricoNaoEncontradoException extends BusinessException {
    
    public HistoricoNaoEncontradoException() {
        super(ErrorInfo.HISTORICO_NAO_ENCONTRADO);
    }

}
