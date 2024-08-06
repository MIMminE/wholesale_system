package nuts.project.wholesale_system.order.domain.service;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderStatusDto;
import nuts.project.wholesale_system.order.domain.service.usecase.create.CreateOrderUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.delete.DeleteOrderIdUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.get.GetOrderUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.get.GetOrdersUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.update.UpdateOrderStatusUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final CreateOrderUseCase createOrderUseCase;
    private final DeleteOrderIdUseCase deleteOrderIdUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final GetOrdersUseCase getOrdersUseCase;
    private final GetOrderUseCase getOrderUseCase;

    public OrderProcessDto createOrder(String userId, List<OrderItem> orderItems) {
        return createOrderUseCase.execute(userId, orderItems);
    }

    public Order deleteOrder(String orderId) {
        return deleteOrderIdUseCase.execute(orderId);
    }

    /**
     * @throws OrderException(OrderException.OrderExceptionCase)
     */
    public UpdateOrderStatusDto updateOrderStatus(String orderId, String orderStatus) {

        try {
            OrderStatus status = OrderStatus.valueOf(orderStatus);
            return updateOrderStatusUseCase.execute(orderId, status);

        } catch (IllegalArgumentException e) {
            throw new OrderException(OrderException.OrderExceptionCase.UPDATE_NO_SUCH_ELEMENT);
        }
    }

    public List<OrderProcessDto> getOrders(String userId) {
        return getOrdersUseCase.execute(userId);
    }

    public OrderProcessDto getOrderByOrderId(String orderId) {
        return getOrderUseCase.execute(orderId);
    }
}
