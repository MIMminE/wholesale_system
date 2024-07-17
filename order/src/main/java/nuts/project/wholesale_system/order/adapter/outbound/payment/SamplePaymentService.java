package nuts.project.wholesale_system.order.adapter.outbound.payment;

import nuts.project.wholesale_system.order.domain.exception.PaymentException;
import nuts.project.wholesale_system.order.domain.exception.PaymentException.PaymentExceptionCase;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentResponse;
import org.springframework.stereotype.Component;

import static nuts.project.wholesale_system.order.domain.exception.PaymentException.PaymentExceptionCase.*;

@Component
public class SamplePaymentService implements PaymentServicePort {

    @Override
    public PaymentResponse requestPayment(String userId, String orderId) throws PaymentException {
        try {
            return new PaymentResponse("no", "test", 10);
        } catch (RuntimeException e) {
            throw new PaymentException(PAYMENT_SERVICE_FAIL);
        }
    }

    @Override
    public PaymentResponse getPaymentInformation(String orderId) throws PaymentException {
        try {
            return new PaymentResponse("no", "test", 10);
        } catch (RuntimeException e) {
            throw new PaymentException(PAYMENT_SERVICE_FAIL);
        }
    }

    @Override
    public PaymentResponse deletePaymentInformation(String orderId) throws PaymentException {
         try {
            return new PaymentResponse("no", "test", 10);
        } catch (RuntimeException e) {
            throw new PaymentException(PAYMENT_DELETE_REQUEST_FAIL);
        }
    }
}