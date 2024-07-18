package nuts.project.wolesale_system.stock.domain.exception;


import lombok.Getter;

public class StockException extends RuntimeException {

    public StockException(StockExceptionCase stockExceptionCase) {
        super(stockExceptionCase.message);
    }

    public StockException(StockExceptionCase stockExceptionCase, Throwable cause) {
        super(stockExceptionCase.message, cause);
    }

    @Getter
    public enum StockExceptionCase {

        SERVER_FAIL("server connection fail."),
        NOT_FOUND_ELEMENT("There is no data available for that condition."),
        OUT_OF_STOCK("There is not enough stock left.")
        ;

        private final String message;

        StockExceptionCase(String message) {
            this.message = message;
        }
    }
}
