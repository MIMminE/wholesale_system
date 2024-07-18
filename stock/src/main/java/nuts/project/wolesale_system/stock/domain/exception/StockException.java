package nuts.project.wolesale_system.stock.domain.exception;


public class StockException extends RuntimeException {

    public StockException(StockExceptionCase stockExceptionCase) {
        super(stockExceptionCase.message);
    }

    public StockException(StockExceptionCase stockExceptionCase, Throwable cause) {
        super(stockExceptionCase.message, cause);
    }

    public enum StockExceptionCase {

        SERVER_FAIL("server connection fail");

        private final String message;

        StockExceptionCase(String message) {
            this.message = message;
        }
    }
}
