package nuts.project.wholesale_system.order.adapter.inbound.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.lib.manager.restdocs_manager.MockMvcSupport;
import nuts.lib.manager.restdocs_manager.RestDocsManager;
import nuts.lib.manager.restdocs_manager.support.ExtendsFixtureRestDocsSupport;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust.*;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.response.CreateOrderResponse;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.response.ResponseRestDocs;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.service.OrderService;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.domain.service.dto.PaymentInformation;
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

    private final RestDocsManager restDocsManager = new RestDocsManager(RequestRestDocs.class, ResponseRestDocs.class);

    @Test
    @DisplayName("상품 주문 요청 정보와 User-Id 헤더 값을 POST /order-service/api/v1/orders 로 전송하고 결과를 반환한다.")
    void createOrder() throws Exception {
        // given
        CreateOrderRequest createOrderRequest = getOrderedObject(CreateOrderRequest.class).get(0);
        String userId = UUID.randomUUID().toString();
        String orderId = UUID.randomUUID().toString();
        String accountNumber = UUID.randomUUID().toString();
        List<OrderItem> orderItems = createOrderRequest.getOrderItems().stream().map(OrderItemRequest::toOrderItem).toList();
        PaymentInformation testPaymentInfo = new PaymentInformation(userId, orderId, accountNumber);

        OrderProcessDto orderProcessDto = new OrderProcessDto(testPaymentInfo, new Order(orderId, userId, orderItems, OrderStatus.pendPayment));
        System.out.println(orderProcessDto);
        CreateOrderResponse createOrderResponse = CreateOrderResponse.fromOrder(orderProcessDto);

        BDDMockito.given(orderService.createOrder(userId, orderItems))
                .willReturn(orderProcessDto);

        // when // then
        mockController.perform(MockMvcRequestBuilders.post("/order-service/api/v1/orders")
                        .header("User-Id", userId)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createOrderRequest)))
                .andExpectAll(MockMvcSupport.mapMatchers(ofEntries(
                        entry("orderId", orderId),
                        entry("userId", userId),
                        entry("payment.userId", testPaymentInfo.getUserId()),
                        entry("orderItems.length()", createOrderResponse.getOrderItems().size()),
                        entry("orderItems[0].productId", createOrderResponse.getOrderItems().get(0).getProductId()),
                        entry("orderItems[0].quantity", createOrderResponse.getOrderItems().get(0).getQuantity()))
                ))
                .andDo(restDocsManager.document("create-order", "createOrderRequest", "createOrderResponse"))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 삭제 요청 정보를 DELETE /order-service/api/v1/orders 로 전송하고 결과를 반환한다.")
    void deleteOrder() throws Exception {
        DeleteOrderRequest deleteOrderRequest = getOrderedObject(DeleteOrderRequest.class).get(0);
        Order order = getOrderedObject(Order.class).get(0);
        String userId = UUID.randomUUID().toString();
        String orderId = deleteOrderRequest.getOrderId();
        changeFieldValue(order, "orderId", orderId);

        BDDMockito.given(orderService.deleteOrder(orderId)).willReturn(order);

        mockController.perform(MockMvcRequestBuilders.delete("/order-service/api/v1/orders")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(deleteOrderRequest))
                ).andExpectAll(MockMvcSupport.mapMatchers(Map.ofEntries(
                        entry("deleteOrderId", orderId)
                )))
                .andDo(restDocsManager.document("delete-order", "deleteOrderRequest", "deleteOrderResponse"))
                .andDo(print());
    }

    @Test
    @DisplayName("주문 상태 변경 요청 정보를 PUT /order-service/api/v1/orders /status 로 전송하고 결과를 반환한다.")
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

        mockController.perform(MockMvcRequestBuilders.put("/order-service/api/v1/orders/status")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateOrderStatusRequest)))
                .andExpectAll(MockMvcSupport.mapMatchers(Map.ofEntries(
                        entry("orderId", orderId),
                        entry("beforeStatus", targetOrder.getOrderStatus().toString()),
                        entry("afterStatus", orderStatus)
                )))
                .andDo(restDocsManager.document("update-order", "updateOrderStatusRequest", "updateOrderStatusResponse"))
                .andDo(print());
    }


    @Test
    @DisplayName("주문 번호를 GET /order-service/api/v1/orders/%s 로 전송하고 결과를 반환한다.")
    void getOrder() throws Exception {

        Order order = getOrderedObject(Order.class).get(0);

        String orderId = order.getOrderId();
        String userId = order.getUserId();
        String accountNumber = UUID.randomUUID().toString();

        PaymentInformation testPaymentInfo = new PaymentInformation(userId, orderId, accountNumber);

        BDDMockito.given(orderService.getOrderByOrderId(orderId)).willReturn(new OrderProcessDto(testPaymentInfo, order));

        mockController.perform(MockMvcRequestBuilders.get("/order-service/api/v1/orders/{orderId}", orderId).header("jwtUserId", userId))
                .andExpectAll(MockMvcSupport.mapMatchers(Map.ofEntries(
                        entry("orderId", orderId),
                        entry("userId", userId),
                        entry("orderItems[0].productId", order.getItems().get(0).getProductId()),
                        entry("orderItems[0].quantity", order.getItems().get(0).getQuantity()),
                        entry("orderItems[1].productId", order.getItems().get(1).getProductId()),
                        entry("orderItems[1].quantity", order.getItems().get(1).getQuantity())
                )))
                .andDo(restDocsManager.document("getOrder", "getOrderResponse"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 번호를 GET /order-service/api/v1/orders/user/%s 로 전송하고 결과를 반환한다.")
    void getOrders() throws Exception {
        Order firetOrder = getOrderedObject(Order.class).get(0);
        Order secondOrder = getOrderedObject(Order.class).get(1);

        String userId = UUID.randomUUID().toString();
        String accountNumber = UUID.randomUUID().toString();

        changeFieldValue(firetOrder, "userId", userId);
        changeFieldValue(secondOrder, "userId", userId);

        OrderProcessDto firstOrderDto = new OrderProcessDto(new PaymentInformation(userId, firetOrder.getOrderId(), accountNumber), firetOrder);
        OrderProcessDto secondOrderDto = new OrderProcessDto(new PaymentInformation(userId, secondOrder.getOrderId(), accountNumber), secondOrder);


        BDDMockito.given(orderService.getOrders(userId)).willReturn(List.of(firstOrderDto, secondOrderDto));

        mockController.perform(MockMvcRequestBuilders.get("/order-service/api/v1/orders/user/%s".formatted(userId)))
                .andExpectAll(MockMvcSupport.mapMatchers(Map.ofEntries(
                                entry("count", 2),
                                entry("orderResponses[0].orderId", firetOrder.getOrderId()),
                                entry("orderResponses[1].orderId", secondOrder.getOrderId())
                        )
                ))
                .andDo(restDocsManager.document("getOrders", "getOrdersResponse"))
                .andDo(print());
    }

    @Override
    protected Object initController() {
        return new OrderController(orderService);
    }

    @Override
    protected List<OrderSheet> ordersObject() {
        return List.of(
                OrderSheet.order(orderCustom(CreateOrderRequest.class).minSize("orderItems", 1), 1),
                OrderSheet.order(DeleteOrderRequest.class, 1),
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