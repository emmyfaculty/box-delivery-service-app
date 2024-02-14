package com.emmy.boxdeliveryserviceapi.exception;

public class LowBatteryException extends RuntimeException {

  public LowBatteryException(String message) {
    super(message);
  }
}
