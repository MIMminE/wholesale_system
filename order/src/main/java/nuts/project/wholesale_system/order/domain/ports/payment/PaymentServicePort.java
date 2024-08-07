package nuts.project.wholesale_system.order.domain.ports.payment;

import nuts.project.wholesale_system.order.domain.ports.payment.request.PaymentDeleteRequest;
import nuts.project.wholesale_system.order.domain.ports.payment.request.PaymentCreateRequest;
import nuts.project.wholesale_system.order.domain.ports.payment.response.PaymentCreateResponse;
import nuts.project.wholesale_system.order.domain.ports.payment.response.PaymentDeleteResponse;
import nuts.project.wholesale_system.order.domain.ports.payment.response.PaymentResponse;

public interface PaymentServicePort {

    PaymentCreateResponse requestPayment(PaymentCreateRequest paymentRequest);

    PaymentResponse getPayment(String orderId);

    PaymentDeleteResponse deletePaymentInformation(PaymentDeleteRequest paymentDeleteRequest);
}
