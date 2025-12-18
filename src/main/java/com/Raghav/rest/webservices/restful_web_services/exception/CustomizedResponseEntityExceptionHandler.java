package com.Raghav.rest.webservices.restful_web_services.exception;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.Raghav.rest.webservices.restful_web_services.user.UserNotFoundException;




@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public final @Nullable ResponseEntity<ErrorDetails>handleAllException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
				ex.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final @Nullable ResponseEntity<ErrorDetails>handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
				ex.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PostNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handlePostNotFoundException(
	        PostNotFoundException ex, WebRequest request) {

	    ErrorDetails errorDetails = new ErrorDetails(
	            LocalDateTime.now(),
	            ex.getMessage(),
	            request.getDescription(false));

	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	
//	@Override
//	protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
//			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//
//		ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
//		"total errors are :"+ex.getErrorCount() +" and 1st error:"+ ex.getFieldError().getDefaultMessage(),request.getDescription(false));
//		
//		
//		return new ResponseEntity(errorDetails,HttpStatus.BAD_REQUEST);
//	
//		
//	}
	
	

		@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
	        MethodArgumentNotValidException ex, HttpHeaders headers,
	        HttpStatusCode status, WebRequest request) {

	    List<String> errors = ex.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .map(error -> error.getField() + " : " + error.getDefaultMessage())
	            .toList();

	    Map<String, Object> body = new HashMap<>();
	    body.put("timestamp", LocalDateTime.now());
	    body.put("totalErrors", errors.size());
	    body.put("errors", errors);
	    body.put("details", request.getDescription(false));

	    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}


}











