package nuts.project.wholesale_system.order.adapter.inbound.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust.*;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.response.*;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.service.OrderService;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderDto;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderStatusDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {

    private final OrderService orderService;

    /**
     *
     * @param request About Requesting Order Creation
     * @param userId If the gateway server successfully verifies the JWT token, it sends the user ID of the token in the header.
     */
    @PostMapping("/api/v1/orders")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest request, @RequestHeader("User-Id") String userId) {

        List<OrderItemRequest> orderItems = request.getOrderItems();

        OrderProcessDto createOrderDto = orderService.createOrder(userId, orderItems.stream()
                .map(OrderItemRequest::toOrderItem).toList());

        CreateOrderResponse createOrderResponse = CreateOrderResponse.fromOrder(createOrderDto);
        return ResponseEntity.ok().body(createOrderResponse);
    }

    /**
     *
     * @param request About Order Deletion Requests
     */
    @DeleteMapping("/api/v1/orders")
    public ResponseEntity<DeleteOrderResponse> deleteOrder(@RequestBody @Valid DeleteOrderRequest request) {

        Order order = orderService.deleteOrder(request.getOrderId());

        return ResponseEntity.ok().body(DeleteOrderResponse.fromOrder(order));
    }

    @PutMapping("/api/v1/orders")
    public ResponseEntity<UpdateOrderResponse> updateOrder(@RequestBody @Valid UpdateOrderRequest request) {

        UpdateOrderDto updateOrderDto = orderService.updateOrder(request.getOrderId(),
                request.getOrderItems().stream().map(OrderItemRequest::toOrderItem).toList());

        return ResponseEntity.ok().body(UpdateOrderResponse.fromUpdateOrderDto(updateOrderDto));
    }

    @PutMapping("/api/v1/orders/status")
    public ResponseEntity<UpdateOrderStatusResponse> updateOrderStatus(@RequestBody @Valid UpdateOrderStatusRequest request) {

        String orderId = request.getOrderId();
        String orderStatus = request.getOrderStatus();

        UpdateOrderStatusDto updateOrderStatusDto = orderService.updateOrderStatus(orderId, orderStatus);

        return ResponseEntity.ok().body(UpdateOrderStatusResponse.fromUpdateOrderStatusDto(updateOrderStatusDto));
    }

    @GetMapping("/api/v1/orders/user/{userId}")
    public ResponseEntity<GetOrdersByUserIdResponse> getOrders(@PathVariable("userId") String userId) {

        List<OrderProcessDto> orderProcessDtoList = orderService.getOrders(userId);

        return ResponseEntity.ok().body(GetOrdersByUserIdResponse.fromOrderProcessDtoList(orderProcessDtoList));
    }

    @GetMapping("/api/v1/orders/{orderId}")
    public ResponseEntity<GetOrderByOrderIdResponse> getOrder(@PathVariable("orderId") String orderId, @RequestHeader("Jwt-Token-id") String jwtTokenId) {

        System.out.println(jwtTokenId);
        OrderProcessDto orderProcessDto = orderService.getOrderByOrderId(orderId);

        return ResponseEntity.ok().body(GetOrderByOrderIdResponse.fromOrderProcessDto(orderProcessDto));
    }
}
