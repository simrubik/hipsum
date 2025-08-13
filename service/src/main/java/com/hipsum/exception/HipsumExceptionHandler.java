package com.hipsum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class HipsumExceptionHandler {

  @ExceptionHandler(HipsumException.class)
  public ResponseEntity<HipsumErrorMessage> handleHistoryException(
      HipsumException ex, WebRequest request) {
    HipsumErrorMessage errorResponse = new HipsumErrorMessage(ex.getMessage(), ex.getErrorCode());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<HipsumErrorMessage> handleException(Exception ex) {
    HipsumErrorMessage errorResponse = new HipsumErrorMessage(ex.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR.name());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }
}
