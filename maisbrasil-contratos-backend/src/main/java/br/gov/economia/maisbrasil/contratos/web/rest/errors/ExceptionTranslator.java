package br.gov.economia.maisbrasil.contratos.web.rest.errors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import com.google.common.base.Throwables;

import br.gov.economia.maisbrasil.contratos.bc.exception.handler.GenericErrorResponse;
import br.gov.economia.maisbrasil.contratos.core.exceptions.BusinessException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.Severity;
import io.github.jhipster.web.util.HeaderUtil;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

	private static final String FIELD_ERRORS_KEY = "fieldErrors";
	private static final String MESSAGE_KEY = "message";
	private static final String PATH_KEY = "path";
	private static final String VIOLATIONS_KEY = "violations";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);

	@ExceptionHandler(value = BusinessException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
     public ResponseEntity<Map<String,Object>> handleBusinessException(BusinessException e, 
    		 NativeWebRequest request) {
     	

		log.debug("..........handleBusinessException...........");
		log.debug(e.getMessage());

		HttpStatus httpStatus;
		Map<String, Object> retorno = new HashMap<String, Object>();
		retorno.put("status", "fail");
		retorno.put("data", failData(e));

		if (e.getErrorInfo().getHttpStatus() == null) {

			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

		} else if (BAD_REQUEST.equals(e.getErrorInfo().getHttpStatus())) {

			httpStatus = HttpStatus.BAD_REQUEST;

			return GenericErrorResponse.genericErrorHandler(e, e.getErrorInfo().getMensagem(),
					e.getErrorInfo().getHttpStatus());
		} else {
			httpStatus = e.getErrorInfo().getHttpStatus();
		}
		log.debug("retorno: {}", retorno);
		return new ResponseEntity<>(retorno, httpStatus);
	}

	@ExceptionHandler(value = RuntimeException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException e, NativeWebRequest request) {

		log.debug("..........handleRuntimeException...........");
		log.debug(e.getMessage());

		HttpStatus httpStatus;
		Map<String, Object> retorno = new HashMap<String, Object>();
		retorno.put("status", "fail");
		retorno.put("data", failData(e));

		httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		return GenericErrorResponse.genericErrorHandler(e, e.getMessage(), httpStatus);
	}

	@ExceptionHandler(value = { SQLException.class })
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
     public ResponseEntity<Map<String,Object>> handleSQLException(SQLException e, 
     		NativeWebRequest request) {
		log.debug("..........handleSQLException...........");
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
		HashMap<String, Object> retorno = new HashMap<>();
		if (rootCause instanceof SQLException) {
			if (CODIGO_ERRO_CONCORRENCIA.equals(((SQLException) rootCause).getSQLState())) {

				retorno.put("status", "fail");
				retorno.put("data", failData(e));

				return new ResponseEntity<>(retorno, HttpStatus.CONFLICT);
			} else {
				retorno.put("ex", e.getCause());
				retorno.put("trace", e.getStackTrace());
				return new ResponseEntity<>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			retorno.put("ex", e.getCause());
			retorno.put("trace", e.getStackTrace());
			return new ResponseEntity<>(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private HashMap<String, Object> failData(BusinessException exception) {
		HashMap<String, Object> data = new HashMap<>();
		data.put("codigo", exception.getErrorInfo().getCodigo());
		data.put("message", exception.getMessage());
		data.put("severity", exception.getErrorInfo().getSeverity());

		return data;
	}

	private HashMap<String, Object> failData(Exception exception) {
		HashMap<String, Object> data = new HashMap<>();
		data.put("message", exception.getMessage());
		data.put("severity", Severity.ERROR);

		return data;
	}

	@Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
		log.debug("..........MethodArgumentNotValidException...........");
		BindingResult result = ex.getBindingResult();
		List<FieldErrorVM> fieldErrors = result.getFieldErrors().stream()
				.map(f -> new FieldErrorVM(f.getObjectName().replaceFirst("DTO$", ""), f.getField(), f.getCode()))
				.collect(Collectors.toList());

        Problem problem = Problem.builder()
            .withType(ErrorConstants.CONSTRAINT_VIOLATION_TYPE)
            .withTitle("Method argument not valid")
            .withStatus(defaultConstraintViolationStatus())
            .with(MESSAGE_KEY, ErrorConstants.ERR_VALIDATION)
            .with(FIELD_ERRORS_KEY, fieldErrors)
            .build();
		return create(ex, problem, request);
	}

	@ExceptionHandler
	public ResponseEntity<Problem> handleNoSuchElementException(NoSuchElementException ex, NativeWebRequest request) {
		log.debug("..........handleNoSuchElementException...........");
        Problem problem = Problem.builder()
            .withStatus(Status.NOT_FOUND)
            .with(MESSAGE_KEY, ErrorConstants.ENTITY_NOT_FOUND_TYPE)
            .build();
		return create(ex, problem, request);
	}

	@ExceptionHandler
    public ResponseEntity<Problem> handleBadRequestAlertException(BadRequestAlertException ex, NativeWebRequest request) {
		log.debug("..........handleBadRequestAlertException...........");
        return create(ex, request, HeaderUtil.createFailureAlert(applicationName, true, ex.getEntityName(), ex.getErrorKey(), ex.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
		log.debug("..........ConcurrencyFailureException...........");
        Problem problem = Problem.builder()
            .withStatus(Status.CONFLICT)
            .with(MESSAGE_KEY, ErrorConstants.ERR_CONCURRENCY_FAILURE)
            .build();
		return create(ex, problem, request);
	}
}
