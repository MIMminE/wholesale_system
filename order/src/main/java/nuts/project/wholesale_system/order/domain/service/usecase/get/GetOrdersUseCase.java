package nuts.project.wholesale_system.order.domain.service.usecase.get;

import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;

import java.util.List;

public interface GetOrdersUseCase {
    List<OrderProcessDto> execute(String userId);
}
