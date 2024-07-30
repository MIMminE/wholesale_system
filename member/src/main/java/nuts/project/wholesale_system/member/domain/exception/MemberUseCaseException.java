package nuts.project.wholesale_system.member.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class MemberUseCaseException extends RuntimeException {

    public MemberUseCaseException(MemberExceptionCase exceptionCase) {
        super(exceptionCase.getMessage());
    }

    public MemberUseCaseException(MemberExceptionCase exceptionCase, Throwable cause) {
        super(exceptionCase.getMessage(), cause);
    }

    @Getter
    @RequiredArgsConstructor
    public enum MemberExceptionCase {
        INVALID_CORPORATION_NUMBER("Invalid institution number."),
        ALREADY_IN_USE_ID("This ID is already in use"),
        GET_NO_SUCH_ELEMENT("No data is available for searching."),
        DELETE_NO_SUCH_ELEMENT("There is no target to delete.");

        private final String message;

    }
}
