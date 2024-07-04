package nuts.project.wholesale_system.order.adapter.inbound.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.dto.CreateOrderRequest;
import nuts.project.wholesale_system.order.domain.dto.OrderResponse;
import nuts.project.wholesale_system.order.domain.dto.DeleteOrderRequest;
import nuts.project.wholesale_system.order.domain.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest request) {

        OrderResponse orderResponse = orderService.createOrder(request);

        return ResponseEntity.ok().body(orderResponse);
    }

    @DeleteMapping("/orders")
    public ResponseEntity<OrderResponse> deleteOrder(@RequestBody @Valid DeleteOrderRequest request) {
        OrderResponse orderResponse = orderService.deleteOrder(request);

        return ResponseEntity.ok().body(orderResponse);
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<OrderResponse> getOrders(@PathVariable String userId) {
        OrderResponse orderResponse = orderService.getOrdersByUserId(userId);

        return ResponseEntity.ok().body(orderResponse);
    }

    @GetMapping("/orders/{userId}/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String userId, @PathVariable String orderId) {
        OrderResponse orderResponse = orderService.getOrderByOrderId(userId, orderId);

        return ResponseEntity.ok().body(orderResponse);
    }
}
