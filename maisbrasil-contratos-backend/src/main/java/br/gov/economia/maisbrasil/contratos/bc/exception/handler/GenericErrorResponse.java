package br.gov.economia.maisbrasil.contratos.bc.exception.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.gov.economia.maisbrasil.contratos.security.SecurityUtils;
import br.gov.economia.maisbrasil.contratos.security.SiconvUser;
import io.sentry.Sentry;
import io.sentry.event.UserBuilder;

@Service
public class GenericErrorResponse {

	private static final Logger log = LoggerFactory.getLogger(GenericErrorResponse.class);

	private static GeradorDeTicket geradorDeTicket;

	@Autowired
	public GenericErrorResponse(GeradorDeTicket geradorDeTicket) {
		GenericErrorResponse.geradorDeTicket = geradorDeTicket;
	}

	public static ResponseEntity<Map<String, Object>> genericErrorHandler(Throwable t, String message,
			HttpStatus status) {
		String cpf = "NA";
		Object roles = "NA";
		Object idProposta = "NA";

		Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		
		if (usuarioAutenticado != null) {
			SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();

			if (usuarioLogado != null) {
				cpf = usuarioLogado.getCpf();
				roles = usuarioLogado.getRoles();
				idProposta = usuarioLogado.getIdProposta();
			}
		}
		
		Sentry.getContext().setUser(new UserBuilder().setUsername(cpf).setData(Collections.singletonMap("info", roles)).build());

		String ticket = geradorDeTicket.generateNewTicket();
		String rootMessage = ExceptionUtils.getRootCauseMessage(t);
		String trace = ExceptionUtils.getStackTrace(t);

		Sentry.getContext().addExtra("ticket", ticket);
		Sentry.getContext().addExtra("proposta", idProposta);

		log.error(String.format("[Contratos:Error] [Ticket: %s] [Motivo: %s] [Trace: %s]", ticket, rootMessage, trace),
				t);

		Sentry.capture(t);

		if (status == null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return getResponse(status, getResponseBody(ticket, rootMessage, trace, message));
	}

	private static ResponseEntity<Map<String, Object>> getResponse(HttpStatus status, Map<String, Object> body) {

		return new ResponseEntity<>(body, status);
	}

	private static Map<String, Object> getResponseBody(String ticket, String rootMessage, String trace,
			String message) {
		HashMap<String, Object> retorno = new HashMap<>();
		retorno.put("status", "error");
		retorno.put("message", message);
		retorno.put("ticket", ticket);

//		List<Stage> ambientesDesenvolvimento = Arrays.asList(Stage.LOCAL, Stage.DEVELOPMENT);
//
//		if (ambientesDesenvolvimento.contains(STAGE)) {
//			retorno.put("environment", STAGE);
//
//			retorno.put("rootMessage", rootMessage);
//
//			if (trace != null) {
//				retorno.put("trace", trace);
//			}
//		}

		return retorno;
	}
}
