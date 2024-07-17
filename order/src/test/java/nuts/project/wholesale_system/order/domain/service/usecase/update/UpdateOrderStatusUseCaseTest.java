package nuts.project.wholesale_system.order.domain.service.usecase.update;

import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderStatusDto;
import nuts.project.wholesale_system.order.support.UseCaseTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;

import java.util.Random;


class UpdateOrderStatusUseCaseTest extends UseCaseTestSupport {

    @Commit
    @DisplayName("updateOrderStatusUseCase 성공 테스트")
    @Test
    void updateOrderStatusUseCaseSuccess() {
        // given
        OrderEntity orderEntity = orderRepository.save(getOrderedObject(OrderEntity.class).get(0));
        String orderId = orderEntity.getOrderId();

        String updateOrderStatus = OrderStatus.values()[new Random().nextInt(OrderStatus.values().length)].toString();

        // when
        UpdateOrderStatusDto statusDto = updateOrderStatusUseCase.execute(orderId, OrderStatus.valueOf(updateOrderStatus));

        // then
        Assertions.assertThat(statusDto)
                .extracting("orderId", "beforeOrderStatus", "afterOrderStatus")
                .contains(orderId, orderEntity.getOrderStatus().toString(), updateOrderStatus);

    }

    @DisplayName("updateOrderStatusUseCase 성공 테스트")
    @Test
    void updateOrderStatusUseCaseException() {
        // given
        OrderEntity orderEntity = orderRepository.save(getOrderedObject(OrderEntity.class).get(0));
        String orderId = orderEntity.getOrderId();

        String updateOrderStatus = OrderStatus.values()[new Random().nextInt(OrderStatus.values().length)].toString();

        // when
        UpdateOrderStatusDto statusDto = updateOrderStatusUseCase.execute(orderId, OrderStatus.valueOf(updateOrderStatus));

        // then
        Assertions.assertThat(statusDto)
                .extracting("orderId", "beforeOrderStatus", "afterOrderStatus")
                .contains(orderId, orderEntity.getOrderStatus().toString(), updateOrderStatus);

    }
}