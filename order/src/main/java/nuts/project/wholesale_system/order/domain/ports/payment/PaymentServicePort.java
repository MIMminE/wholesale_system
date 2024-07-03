package nuts.project.wholesale_system.order.domain.ports.payment;

public interface PaymentServicePort {

    PaymentResponse getPaymentInformation(PaymentRequest request);

}
