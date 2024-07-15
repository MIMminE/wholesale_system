package nuts.project.wholesale_system.order.domain.service.usecase.delete;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.model.Order;
import org.springframework.stereotype.Component;

import static nuts.project.wholesale_system.order.domain.exception.OrderException.OrderExceptionCase.*;

@Component
@RequiredArgsConstructor
public class DeleteOrderByOrderIdUseCaseImpl implements DeleteOrderIdUseCase {
    private final OrderRepository orderRepository;

    @Override
    public Order execute(String orderId) {

        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new OrderException(DELETE_NO_SUCH_ELEMENT));
        orderRepository.delete(orderEntity);

        return orderEntity.toOrder();
    }
}
