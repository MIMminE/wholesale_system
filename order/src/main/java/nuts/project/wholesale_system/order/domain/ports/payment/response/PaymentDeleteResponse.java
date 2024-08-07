package nuts.project.wholesale_system.order.domain.ports.payment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentDeleteResponse {

    private String userId;
    private String orderId;
    private boolean deleted;
}
