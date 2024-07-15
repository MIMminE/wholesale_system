package nuts.project.wholesale_system.order.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nuts.project.wholesale_system.order.domain.model.Order;

@AllArgsConstructor
@Getter
public class UpdateOrderDto {

    private String orderId;

    private String userId;

    private PaymentInformation paymentInformation;

    private Order beforeOrder;

    private Order afterOrder;
}
