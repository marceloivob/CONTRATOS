package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ConclusaoTermoAditivoSemAnexoPublicacaoTermoAditivoException extends BusinessException {
	
	private static final long serialVersionUID = -4784635219169260915L;

	public ConclusaoTermoAditivoSemAnexoPublicacaoTermoAditivoException() {
		super(ErrorInfo.CONCLUSAO_TERMO_ADITIVO_SEM_ANEXO_PUBLICACAO_TERMO_ADITIVO);
	}

}
