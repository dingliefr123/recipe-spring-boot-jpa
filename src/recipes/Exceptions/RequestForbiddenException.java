package recipes.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class RequestForbiddenException extends RuntimeException {
  public RequestForbiddenException(String cause) {
    super(cause);
  }
}
