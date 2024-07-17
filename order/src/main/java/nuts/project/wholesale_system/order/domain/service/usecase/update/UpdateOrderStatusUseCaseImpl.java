package nuts.project.wholesale_system.order.domain.service.usecase.update;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderDto;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderStatusDto;
import org.springframework.stereotype.Component;

import static nuts.project.wholesale_system.order.domain.exception.OrderException.OrderExceptionCase.UPDATE_NO_SUCH_ELEMENT;

@Component
@RequiredArgsConstructor
public class UpdateOrderStatusUseCaseImpl implements UpdateOrderStatusUseCase {

    private final OrderRepository orderRepository;

    @Override
    public UpdateOrderStatusDto execute(String orderId, OrderStatus orderStatus) {

        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new OrderException(UPDATE_NO_SUCH_ELEMENT));
        OrderStatus beforeOrderStatus = orderEntity.getOrderStatus();
        orderEntity.setOrderStatus(orderStatus);

        return new UpdateOrderStatusDto(orderId, beforeOrderStatus.toString(), orderStatus.toString());
    }
}
