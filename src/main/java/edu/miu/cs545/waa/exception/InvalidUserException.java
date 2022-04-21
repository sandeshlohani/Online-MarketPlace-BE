package edu.miu.cs545.waa.exception;

public class InvalidUserException extends RuntimeException {

  public InvalidUserException(String message) {
    super(message);
  }
}
