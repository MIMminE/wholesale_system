package nuts.project.wholesale_system.order.domain.service.usecase.get;

import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.support.UseCaseTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetOrderUseCaseTest extends UseCaseTestSupport {
    @DisplayName("getOrderUseCase 성공 테스트")
    @Test
    void getOrderUseCaseSuccess() {
        // given
        Order order = getOrderedObject(Order.class).get(0);
        String userId = order.getUserId();
        List<OrderItem> items = order.getItems();

        // when
//        getOrderUseCase.execute();

        // then
    }

    @DisplayName("getOrderUseCase 예외 발생 테스트 : 조회한 주문 번호에 대한 정보가 없을 때")
    @Test
    void getOrderUseCaseException() {
        // given
        Order order = getOrderedObject(Order.class).get(0);
        String userId = order.getUserId();
        List<OrderItem> items = order.getItems();

        // when
//        getOrderUseCase.execute();

        // then
    }

}