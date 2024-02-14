package com.emmy.boxdeliveryserviceapi.exception;

public class BoxNotFoundException extends RuntimeException {

  public BoxNotFoundException(String message) {
    super(message);
  }
}
