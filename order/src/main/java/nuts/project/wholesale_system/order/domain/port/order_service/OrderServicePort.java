package nuts.project.wholesale_system.order.domain.port.order_service;

import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.port.order_service.dto.CreateOrderRequest;
import nuts.project.wholesale_system.order.domain.port.order_service.dto.OrderResponse;


public interface OrderServicePort {

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse getOrderByOrderId(String orderId);

    OrderResponse getOrderByCustomerId(String customerId);

    OrderResponse updateOrderStatus(String orderId, OrderStatus orderStatus);

    OrderResponse deleteOrder(String orderId);
}
