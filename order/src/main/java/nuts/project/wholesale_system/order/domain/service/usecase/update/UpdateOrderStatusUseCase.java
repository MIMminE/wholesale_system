package nuts.project.wholesale_system.order.domain.service.usecase.update;

import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderStatusDto;

public interface UpdateOrderStatusUseCase {
    UpdateOrderStatusDto execute(String orderId, OrderStatus orderStatus);
}
