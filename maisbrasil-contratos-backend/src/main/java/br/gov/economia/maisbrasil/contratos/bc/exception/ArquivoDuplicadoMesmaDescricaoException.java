package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ArquivoDuplicadoMesmaDescricaoException extends BusinessException {

    private static final long serialVersionUID = 7210766975623617077L;

    public ArquivoDuplicadoMesmaDescricaoException() {
        super(ErrorInfo.ERRO_EXISTE_ANEXO_COM_MESMA_DESCRICAO);
    }

}
