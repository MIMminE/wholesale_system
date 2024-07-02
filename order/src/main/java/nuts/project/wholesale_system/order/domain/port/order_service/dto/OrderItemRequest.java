package nuts.project.wholesale_system.order.domain.port.order_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nuts.project.wholesale_system.order.domain.model.OrderItem;

@Getter
@AllArgsConstructor
public class OrderItemRequest {

    @NotBlank
    private String productId;

    @Size(min = 1)
    private int quantity;

    public static OrderItem toOrderItem(OrderItemRequest orderItemRequest) {

        return OrderItem.createOrderItem(orderItemRequest.productId, orderItemRequest.quantity);
    }
}
