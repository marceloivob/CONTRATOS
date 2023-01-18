package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class SubmetaDocComplementarNaoEncontradoException extends BusinessException {
    
    public SubmetaDocComplementarNaoEncontradoException() {
        super(ErrorInfo.SUBMETADOCCOMPLEMENTAR_NAO_ENCONTRADO);
    }

}
