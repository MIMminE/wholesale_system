package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nuts.project.wholesale_system.order.domain.model.OrderItem;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class OrderItemResponse {

    @NotBlank
    private String productId;

    @Min(1)
    private int quantity;

    public static OrderItemResponse fromOrderItem(OrderItem orderItem) {
        return new OrderItemResponse(orderItem.getProductId(), orderItem.getQuantity());
    }
}
