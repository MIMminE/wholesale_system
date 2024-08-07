package nuts.project.wholesale_system.order.domain.ports.payment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCreateResponse {
    private String userId;
    private String orderId;
    private String accountNumber;
    private int totalPrice;
}
