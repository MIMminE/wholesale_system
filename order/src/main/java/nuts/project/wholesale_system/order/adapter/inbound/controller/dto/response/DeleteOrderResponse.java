package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nuts.project.wholesale_system.order.domain.model.Order;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DeleteOrderResponse {

    private String deleteOrderId;

    private List<OrderItemResponse> deleteOrderItems;

    public static DeleteOrderResponse fromOrder(Order order) {
        return new DeleteOrderResponse(order.getOrderId(),
                order.getItems().stream().map(OrderItemResponse::fromOrderItem).toList());
    }
}
