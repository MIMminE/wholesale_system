package nuts.project.wholesale_system.order.adapter.outbound.payment;

import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentResponse;
import org.springframework.stereotype.Component;


@Component
public class SamplePaymentService implements PaymentServicePort {

    @Override
    public PaymentResponse requestPayment(String userId, String orderId) {
        return new PaymentResponse("no", "test", 10);
    }

    @Override
    public PaymentResponse getPaymentInformation(String orderId) {
        return new PaymentResponse("no", "test", 10);
    }

    @Override
    public PaymentResponse deletePaymentInformation(String orderId) {
        return new PaymentResponse("no", "test", 10);
    }
}