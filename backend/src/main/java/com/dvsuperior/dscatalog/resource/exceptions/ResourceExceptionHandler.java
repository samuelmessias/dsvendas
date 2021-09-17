package com.dvsuperior.dscatalog.resource.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dvsuperior.dscatalog.services.exceptions.DataBaseException;
import com.dvsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandarError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandarError err = new StandarError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandarError> dataBase(DataBaseException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		StandarError err = new StandarError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("DataBase exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}

}
