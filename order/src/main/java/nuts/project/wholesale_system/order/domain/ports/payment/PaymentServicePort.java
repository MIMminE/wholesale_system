package nuts.project.wholesale_system.order.domain.ports.payment;

public interface PaymentServicePort {

    PaymentResponse requestPayment(PaymentRequest request);

    PaymentResponse getPaymentInformation(String orderId);
}
