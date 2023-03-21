package recipes.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class NotAuthenticatedException extends RuntimeException {
  public NotAuthenticatedException(String cause) {
    super(cause);
  }
}
