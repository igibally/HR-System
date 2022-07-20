package com.workmotion.ems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class EmployeeExceptionController {

   @ExceptionHandler(value = javax.persistence.EntityNotFoundException.class)
    public ResponseEntity<Object> exception(javax.persistence.EntityNotFoundException exception) {
      return new ResponseEntity<>(
            new ErrorMessage("Employee not found",HttpStatus.NOT_FOUND.toString()), HttpStatus.NOT_FOUND
      );
    }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Object> exception(Exception exception) {
    return new ResponseEntity<>(
        new ErrorMessage("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.NOT_FOUND
    );
  }

}
