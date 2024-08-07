package nuts.project.wholesale_system.order.adapter.outbound.payment;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.ports.payment.request.PaymentCreateRequest;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.ports.payment.request.PaymentDeleteRequest;
import nuts.project.wholesale_system.order.domain.ports.payment.response.PaymentCreateResponse;
import nuts.project.wholesale_system.order.domain.ports.payment.response.PaymentDeleteResponse;
import nuts.project.wholesale_system.order.domain.ports.payment.response.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PaymentService implements PaymentServicePort {

    private final RestTemplate restTemplate;

    @Value("${payment_server_url}")
    private String paymentServiceUrl;

    @Override
    public PaymentCreateResponse requestPayment(PaymentCreateRequest paymentRequest) throws OrderException {

        try {
            ResponseEntity<Map> paymentResponse = restTemplate.postForEntity(paymentServiceUrl + "payments", paymentRequest, Map.class);
            String accountNumber = (String) Objects.requireNonNull(paymentResponse.getBody()).get("accountNumber");
            int totalPrice = (int) paymentResponse.getBody().get("totalPrice");

            return new PaymentCreateResponse(paymentRequest.getUserId(), paymentRequest.getOrderId(), accountNumber, totalPrice);

        } catch (ResourceAccessException e) {
            throw new OrderException(OrderException.OrderExceptionCase.PAYMENT_SERVICE_FAIL);
        }
    }

    @Override
    public PaymentResponse getPayment(String orderId) {
        try {
            ResponseEntity<Map> paymentResponse = restTemplate.getForEntity(paymentServiceUrl + "payments/" + orderId, Map.class);

            boolean findPayment = (boolean) paymentResponse.getBody().get("findPayment");

            if (findPayment) {
                String userId = (String) Objects.requireNonNull(paymentResponse.getBody()).get("userId");
                String accountNumber = (String) paymentResponse.getBody().get("accountNumber");
                int totalPrice = (int) paymentResponse.getBody().get("totalPrice");

                return new PaymentResponse(userId, orderId, accountNumber, totalPrice);
            } else {
                throw new OrderException(OrderException.OrderExceptionCase.PAYMENT_NOT_FOUND);
            }
        } catch (ResourceAccessException e) {
            throw new OrderException(OrderException.OrderExceptionCase.PAYMENT_SERVICE_FAIL);
        }
    }

    @Override
    public PaymentDeleteResponse deletePaymentInformation(PaymentDeleteRequest paymentDeleteRequest) throws OrderException {

        try {
            Map result = restTemplate.exchange(paymentServiceUrl + "payments", HttpMethod.DELETE, new HttpEntity<>(paymentDeleteRequest), Map.class).getBody();

            String userId = (String) Objects.requireNonNull(result).get("userId");
            String orderId = (String) Objects.requireNonNull(result).get("orderId");

            return new PaymentDeleteResponse(userId, orderId, true);

        } catch (ResourceAccessException e) {
            throw new OrderException(OrderException.OrderExceptionCase.PAYMENT_SERVICE_FAIL);
        }
    }
}