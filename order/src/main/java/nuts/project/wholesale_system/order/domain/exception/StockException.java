package nuts.project.wholesale_system.order.domain.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class StockException extends RuntimeException {

    public StockException(StockExceptionCase stockExceptionCase) {
        super(stockExceptionCase.message);
    }

    public StockException(StockExceptionCase stockExceptionCase, Throwable cause) {
        super(stockExceptionCase.message, cause);
    }

    @RequiredArgsConstructor
    @Getter
    public enum StockExceptionCase {

        STOCK_SERVICE_FAIL("Stock Service fail"),
        OUT_OF_STOCK("out of stock!");

        private final String message;
    }
}
