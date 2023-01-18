package br.gov.economia.maisbrasil.contratos.integracao.restclient;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import br.gov.economia.maisbrasil.contratos.bc.exception.handler.GeradorDeTicket;
import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.ErrorInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AcionarServicoSiconvException extends BusinessException {

	private static final long serialVersionUID = 72197312351403296L;

	// Registro do Motivo do Erro
	private String mensagemDeErroFormatada;

	public AcionarServicoSiconvException(String servico, Object parametros, Response response) {
		super(ErrorInfo.ERRO_AO_ACIONAR_SERVICO_SICONV);

		tratarResposta(servico, parametros, response);
	}

	/**
	 * 400 - Erro na requisição (parâmetros obrigatórios ausentes ou inválidos) <br>
	 * 401 - Erro de autenticação (Token expirado, inválido, etc) <br>
	 * 403 - Erro de autorização (Usuario sem permissão para a funcionalidade) <br>
	 * 406 - Erro na definição do Cabeçalho HTTP: Accept <br>
	 * 422 - Erro de negocio qualquer (LicitacaoException) <br>
	 * 500 - Erro Interno do Servidor
	 */
	private void tratarResposta(String servico, Object parametros, Response response) {

		String motivo = "";
		if (temErroNaRequisicao(response)) {
			motivo = "Erro na requisição (parâmetros obrigatórios ausentes ou inválidos)";
		} else if (temErroDeAutenticacao(response)) {
			motivo = "Erro de autenticação (Token expirado, inválido, etc)";
		} else if (temErroDeAutorizacao(response)) {
			motivo = "Erro de autorização (Usuario sem permissão para a funcionalidade)";
		} else if (temErroDeNegocioNoSICONV(response)) {
			motivo = "Erro de negocio qualquer (LicitacaoException)";
		} else if (temErroInternoDoServidorNoSICONV(response)) {
			motivo = "Erro Interno do Servidor (RuntimeException)";
		} else if (naoFoiAceito(response)) {
			motivo = "Erro na definição do Cabeçalho HTTP: Accept";
		} else {
			motivo = "Erro desconhecido na integração com o SICONV gRPC";
		}

		GeradorDeTicket ticketGenerador = new GeradorDeTicket();
		String ticket = ticketGenerador.generateNewTicket();

		final String mensagem = "Mensagem: [%s], detalhe: [Serviço: %s, Parâmetros: %s, Status Code: %s, Motivo: %s - %s, Ticket: %s]";

		Gson gson = new Gson();

		this.mensagemDeErroFormatada = String.format(mensagem, ErrorInfo.ERRO_AO_ACIONAR_SERVICO_SICONV, servico,
				gson.toJson(parametros), response.getStatus(), motivo, response.readEntity(String.class), ticket);
		log.error(this.mensagemDeErroFormatada);
	}

	protected boolean temErroNaRequisicao(Response response) {
		return response.getStatus() == Status.BAD_REQUEST.getStatusCode(); // HTTP Status 400
	}

	protected boolean temErroDeAutenticacao(Response response) {
		return response.getStatus() == Status.UNAUTHORIZED.getStatusCode(); // HTTP Status 401
	}

	protected boolean temErroDeNegocioNoSICONV(Response response) {
		final int UNPROCESSABLE_ENTITY = 422;

		return response.getStatus() == UNPROCESSABLE_ENTITY;
	}

	protected boolean temErroDeAutorizacao(Response response) {
		return response.getStatus() == Status.FORBIDDEN.getStatusCode(); // HTTP Status 403
	}

	protected boolean naoFoiAceito(Response response) {
		return response.getStatus() == Status.NOT_ACCEPTABLE.getStatusCode();// HTTP Status 406
	}

	protected boolean temErroInternoDoServidorNoSICONV(Response response) {
		return response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode(); // HTTP Status 500
	}

	public String getMotivo() {
		return mensagemDeErroFormatada;
	}

}
