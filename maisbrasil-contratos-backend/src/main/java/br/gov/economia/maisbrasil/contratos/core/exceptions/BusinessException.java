package br.gov.economia.maisbrasil.contratos.core.exceptions;

import java.text.MessageFormat;

public class BusinessException extends MandatariasException {
    private static final long serialVersionUID = -8080210994849015208L;

    private final ErrorInfo errorInfo;

    public BusinessException(ErrorInfo errorInfo) {
        super(errorInfo.getMensagem());
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public BusinessException(ErrorInfo errorInfo, Object... param) {
        super(MessageFormat.format(errorInfo.getMensagem(), param));
        this.errorInfo = errorInfo;
    }

}
