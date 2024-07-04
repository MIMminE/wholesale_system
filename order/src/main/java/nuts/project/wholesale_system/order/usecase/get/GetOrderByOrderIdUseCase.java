package nuts.project.wholesale_system.order.usecase.get;

import nuts.project.wholesale_system.order.domain.dto.OrderResponse;

public interface GetOrderByOrderIdUseCase {
    OrderResponse execute(String userId, String orderId);
}
