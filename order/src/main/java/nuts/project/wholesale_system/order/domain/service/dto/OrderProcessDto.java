package nuts.project.wholesale_system.order.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import nuts.project.wholesale_system.order.domain.model.Order;

@AllArgsConstructor
@Getter
@ToString
public class OrderProcessDto {
    private PaymentInformation payment;
    private Order order;
}
