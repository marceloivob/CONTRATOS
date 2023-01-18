package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;

public class ServicoSiconvIndisponivelException  extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6655443327542902379L;

	public ServicoSiconvIndisponivelException() {
		super(ErrorInfo.SERVICO_SICONV_INDISPONIVEL);
		// TODO Auto-generated constructor stub
	}

}
