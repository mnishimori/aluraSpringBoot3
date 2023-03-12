package med.voll.api.infrastructure.exception;

import jakarta.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import med.voll.api.domain.exception.ValidacaoException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity handler404Error() {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity handler400Error(MethodArgumentNotValidException exception) {
    var errors = exception.getFieldErrors();
    return ResponseEntity.badRequest().body(errors.stream().map(DadosErroValidacao::new).toList());
  }

  @ExceptionHandler(ValidacaoException.class)
  public ResponseEntity handerBusinessException(ValidacaoException exception) {
    var error = exception.getMessage();
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public ResponseEntity handlerSQLIntegrityConstraintViolationException(
      SQLIntegrityConstraintViolationException exception) {
    return ResponseEntity.badRequest().body(exception.getMessage());
  }

  private record DadosErroValidacao(String campo, String message) {

    public DadosErroValidacao(FieldError error) {
      this(error.getField(), error.getDefaultMessage());
    }
  }
}
