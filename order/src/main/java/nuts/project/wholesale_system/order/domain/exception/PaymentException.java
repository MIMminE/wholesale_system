package nuts.project.wholesale_system.order.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PaymentException extends RuntimeException {

    public PaymentException(PaymentExceptionCase paymentExceptionCase) {
        super(paymentExceptionCase.getMessage());
    }

    @RequiredArgsConstructor
    @Getter
    public enum PaymentExceptionCase {

        PAYMENT_SERVICE_FAIL("Payment Service fail"),
        PAYMENT_DELETE_REQUEST_FAIL("Delete Request Fail!");

        private final String message;
    }

}
