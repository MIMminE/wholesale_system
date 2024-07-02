package nuts.project.wholesale_system.order.domain.port.payment_service;

import nuts.project.wholesale_system.order.domain.port.payment_service.dto.PaymentRequest;
import nuts.project.wholesale_system.order.domain.port.payment_service.dto.PaymentResponse;

public interface PaymentServicePort {

    PaymentResponse getPaymentInformation(PaymentRequest request);

}
