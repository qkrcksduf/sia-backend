package io.wisoft.siabackend.exception;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import javassist.NotFoundException;
import org.postgresql.util.PSQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@EnableWebMvc
public class ControllerExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BindingErrorResponse> methodArgumentNotValidationExceptionHandler(MethodArgumentNotValidException e) {
    BindingErrorResponse bindingErrorResponse = BindingErrorResponse.builder()
        .title("Binding Exception")
        .message("입력하신 정보가 유효하지 않습니다.")
        .build();
    bindingErrorResponse.setErrors(e.getBindingResult().getFieldErrors());
    return ResponseEntity.status(BAD_REQUEST).body(bindingErrorResponse);
  }

  @ExceptionHandler(PSQLException.class)
  public ResponseEntity<ErrorResponse> postgreSQLExceptionHandler(PSQLException e) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .title("Database Exception")
        .message(e.getMessage())
        .build();
    return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException e) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .title("NotFound Exception")
        .message(e.getMessage())
        .build();
    return ResponseEntity.status(NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .title("NoHandlerFound Exception")
        .message(e.getMessage())
        .build();
    return ResponseEntity.status(NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException e) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .title("IllegalArgument Exception")
        .message(e.getMessage())
        .build();
    return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(InvalidDefinitionException.class)
  public ResponseEntity<ErrorResponse> invalidDefinitionException(InvalidDefinitionException e) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .title("InvalidDefinitionException")
        .message(e.getMessage())
        .build();
    return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
  }

}
