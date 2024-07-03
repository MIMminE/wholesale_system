package nuts.project.wholesale_system.order.domain.usecase.create;

import nuts.project.wholesale_system.order.domain.dto.OrderResponse;
import nuts.project.wholesale_system.order.domain.model.OrderItem;

import java.util.List;

public interface CreateOrderUseCase {
    OrderResponse execute(String userId, List<OrderItem> items);
}