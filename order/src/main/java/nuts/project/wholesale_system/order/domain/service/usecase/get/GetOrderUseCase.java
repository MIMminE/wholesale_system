package nuts.project.wholesale_system.order.domain.service.usecase.get;

import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;

public interface GetOrderUseCase {
    OrderProcessDto execute(String orderId);
}
