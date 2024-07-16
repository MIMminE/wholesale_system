package nuts.project.wholesale_system.order.domain.service.usecase.update;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderDto;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderStatusDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateOrderStatusUseCaseImpl implements UpdateOrderStatusUseCase{
    @Override
    public UpdateOrderStatusDto execute(String orderId, OrderStatus orderStatus) {
        return null;
    }
}
