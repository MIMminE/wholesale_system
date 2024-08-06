package nuts.project.wholesale_system.order.adapter.outbound.payment;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentRequest;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Component
@RequiredArgsConstructor
public class PaymentService implements PaymentServicePort {

    private final RestTemplate restTemplate;
    private final String paymentServiceUrl = "http://payment-service";

    @Override
    public PaymentResponse requestPayment(String userId, String orderId) {

        PaymentRequest paymentRequest = new PaymentRequest();
        try {

            ResponseEntity<Map> paymentResponse = restTemplate.postForEntity(paymentServiceUrl, paymentRequest, Map.class);
        } catch (ResourceAccessException e) {
            throw new OrderException(OrderException.OrderExceptionCase.PAYMENT_SERVICE_FAIL);
        }

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