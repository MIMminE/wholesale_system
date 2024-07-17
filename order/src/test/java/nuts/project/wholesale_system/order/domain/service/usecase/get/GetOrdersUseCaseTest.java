package nuts.project.wholesale_system.order.domain.service.usecase.get;

import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.support.UseCaseTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetOrdersUseCaseTest extends UseCaseTestSupport {
    @DisplayName("getOrdersUseCase 성공 테스트")
    @Test
    void getOrdersUseCaseSuccess() {
        // given
        Order order = getOrderedObject(Order.class).get(0);
        String userId = order.getUserId();
        List<OrderItem> items = order.getItems();

        // when
//        getOrdersUseCase.execute();

        // then
    }

    @DisplayName("getOrdersUseCase 예외 발생 테스트 : 조회한 유저에 대한 주문 정보가 없을 때")
    @Test
    void getOrdersUseCaseException() {
        // given
        Order order = getOrderedObject(Order.class).get(0);
        String userId = order.getUserId();
        List<OrderItem> items = order.getItems();

        // when
//        getOrdersUseCase.execute();

        // then
    }
}