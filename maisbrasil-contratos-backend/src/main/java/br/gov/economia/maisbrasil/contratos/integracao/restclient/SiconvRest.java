package br.gov.economia.maisbrasil.contratos.integracao.restclient;

public interface SiconvRest {

	AtualizarHistoricoResponse atualizarHistorico(Long propostaFk, String justificativa,
			boolean batch);

}