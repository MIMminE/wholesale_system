package nuts.project.wholesale_system.order.domain.ports.payment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentCreateRequest {
    private String userId;
    private String orderId;
}
