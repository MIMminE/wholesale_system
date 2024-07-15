package nuts.project.wholesale_system.order.adapter.inbound.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Map.*;
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
        mockController.perform(MockMvcRequestBuilders.post("/orders")
                        .header("jwtUserId", userId)
                        .contentType(MediaType.APPLICATION_JSON)
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

        mockController.perform(MockMvcRequestBuilders.delete("/orders")
                        .header("jwtUserId", userId) // TODO :: jwt
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(deleteOrderRequest))
                ).andExpectAll(MockMvcSupport.mapMatchers(Map.ofEntries(
                        entry("deleteOrderId", orderId) // TODO:: 도메인 객체에서의 객체 커스텀
                )))
                .andDo(print());

    }

    @Test
    void updateOrder() {
    }

    @Test
    void updateOrderStatus() {
        UpdateOrderStatusRequest updateOrderStatusRequest = getOrderedObject(UpdateOrderStatusRequest.class).get(0);
        System.out.println(updateOrderStatusRequest);
    }

    @Test
    void getOrders() {
    }

    @Test
    void getOrder() {
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
                OrderSheet.order(UpdateOrderRequest.class, 1),
                OrderSheet.order(orderCustom(UpdateOrderStatusRequest.class).set("orderStatus", Arbitraries.of(Arrays.stream(OrderStatus.values()).map(Enum::toString).toList())), 1),
                OrderSheet.order(orderCustom(Order.class).set("items", lists), 1)
        );
    }
}