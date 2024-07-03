package nuts.project.wholesale_system.order.domain.usecase.get;

import nuts.project.wholesale_system.order.domain.dto.OrderResponse;

public interface GetOrdersByUserIdUseCase {
    OrderResponse execute(String userId);
}
