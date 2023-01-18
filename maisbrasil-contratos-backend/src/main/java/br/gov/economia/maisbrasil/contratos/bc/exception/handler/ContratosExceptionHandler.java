package br.gov.economia.maisbrasil.contratos.bc.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.common.base.Throwables;

import br.gov.economia.maisbrasil.contratos.config.ApplicationContextProvider;
import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.Severity;
import io.jsonwebtoken.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebMvc
@ControllerAdvice
public class ContratosExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(ContratosExceptionHandler.class);

	private final ApplicationContext CONTEXT = ApplicationContextProvider.getApplicationContext();

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> retorno = new HashMap<>();
		retorno.put("message", "Malformed JSON request");
		return buildResponseEntity(retorno, HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Object> buildResponseEntity(Map<String, Object> mapa, HttpStatus status) {
		return new ResponseEntity<>(mapa, status);
	}

	@ExceptionHandler(value = BusinessException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException e, WebRequest request) {

		Map<String, Object> retorno = new HashMap<>();
		retorno.put("status", "fail");
		retorno.put("data", failData(e));

		log.debug("..........handleBusinessException...........");
		log.debug(e.getMessage());
		log.debug("retorno: {}", retorno);

		if (e.getErrorInfo().getHttpStatus() == null) {
			// Quando é alguma Business Exception, sem Status Code
			HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

			return new ResponseEntity<>(retorno, httpStatus);
		} else if (BAD_REQUEST.equals(e.getErrorInfo().getHttpStatus())) {
			// Quando é alguma Business Exception, mas com o Status Code == BAD_REQUEST
			return GenericErrorResponse.genericErrorHandler(e, e.getErrorInfo().getMensagem(),
					e.getErrorInfo().getHttpStatus());
		} else {
			// Quando é alguma Business Exception, com qualquer Status Code diferente de Null e BAD_REQUEST
			HttpStatus httpStatus = e.getErrorInfo().getHttpStatus();
			return new ResponseEntity<>(retorno, httpStatus);
		}

	}

	@ExceptionHandler(value = SQLException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleSQLException(SQLException e, WebRequest request) {
		boolean isProd = Arrays.asList(CONTEXT.getEnvironment().getActiveProfiles()).contains("prod");
		final String CODIGO_ERRO_CONCORRENCIA = "23501";
		// Existe mais de uma versão da exceção PSQLException no Classloader
		// Não foi identificado a origem dessa outra versão da classe
		// Isso trouxe como consequência não poder simplesmente usar o instanceof
		// PSQLException
		// Isso sempre retornava false.
		// Foi tentado o uso do cast (PSQLException) exception.getCause().
		// Neste cenário, era apresentado o erro de ClassCastException: PSQLException
		// não é uma classe do tipo PSQLException
		// ¯\_(ツ)_/¯
		// A solução aqui usada foi encontrada no seguinte link:
		// https://stackoverflow.com/questions/40301779/how-to-handle-a-psqlexception-in-java
		Throwable rootCause = Throwables.getRootCause(e.getCause());
		Map<String, Object> retorno = new HashMap<>();
		if (rootCause instanceof SQLException) {
			if (CODIGO_ERRO_CONCORRENCIA.equals(((SQLException) rootCause).getSQLState())) {

				if(!isProd) {
					retorno.put("status", "fail");
					retorno.put("data", failData(e));
				}

				return new ResponseEntity<>(retorno, HttpStatus.CONFLICT);
			} else {
				if(!isProd) {
					retorno.put("ex", e.getCause());
					retorno.put("trace", e.getStackTrace());
				}

				return new ResponseEntity<>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			if(!isProd) {
				retorno.put("ex", e.getCause());
				retorno.put("trace", e.getStackTrace());
			}

			return new ResponseEntity<>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ExceptionHandler(value = IOException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleIOException(IOException e, WebRequest request) {
		boolean isProd = Arrays.asList(CONTEXT.getEnvironment().getActiveProfiles()).contains("prod");
		HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		Map<String, Object> retorno = new HashMap<>();

		if(!isProd) {
			retorno.put("status", "fail");
			retorno.put("data", failData(e));
		}

		return new ResponseEntity<>(retorno, httpStatus);
	}

	private Map<String, Object> failData(BusinessException exception) {
		Map<String, Object> data = new HashMap<>();
		data.put("codigo", exception.getErrorInfo().getCodigo());
		data.put("message", exception.getMessage());
		data.put("severity", exception.getErrorInfo().getSeverity());

		return data;
	}

	private Map<String, Object> failData(Exception exception) {
		Map<String, Object> data = new HashMap<>();
		data.put("message", exception.getMessage());
		data.put("severity", Severity.ERROR);

		return data;
	}

}
