package br.gov.economia.maisbrasil.contratos.core.exceptions;

public class CampoObrigatorioException extends BusinessException {

    private static final long serialVersionUID = 8137929452463827245L;

    public CampoObrigatorioException() {
        super(ErrorInfo.ERRO_CAMPO_OBRIGATORIO);
    }

}
