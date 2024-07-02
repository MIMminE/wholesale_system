package nuts.project.wholesale_system.order.domain.port.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PaymentResponse {
    private String accountId;
    private String accountName;
    private int totalPrice;
}
