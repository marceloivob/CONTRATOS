package br.gov.economia.maisbrasil.contratos.bc.exception;

import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LicitacaoNaoEncontradaPropostaException extends BusinessException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2976186079422930751L;

	public LicitacaoNaoEncontradaPropostaException(Long idProposta) {
		super(ErrorInfo.ERRO_LICITACAO_NAO_ENCONTRADA_PROPOSTA, idProposta);
		log.info("Mensagem: [{}], detalhe: [IdProposta: {}]", ErrorInfo.ERRO_LICITACAO_NAO_ENCONTRADA_PROPOSTA, idProposta);

	}

}
