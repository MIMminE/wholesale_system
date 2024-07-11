package nuts.project.wholesale_system.member.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CorporationUseCaseException extends RuntimeException {
    public CorporationUseCaseException(CorporationExceptionCase exceptionCase, Throwable throwable) {
        super(exceptionCase.message);
        this.exceptionCase = exceptionCase;
        this.throwable = throwable;
    }

    private final CorporationExceptionCase exceptionCase;
    private final Throwable throwable;


    @RequiredArgsConstructor
    @Getter
    public enum CorporationExceptionCase {

        CREATE_REDUNDANCY_BUSINESS_NUMBER("This is a business number that is already registered."),
        DELETE_NO_SUCH_ELEMENT("There is no target to delete."),
        GET_NO_SUCH_ELEMENT("No data is available for searching."),
        SEARCH_NO_SUCH_ELEMENT("There is no data found for that condition."),
        UPDATE_NO_SUCH_ELEMENT("An invalid ID was entered."),

        UNKNOWN_EXCEPTION("It's an unknown exception.");

        private final String message;
    }
}
