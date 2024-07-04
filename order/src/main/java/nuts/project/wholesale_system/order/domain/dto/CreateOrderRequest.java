package nuts.project.wholesale_system.order.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import nuts.project.wholesale_system.order.domain.model.OrderItem;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class CreateOrderRequest {

    @NotBlank
    private String userId;

    @NotEmpty
    private List<OrderItemRequest> orderItems;


    @Getter
    @AllArgsConstructor
    public static class OrderItemRequest {

        @NotBlank
        private String productId;

        @Size(min = 1)
        private int quantity;

        public static OrderItem toOrderItem(OrderItemRequest orderItemRequest) {

            return OrderItem.createOrderItem(orderItemRequest.productId, orderItemRequest.quantity);
        }
    }
}
