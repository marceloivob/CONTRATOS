package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class TamanhoMaximoArquivoInvalidoException extends BusinessException {

    private static final long serialVersionUID = -9171953282568254389L;

    public TamanhoMaximoArquivoInvalidoException() {
        super(ErrorInfo.ERRO_TAMANHO_MAXIMO_ANEXO_EXCEDIDO);
    }

}
