package nuts.project.wholesale_system.order.adapter.inbound.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.port.order_service.dto.CreateOrderRequest;
import nuts.project.wholesale_system.order.domain.port.order_service.dto.OrderItemRequest;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.port.order_service.OrderServicePort;
import nuts.project.wholesale_system.order.domain.port.order_service.dto.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static nuts.project.wholesale_system.order.domain.model.OrderStatus.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderServicePort orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest request) {

        Order order = Order.builder()
                .orderId(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .items(request.getOrderItems().stream().map(OrderItemRequest::toOrderItem).toList())
                .orderStatus(pendPayment)
                .build();


        OrderResponse orderResponse = orderService.createOrder(request);

        return ResponseEntity.ok().body(orderResponse);
    }
}
