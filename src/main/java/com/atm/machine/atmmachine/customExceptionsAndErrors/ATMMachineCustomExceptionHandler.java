package com.atm.machine.atmmachine.customExceptionsAndErrors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ATMMachineCustomExceptionHandler {

	@ExceptionHandler(BankAccountGenericException.class)
	protected ResponseEntity<Object> handleBankAccountGeneric(BankAccountGenericException ex) {
		ErrorObject errorObject = new ErrorObject();
		errorObject.setStatus(HttpStatus.BAD_REQUEST);
		errorObject.setMessage(ex.getMessage());
		return errorResponseEntity(errorObject);
	}

	private ResponseEntity<Object> errorResponseEntity(ErrorObject errorObject) {
		return new ResponseEntity<>(errorObject, errorObject.getStatus());
	}
}
