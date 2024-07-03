package nuts.project.wholesale_system.order.adapter.outbound.payment;

import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentRequest;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class SamplePaymentService implements PaymentServicePort {
    @Override
    public PaymentResponse getPaymentInformation(PaymentRequest request) {
        return new PaymentResponse("no", "test",10);
    }
}
