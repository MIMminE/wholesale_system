package nuts.project.wholesale_system.order.domain.service;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.port.log_publisher.LogPublisherPort;
import nuts.project.wholesale_system.order.domain.port.order_service.OrderServicePort;
import nuts.project.wholesale_system.order.domain.port.order_service.dto.CreateOrderRequest;
import nuts.project.wholesale_system.order.domain.port.order_service.dto.OrderItemRequest;
import nuts.project.wholesale_system.order.domain.port.order_service.dto.OrderResponse;
import nuts.project.wholesale_system.order.domain.port.payment_service.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.port.payment_service.dto.PaymentRequest;
import nuts.project.wholesale_system.order.domain.port.payment_service.dto.PaymentResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static nuts.project.wholesale_system.order.domain.model.OrderStatus.pendPayment;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService implements OrderServicePort {

    private final OrderRepository orderRepository;
    private final LogPublisherPort logPublisher;
    private final PaymentServicePort paymentService;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        Order order = Order.builder()
                .orderId(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .items(request.getOrderItems().stream().map(OrderItemRequest::toOrderItem).toList())
                .orderStatus(pendPayment)
                .build();

        OrderEntity entity = OrderEntity.fromOrder(order);
        orderRepository.save(entity);


        PaymentRequest paymentRequest = new PaymentRequest();
        PaymentResponse paymentResponse = paymentService.getPaymentInformation(paymentRequest);

        logPublisher.publishing(entity.toString());
        logPublisher.publishing(paymentResponse.toString());

        return OrderResponse.generateCreateOrderResponse(order.getUserId(), order.getOrderId(), entity.getCreatedAt(), paymentResponse);
    }

    @Override
    public OrderResponse getOrderByOrderId(String orderId) {
        return null;
    }

    @Override
    public OrderResponse getOrderByCustomerId(String customerId) {
        return null;
    }

    @Override
    public OrderResponse updateOrderStatus(String orderId, OrderStatus orderStatus) {


        logPublisher.publishing("test order update log test!");
        return null;
    }

    @Override
    public OrderResponse deleteOrder(String orderId) {

        logPublisher.publishing("test order delete log test!");
        return null;
    }
}