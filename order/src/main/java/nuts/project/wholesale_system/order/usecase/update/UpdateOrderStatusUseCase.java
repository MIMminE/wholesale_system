package nuts.project.wholesale_system.order.usecase.update;

import nuts.project.wholesale_system.order.domain.dto.OrderResponse;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;

public interface UpdateOrderStatusUseCase {
    OrderResponse execute(String orderId, OrderStatus orderStatus);
}
