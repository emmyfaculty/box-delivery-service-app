package com.emmy.boxdeliveryserviceapi.exception;

public class BoxAlreadyExistsException extends RuntimeException {

  public BoxAlreadyExistsException(String message) {
    super(message);
  }
}
