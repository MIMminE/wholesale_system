package nuts.project.wholesale_system.order.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class UpdateOrderDto {

    private String orderId;

    private String userId;

    private List<OrderItem> beforeOrderItems;

    private List<OrderItem> afterOrderItems;
}
