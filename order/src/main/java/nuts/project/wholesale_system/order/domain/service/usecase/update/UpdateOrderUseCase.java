package nuts.project.wholesale_system.order.domain.service.usecase.update;

import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderDto;

import java.util.List;

public interface UpdateOrderUseCase {
    UpdateOrderDto execute(String orderId, List<OrderItem> items);
}
