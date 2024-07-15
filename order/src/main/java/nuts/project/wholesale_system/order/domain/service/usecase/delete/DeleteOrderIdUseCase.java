package nuts.project.wholesale_system.order.domain.service.usecase.delete;

import nuts.project.wholesale_system.order.domain.model.Order;

public interface DeleteOrderIdUseCase {
    Order execute(String orderId);
}
