package nuts.project.wholesale_system.authentication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(AuthenticationExceptionType authenticationExceptionType) {
        super(authenticationExceptionType.name());
    }

    public AuthenticationException(AuthenticationExceptionType authenticationExceptionType, Throwable cause) {
        super(authenticationExceptionType.name(), cause);
    }


    public enum AuthenticationExceptionType {
        USER_NOT_FOUND,
    }
}
