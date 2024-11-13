package com.learning.restful_web_services.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request)
			throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(PostNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handlePostNotFoundException(Exception ex, WebRequest request)
			throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);

	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> li = new ArrayList<>();
		ex.getFieldErrors().forEach(e -> li.add(e.getDefaultMessage()));

		String msg1 = li.toString();

		String msg2 = "Total errors: " + ex.getFieldErrorCount() + ". First error is: " + ex.getFieldError().getDefaultMessage();

		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), msg2, request.getDescription(false));

		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}

}