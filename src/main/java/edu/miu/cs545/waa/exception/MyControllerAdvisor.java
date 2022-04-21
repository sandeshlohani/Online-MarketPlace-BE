package edu.miu.cs545.waa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class MyControllerAdvisor {
  @ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<Object> handleNodataFoundException(
      DataNotFoundException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.NOT_FOUND.value());
    body.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidUserException.class)
  public ResponseEntity<Object> handleInvalidUserException(
      InvalidUserException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.UNAUTHORIZED.value());
    body.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(UnprocessableException.class)
  public ResponseEntity<Object> handleUnprocessableException(
          UnprocessableException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
    body.put("error", HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
