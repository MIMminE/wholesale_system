package nuts.project.wholesale_system.order.adapter.inbound.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust.*;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.response.*;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.service.OrderService;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest request, @RequestHeader("jwtUserId") String jwtUserId) {

        List<OrderItemRequest> orderItems = request.getOrderItems();

        OrderProcessDto createOrderDto = orderService.createOrder(jwtUserId, orderItems.stream()
                .map(OrderItemRequest::toOrderItem).toList());

        CreateOrderResponse createOrderResponse = CreateOrderResponse.fromOrder(createOrderDto);
        return ResponseEntity.ok().body(createOrderResponse);
    }

    @DeleteMapping("/orders")
    public ResponseEntity<DeleteOrderResponse> deleteOrder(@RequestBody @Valid DeleteOrderRequest request, @RequestHeader("jwtUserId") String jwtUserId) {

        Order order = orderService.deleteOrder(request.getOrderId());

        return ResponseEntity.ok().body(DeleteOrderResponse.fromOrder(order));
    }

    @PutMapping("/orders")
    public ResponseEntity<UpdateOrderResponse> updateOrder(@RequestBody @Valid UpdateOrderRequest request) {

        UpdateOrderDto updateOrderDto = orderService.updateOrder(request.getOrderId(),
                request.getOrderItems().stream().map(OrderItemRequest::toOrderItem).toList());

        return ResponseEntity.ok().body(UpdateOrderResponse.fromUpdateOrderDto(updateOrderDto));
    }

    @PutMapping("/orders/status")
    public ResponseEntity<UpdateOrderResponse> updateOrderStatus(@RequestBody @Valid UpdateOrderStatusRequest request) {

        String orderId = request.getOrderId();
        String orderStatus = request.getOrderStatus();

        UpdateOrderDto updateOrderDto = orderService.updateOrderStatus(orderId, orderStatus);

        return ResponseEntity.ok().body(UpdateOrderResponse.fromUpdateOrderDto(updateOrderDto));
    }

    @GetMapping("/orders/user/{userId}")
    public ResponseEntity<GetOrdersByUserIdResponse> getOrders(@PathVariable String userId) {

        List<OrderProcessDto> orderProcessDtoList = orderService.getOrders(userId);

        return ResponseEntity.ok().body(GetOrdersByUserIdResponse.fromOrderProcessDtoList(orderProcessDtoList));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<GetOrderByOrderIdResponse> getOrder(@PathVariable String orderId) {

        OrderProcessDto orderProcessDto = orderService.getOrderByOrderId(orderId);

        return ResponseEntity.ok().body(GetOrderByOrderIdResponse.fromOrderProcessDto(orderProcessDto));
    }
}
