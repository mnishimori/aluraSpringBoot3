package med.voll.api.domain.exception;

public class ValidacaoException extends RuntimeException {

  public ValidacaoException(String errorMessage) {
    super(errorMessage);
  }
}
