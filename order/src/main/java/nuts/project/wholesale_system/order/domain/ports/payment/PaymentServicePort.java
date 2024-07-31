package nuts.project.wholesale_system.order.domain.ports.payment;

public interface PaymentServicePort {

    PaymentResponse requestPayment(String userId, String orderId);

    PaymentResponse getPaymentInformation(String orderId);

    PaymentResponse deletePaymentInformation(String orderId);
}
