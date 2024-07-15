package nuts.project.wholesale_system.order.domain.service.usecase.create;

import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;

import java.util.List;

public interface CreateOrderUseCase {
    OrderProcessDto execute(String userId, List<OrderItem> items);
}