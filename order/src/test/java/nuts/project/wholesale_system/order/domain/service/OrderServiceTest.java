package nuts.project.wholesale_system.order.domain.service;

import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.domain.service.dto.PaymentInformation;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderDto;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderStatusDto;
import nuts.project.wholesale_system.order.domain.service.usecase.create.CreateOrderUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.delete.DeleteOrderIdUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.get.GetOrderUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.get.GetOrdersUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.update.UpdateOrderStatusUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.update.UpdateOrderUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest extends FixtureGenerateSupport {

    @Mock
    CreateOrderUseCase createOrderUseCase;

    @Mock
    DeleteOrderIdUseCase deleteOrderIdUseCase;

    @Mock
    GetOrderUseCase getOrderUseCase;

    @Mock
    GetOrdersUseCase getOrdersUseCase;

    @Mock
    UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @Mock
    UpdateOrderUseCase updateOrderUseCase;

    @InjectMocks
    OrderService orderService;

    @Test
    @DisplayName("컨트롤러에게 전달 받은 인증 정보, 상품 정보를 CreateOrderUseCase 에게 전달하고 결과를 반환한다.")
    void createOrder() {
        // given
        Order order = getOrderedObject(Order.class).get(0);

        String orderId = order.getOrderId();
        String userId = order.getUserId();
        List<OrderItem> items = order.getItems();

        BDDMockito.given(createOrderUseCase.execute(userId, items))
                .willReturn(new OrderProcessDto(new PaymentInformation("test_payment"), order));

        // when
        OrderProcessDto orderProcessDto = orderService.createOrder(userId, items);

        // then
        Assertions.assertThat(orderProcessDto)
                .extracting("order.orderId", "order.userId", "order.items", "order.orderStatus")
                .contains(order.getOrderId(), order.getUserId(), order.getItems(), order.getOrderStatus());
    }

    @Test
    @DisplayName("컨트롤러에게 전달 받은 인증 정보, 주문 번호를 DeleteOrderUseCase 에게 전달하고 결과를 반환한다.")
    void deleteOrder() {

        // given
        Order order = getOrderedObject(Order.class).get(0);

        String orderId = order.getOrderId();
        String userId = order.getUserId();
        List<OrderItem> items = order.getItems();
        OrderStatus orderStatus = order.getOrderStatus();

        BDDMockito.given(deleteOrderIdUseCase.execute(orderId))
                .willReturn(order);

        // when
        Order resultOrder = orderService.deleteOrder(orderId);

        // then
        Assertions.assertThat(resultOrder)
                .extracting("orderId", "userId", "items", "orderStatus")
                .contains(orderId, userId, items, orderStatus);
    }

    @Test
    @DisplayName("컨트롤러에게 전달 받은 인증 정보, 상품 수정 정보를 UpdateOrderUseCase 에게 전달하고 결과를 반환한다.")
    void updateOrder() {
        // given
        Order beforeOrder = getOrderedObject(Order.class).get(0);
        String orderId = beforeOrder.getOrderId();
        String userId = beforeOrder.getUserId();

        Order afterOrder = getOrderedObject(Order.class).get(1);

        changeFieldValue(afterOrder, "orderId", orderId);

        UpdateOrderDto updateOrderDto = new UpdateOrderDto(orderId, userId, beforeOrder.getItems(), afterOrder.getItems());

        BDDMockito.given(updateOrderUseCase.execute(orderId, afterOrder.getItems()))
                .willReturn(updateOrderDto);

        // when
        UpdateOrderDto resultUpdateOrderDto = orderService.updateOrder(orderId, afterOrder.getItems());

        // then
        Assertions.assertThat(resultUpdateOrderDto)
                .extracting("orderId", "userId", "beforeOrderItems", "afterOrderItems")
                .contains(orderId, userId, beforeOrder.getItems(), afterOrder.getItems());
    }

    @Test
    @DisplayName("컨트롤러에게 전달 받은 인증 정보, 주문 상태 변경 정보를 UpdateOrderStatusUseCase에게 전달하고 결과를 반환한다.")
    void updateOrderStatus() {

        Order beforeOrder = getOrderedObject(Order.class).get(0);
        String orderId = beforeOrder.getOrderId();

        Order afterOrder = getOrderedObject(Order.class).get(1);

        OrderStatus orderStatus = OrderStatus.values()[new Random().nextInt(OrderStatus.values().length)];
        changeFieldValue(afterOrder, "orderId", orderId);
        changeFieldValue(afterOrder, "orderStatus", orderStatus);

        BDDMockito.given(updateOrderStatusUseCase.execute(orderId, orderStatus))
                .willReturn(new UpdateOrderStatusDto(orderId, beforeOrder.getOrderStatus().toString(), afterOrder.getOrderStatus().toString()));

        UpdateOrderStatusDto updateOrderStatusDto = orderService.updateOrderStatus(orderId, afterOrder.getOrderStatus().toString());

        Assertions.assertThat(updateOrderStatusDto)
                .extracting("orderId", "beforeOrderStatus", "afterOrderStatus")
                .contains(orderId, beforeOrder.getOrderStatus().toString(), afterOrder.getOrderStatus().toString());
    }

    @Test
    @DisplayName("컨트롤러에게 전달 받은 인증 정보, 주문 번호를 GetOrderUseCase 에게 전달하고 결과를 반환한다.")
    void getOrder() {

        Order order = getOrderedObject(Order.class).get(0);
        String orderId = order.getOrderId();

        BDDMockito.given(getOrderUseCase.execute(orderId))
                .willReturn(new OrderProcessDto(null, order));

        OrderProcessDto resultOrderDto = orderService.getOrderByOrderId(orderId);

        Assertions.assertThat(resultOrderDto)
                .extracting("order.orderId", "order.userId")
                .contains(order.getOrderId(), order.getUserId());
    }

    @Test
    @DisplayName("컨트롤러에게 전달 받은 인증 정보, 회원 번호를 GetOrdersUseCase 에게 전달하고 결과를 반환한다.")
    void getOrders() {

        Order firstOrder = getOrderedObject(Order.class).get(0);
        Order secondOrder = getOrderedObject(Order.class).get(1);
        String userId = firstOrder.getUserId();

        changeFieldValue(secondOrder, "userId", userId);

        BDDMockito.given(getOrdersUseCase.execute(userId))
                .willReturn(List.of(new OrderProcessDto(null, firstOrder), new OrderProcessDto(null, secondOrder)));

        List<OrderProcessDto> returnedOrderDto = orderService.getOrders(userId);

        Assertions.assertThat(returnedOrderDto)
                .extracting("order")
                .containsExactly(firstOrder, secondOrder);
    }

    @Override
    protected List<OrderSheet> ordersObject() {
        return List.of(OrderSheet.order(orderCustom(Order.class)
                .set("orderId", UUID.randomUUID().toString())
                .size("items", 2, 5), 2));
    }
}