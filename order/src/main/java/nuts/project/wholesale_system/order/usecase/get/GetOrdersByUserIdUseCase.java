package nuts.project.wholesale_system.order.usecase.get;

import nuts.project.wholesale_system.order.domain.dto.OrderResponse;

public interface GetOrdersByUserIdUseCase {
    OrderResponse execute(String userId);
}
