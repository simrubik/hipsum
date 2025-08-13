package com.hipsum.exception;

import lombok.Getter;

@Getter
public class HipsumException extends RuntimeException {

  private final String errorCode;

  public HipsumException(String message, Throwable cause, String errorCode) {
    super(message, cause);
    this.errorCode = errorCode;
  }
}
