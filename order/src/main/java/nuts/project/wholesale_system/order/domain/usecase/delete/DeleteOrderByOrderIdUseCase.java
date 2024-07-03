package nuts.project.wholesale_system.order.domain.usecase.delete;

import nuts.project.wholesale_system.order.domain.dto.OrderResponse;

public interface DeleteOrderByOrderIdUseCase {
    OrderResponse execute(String orderId);
}
