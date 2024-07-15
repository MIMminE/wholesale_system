package nuts.project.wholesale_system.order.domain.service.usecase.update;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateOrderUseCaseImpl implements UpdateOrderUseCase{
    @Override
    public UpdateOrderDto execute(String orderId, List<OrderItem> items) {
        return null;
    }
}
