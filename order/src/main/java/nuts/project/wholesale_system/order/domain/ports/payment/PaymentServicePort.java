package nuts.project.wholesale_system.order.domain.ports.payment;

public interface PaymentServicePort {

    PaymentResponse requestPayment(String userId, String orderId) throws PaymentException;

    PaymentResponse getPaymentInformation(String orderId) throws PaymentException;

    PaymentResponse deletePaymentInformation(String orderId) throws PaymentException;
}
