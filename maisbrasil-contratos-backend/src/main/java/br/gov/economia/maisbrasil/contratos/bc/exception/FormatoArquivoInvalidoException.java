package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class FormatoArquivoInvalidoException extends BusinessException {

    private static final long serialVersionUID = 7018433494804392899L;

    public FormatoArquivoInvalidoException() {
        super(ErrorInfo.ERRO_FORMATO_ANEXO_NAO_PERMITIDO);
    }

}
