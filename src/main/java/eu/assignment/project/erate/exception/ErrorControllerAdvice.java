package eu.assignment.project.erate.exception;

import com.google.common.base.CaseFormat;
import eu.assignment.project.erate.common.gen.model.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
public class ErrorControllerAdvice {
	private static final Logger logger =
			LoggerFactory.getLogger(ErrorControllerAdvice.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Error> handleException(
			MethodArgumentNotValidException e, HttpServletRequest request) {
		logExceptionTrace(e, request);


		StringBuilder errorStackBuilder = new StringBuilder("Validation Errors [");
		AtomicInteger counter= new AtomicInteger(1);
		e.getBindingResult().getAllErrors().forEach(error ->
				errorStackBuilder.append(counter.getAndIncrement())
						.append(". { FiledName='")
						.append(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, ((FieldError) error).getField()))
						.append("' RejectedValue='")
						.append(((FieldError) error).getRejectedValue())
						.append("' Message='")
						.append(error.getDefaultMessage())
						.append("'} ")
		);
		errorStackBuilder.append("]");

		return createResponse(HttpStatus.BAD_REQUEST, errorStackBuilder.toString());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Error> handleException(
			MethodArgumentTypeMismatchException e, HttpServletRequest request) {
		logExceptionTrace(e, request);
		return createResponse(HttpStatus.BAD_REQUEST,
				String.format("Parameter '%s', %s", e.getName(), e.getMessage()));
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Error> handleException(
			NoHandlerFoundException e, HttpServletRequest request) {
		logExceptionTrace(e, request);
		return createResponse(HttpStatus.NOT_FOUND,
				"The service API is not available, kindly validate the URL and try again.");
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<Error> handleException(
			HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
		logExceptionTrace(e, request);
		return createResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				"Invalid Content-Type provided. Kindly provide application/json as the Media type for the request.");
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Error> handleException(
			HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
		logExceptionTrace(e, request);
		return createResponse(HttpStatus.METHOD_NOT_ALLOWED,
				"Requested method is not allowed for the API.");
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Error> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException e, HttpServletRequest request) {
		logExceptionTrace(e, request);
		return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Error> handleException(Exception e, HttpServletRequest request) {
		logExceptionTrace(e, request);
		return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(CustomBusinessException.class)
	public ResponseEntity<Error> handleException(
			CustomBusinessException e, HttpServletRequest request) {
		logExceptionTrace(e, request);
		return createResponse(e.getHttpStatus(), e.getMessage());
	}


	private void logExceptionTrace(Exception e, HttpServletRequest request) {
		logger.warn("Exception occurred for request={}, errorType={}, error={}",
				request.getRequestURI(), e.getClass().getSimpleName(), e.getMessage());
		logger.debug("ExceptionTrace=", e);
	}


	private ResponseEntity<Error> createResponse(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(new Error().code(httpStatus.value()).message(message), httpStatus);
	}

}
