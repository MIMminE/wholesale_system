package nuts.project.wholesale_system.order.domain.service.usecase.create;

import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.support.UseCaseTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.List;


class CreateOrderUseCaseTest extends UseCaseTestSupport {

    @DisplayName("createOrderUseCase 성공 테스트")
    @Test
    void createOrderUseCaseSuccess() {

        // given
        Order order = getOrderedObject(Order.class).get(0);
        String orderId = order.getOrderId();
        String userId = order.getUserId();
        List<OrderItem> items = order.getItems();

        BDDMockito.given(paymentService.requestPayment())

        // when
        OrderProcessDto execute = createOrderUseCase.execute(userId, items);

        // then
        System.out.println(execute);
    }

    @DisplayName("createOrderUseCase payment service exception")
    @Test
    void createOrderUseCaseException() {

        // given
        Order order = getOrderedObject(Order.class).get(0);
        String userId = order.getUserId();
        List<OrderItem> items = order.getItems();

        BDDMockito.given(mockPaymentService.getPaymentInformation(userId)).willThrow(new RuntimeException());

        // when
        OrderProcessDto orderProcessDto = mockCreateOrderUseCase.execute(userId, items);

        // then
    }
}