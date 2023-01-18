package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SiconvRestStub implements SiconvRest {
	
	@Override
	public AtualizarHistoricoResponse atualizarHistorico(Long propostaFk, String justificativa,
			boolean batch) {
		
		log.info(
				"Integração com o serviço atualizarHistorico, com os parâmetros: propostaFk: {}, justificativa: {}, batch: {}",
				propostaFk, justificativa, false);
		
		return new AtualizarHistoricoResponse(Boolean.TRUE);
	}

}
