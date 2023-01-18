package br.gov.economia.maisbrasil.contratos.core.exceptions;

public class EmptyProfileException extends BusinessException {

    public EmptyProfileException() {
        super(ErrorInfo.ERRO_ACESSO_PERFIL_NAO_INFORMADO);
    }

}
