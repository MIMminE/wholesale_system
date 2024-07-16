package nuts.project.wholesale_system.order.adapter.inbound.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.lib.manager.restdocs_manager.MockMvcSupport;
import nuts.lib.manager.restdocs_manager.support.ExtendsFixtureRestDocsSupport;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust.*;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.response.CreateOrderResponse;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.service.OrderService;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.domain.service.dto.PaymentInformation;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderDto;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderStatusDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Map.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class OrderControllerTest extends ExtendsFixtureRestDocsSupport {

    @Mock
    private OrderService orderService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("createOrder Controller 성공 테스트")
    void createOrder() throws Exception {
        // given
        CreateOrderRequest createOrderRequest = getOrderedObject(CreateOrderRequest.class).get(0);
        String userId = UUID.randomUUID().toString();
        String orderId = UUID.randomUUID().toString();
        List<OrderItem> orderItems = createOrderRequest.getOrderItems().stream().map(OrderItemRequest::toOrderItem).toList();
        PaymentInformation testPaymentInfo = new PaymentInformation("test_payment_info");
        OrderProcessDto orderProcessDto = new OrderProcessDto(testPaymentInfo, new Order(orderId, userId, orderItems, OrderStatus.pendPayment));
        CreateOrderResponse createOrderResponse = CreateOrderResponse.fromOrder(orderProcessDto);

        BDDMockito.given(orderService.createOrder(userId, orderItems))
                .willReturn(orderProcessDto);

        // when // then
        mockController.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                        .header("jwtUserId", userId)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createOrderRequest)))
                .andExpectAll(MockMvcSupport.mapMatchers(ofEntries(
                        entry("orderId", orderId),
                        entry("userId", userId),
                        entry("paymentInformation.payInfo", testPaymentInfo.getPayInfo()),
                        entry("orderItems.length()", createOrderResponse.getOrderItems().size()),
                        entry("orderItems[0].productId", createOrderResponse.getOrderItems().get(0).getProductId()),
                        entry("orderItems[0].quantity", createOrderResponse.getOrderItems().get(0).getQuantity()))
                ))
                .andDo(print());
    }

    @Test
    @DisplayName("deleteOrder Controller 성공 테스트")
    void deleteOrder() throws Exception {
        DeleteOrderRequest deleteOrderRequest = getOrderedObject(DeleteOrderRequest.class).get(0);
        Order order = getOrderedObject(Order.class).get(0);
        String userId = UUID.randomUUID().toString();
        String orderId = deleteOrderRequest.getOrderId();
        changeFieldValue(order, "orderId", orderId);

        BDDMockito.given(orderService.deleteOrder(orderId)).willReturn(order);

        mockController.perform(MockMvcRequestBuilders.delete("/api/v1/orders")
                        .header("jwtUserId", userId) // TODO :: jwt
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(deleteOrderRequest))
                ).andExpectAll(MockMvcSupport.mapMatchers(Map.ofEntries(
                        entry("deleteOrderId", orderId) // TODO:: 도메인 객체에서의 객체 커스텀
                )))
                .andDo(print());
    }

    @Test
    @DisplayName("updateOrder Controller 성공 테스트")
    void updateOrder() throws Exception {
        UpdateOrderRequest updateOrderRequest = getOrderedObject(UpdateOrderRequest.class).get(0);
        String orderId = updateOrderRequest.getOrderId();
        Order beforeOrder = getOrderedObject(Order.class).get(0);
        String userId = beforeOrder.getUserId();
        PaymentInformation testPaymentInfo = new PaymentInformation("test_payment_info");
        List<OrderItem> orderItems = updateOrderRequest.getOrderItems().stream().map(OrderItemRequest::toOrderItem).toList();

        changeFieldValue(beforeOrder, "orderId", orderId);
        Order afterOrder = new Order(orderId, userId, orderItems, OrderStatus.pendPayment);

        UpdateOrderDto returnedOrderDto = new UpdateOrderDto(orderId, userId, testPaymentInfo, beforeOrder, afterOrder);

        BDDMockito.given(orderService.updateOrder(orderId, orderItems))
                .willReturn(returnedOrderDto);

        mockController.perform(MockMvcRequestBuilders.put("/api/v1/orders")
                        .header("jwtUserId", userId) // TODO :: jwt
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateOrderRequest)))
                .andDo(print())
                .andExpectAll(MockMvcSupport.mapMatchers(Map.ofEntries(
                        entry("orderId", orderId),
                        entry("userId", userId),
                        entry("beforeOrderItems[0].productId", beforeOrder.getItems().get(0).getProductId()),
                        entry("beforeOrderItems[0].quantity", beforeOrder.getItems().get(0).getQuantity()),
                        entry("afterOrderItems[0].productId", afterOrder.getItems().get(0).getProductId()),
                        entry("afterOrderItems[0].quantity", afterOrder.getItems().get(0).getQuantity())
                )));
    }

    @Test
    @DisplayName("updateOrderStatus Controller 성공 테스트")
    void updateOrderStatus() throws Exception {
        UpdateOrderStatusRequest updateOrderStatusRequest = getOrderedObject(UpdateOrderStatusRequest.class).get(0);
        Order targetOrder = getOrderedObject(Order.class).get(0);

        changeFieldValue(targetOrder, "orderId", updateOrderStatusRequest.getOrderId());

        String orderId = updateOrderStatusRequest.getOrderId();
        String orderStatus = updateOrderStatusRequest.getOrderStatus();
        String userId = targetOrder.getUserId();

        UpdateOrderStatusDto updateOrderStatusDto = new UpdateOrderStatusDto(orderId, targetOrder.getOrderStatus().toString(), updateOrderStatusRequest.getOrderStatus());

        BDDMockito.given(orderService.updateOrderStatus(orderId, orderStatus))
                .willReturn(updateOrderStatusDto);

        mockController.perform(MockMvcRequestBuilders.put("/api/v1/orders/status")
                        .header("jwtUserId", userId) // TODO :: jwt
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateOrderStatusRequest)))
                .andExpectAll(MockMvcSupport.mapMatchers(Map.ofEntries(
                        entry("orderId", orderId),
                        entry("beforeStatus", targetOrder.getOrderStatus().toString()),
                        entry("afterStatus", orderStatus)
                )))
                .andDo(print());
    }

    @Test
    @DisplayName("getOrders Controller 성공 테스트")
    void getOrders() throws Exception {
        Order firetOrder = getOrderedObject(Order.class).get(0);
        Order secondOrder = getOrderedObject(Order.class).get(1);

        String userId = UUID.randomUUID().toString();

        changeFieldValue(firetOrder, "userId", userId);
        changeFieldValue(secondOrder, "userId", userId);

        OrderProcessDto firstOrderDto = new OrderProcessDto(new PaymentInformation("test_payment"), firetOrder);
        OrderProcessDto secondOrderDto = new OrderProcessDto(new PaymentInformation("test_payment"), secondOrder);


        BDDMockito.given(orderService.getOrders(userId)).willReturn(List.of(firstOrderDto, secondOrderDto));

        mockController.perform(MockMvcRequestBuilders.get("/api/v1/orders/user/%s".formatted(userId))
                        .header("jwtUserId", userId))
                .andExpectAll(MockMvcSupport.mapMatchers(Map.ofEntries(
                                entry("count", 2),
                                entry("orderResponses[0].orderId", firetOrder.getOrderId()),
                                entry("orderResponses[1].orderId", secondOrder.getOrderId())
                        )
                ))
                .andDo(print());
    }

    @Test
    @DisplayName("getOrder Controller 성공 테스트")
    void getOrder() throws Exception {

        Order order = getOrderedObject(Order.class).get(0);

        String orderId = order.getOrderId();
        String userId = order.getUserId();
        PaymentInformation testPaymentInfo = new PaymentInformation("test_payment_info");

        BDDMockito.given(orderService.getOrderByOrderId(orderId)).willReturn(new OrderProcessDto(testPaymentInfo, order));

        mockController.perform(MockMvcRequestBuilders.get("/api/v1/orders/{orderId}", orderId).header("jwtUserId", userId))
                .andExpectAll(MockMvcSupport.mapMatchers(Map.ofEntries(
                        entry("orderId", orderId),
                        entry("userId", userId),
                        entry("orderItems[0].productId", order.getItems().get(0).getProductId()),
                        entry("orderItems[0].quantity", order.getItems().get(0).getQuantity()),
                        entry("orderItems[1].productId", order.getItems().get(1).getProductId()),
                        entry("orderItems[1].quantity", order.getItems().get(1).getQuantity())
                )))
                .andDo(print());
    }


    @Override
    protected Object initController() {
        return new OrderController(orderService);
    }

    @Override
    protected List<OrderSheet> ordersObject() {
        return List.of(
                OrderSheet.order(CreateOrderRequest.class, 1),
                OrderSheet.order(DeleteOrderRequest.class, 1),
                OrderSheet.order(orderCustom(UpdateOrderRequest.class)
                        .minSize("orderItems", 2).set("orderId", UUID.randomUUID().toString()), 1),
                OrderSheet.order(orderCustom(UpdateOrderStatusRequest.class)
                        .set("orderId", UUID.randomUUID().toString())
                        .set("orderStatus", Arbitraries.of(Arrays.stream(OrderStatus.values()).map(Enum::toString).toList())), 1),
                OrderSheet.order(orderCustom(Order.class)
                        .set("orderId", UUID.randomUUID().toString())
                        .set("userId", UUID.randomUUID().toString())
                        .size("items", 2, 5)
                        .set("items[*].quantity", Arbitraries.integers().between(10, 30)), 2)
        );
    }
}