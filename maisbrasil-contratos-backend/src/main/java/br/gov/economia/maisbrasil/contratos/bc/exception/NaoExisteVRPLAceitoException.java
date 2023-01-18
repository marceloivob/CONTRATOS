package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NaoExisteVRPLAceitoException extends BusinessException {

	private static final long serialVersionUID = 5908161420056274305L;

	public NaoExisteVRPLAceitoException(Long idProposta) {
		super(ErrorInfo.ERRO_PROPOSTA_SEM_VRPL_ACEITO);

		log.info("Mensagem: [{}], detalhe: [IdProposta: {}]", ErrorInfo.ERRO_PROPOSTA_SEM_VRPL_ACEITO, idProposta);
	}

}
