package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class FornecedorNaoEncontradoException extends BusinessException {
    
    public FornecedorNaoEncontradoException() {
        super(ErrorInfo.FORNECEDOR_NAO_ENCONTRADO);
    }

}
