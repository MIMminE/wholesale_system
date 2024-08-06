package nuts.project.wholesale_system.order.adapter.inbound.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureManager;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust.CreateOrderRequest;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust.DeleteOrderRequest;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust.UpdateOrderStatusRequest;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.service.usecase.delete.DeleteOrderIdUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.get.GetOrderUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.get.GetOrdersUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.update.UpdateOrderStatusUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static nuts.lib.manager.fixture_manager.FixtureManager.orderCustom;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderUseCaseExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteOrderIdUseCase deleteOrderIdUseCase;

    @MockBean
    private UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @MockBean
    private GetOrderUseCase getOrderUseCase;

    @MockBean
    private GetOrdersUseCase getOrdersUseCase;

    ObjectMapper mapper = new ObjectMapper();

    FixtureManager fixtureManager = new FixtureManager(
            List.of(
                    OrderSheet.order(orderCustom(CreateOrderRequest.class)
                            .minSize("orderItems", 1), 1),

                    OrderSheet.order(orderCustom(DeleteOrderRequest.class)
                            .set("orderId", UUID.randomUUID().toString()), 1),

                    OrderSheet.order(orderCustom(UpdateOrderStatusRequest.class)
                            .set("orderId", UUID.randomUUID().toString())
                            .set("orderStatus", Arbitraries.of(Arrays.stream(OrderStatus.values()).map(Enum::toString).toList())), 1))
    );

    @DisplayName("상품 삭제 요청 정보의 주문번호에 해당하는 데이터가 없을 경우 발생하는 예외를 핸들링한다.")
    @Test
    void deleteOrderExceptionHandler() throws Exception {
        // given
        DeleteOrderRequest deleteOrderRequest = fixtureManager.getOrderObject(DeleteOrderRequest.class);

        BDDMockito.given(deleteOrderIdUseCase.execute(deleteOrderRequest.getOrderId()))
                .willThrow(new OrderException(OrderException.OrderExceptionCase.DELETE_NO_SUCH_ELEMENT));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/order-service/api/v1/orders")
                .header("Gateway-Pass", "pass")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(deleteOrderRequest)));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(OrderException.OrderExceptionCase.DELETE_NO_SUCH_ELEMENT.getMessage()));
    }

    @DisplayName("주문 상태 변경 요청 정보의 주문번호에 해당하는 주문이 없을 경우 발생하는 예외를 핸들링한다.")
    @Test
    void updateOrderStatusExceptionHandler() throws Exception {
        // given
        UpdateOrderStatusRequest updateOrderStatusRequest = fixtureManager.getOrderObject(UpdateOrderStatusRequest.class);

        BDDMockito.given(updateOrderStatusUseCase.execute(updateOrderStatusRequest.getOrderId(), OrderStatus.delivering))
                .willThrow(new OrderException(OrderException.OrderExceptionCase.UPDATE_NO_SUCH_ELEMENT));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/order-service/api/v1/orders/status")
                .header("Gateway-Pass", "pass")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(updateOrderStatusRequest)));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(OrderException.OrderExceptionCase.UPDATE_NO_SUCH_ELEMENT.getMessage()));
    }

    @DisplayName("주문번호에 해당하는 주문이 없을 경우 발생하는 예외를 핸들링한다.")
    @Test
    void getOrderExceptionHandler() throws Exception {
        // given
        String orderId = UUID.randomUUID().toString();
        BDDMockito.given(getOrderUseCase.execute(orderId))
                .willThrow(new OrderException(OrderException.OrderExceptionCase.GET_NO_SUCH_ELEMENT));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/order-service/api/v1/orders/{orderId}", orderId)
                .header("Gateway-Pass", "pass"));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(OrderException.OrderExceptionCase.GET_NO_SUCH_ELEMENT.getMessage()));
    }

    @DisplayName("회원 주문 전체 조회에서 잘못된 회원 번호로 요청을 보냈을 경우 발생한 예외를 핸들링한다.")
    @Test
    void getOrdersByMemberIdExceptionHandler() throws Exception {
        // given
        String userId = UUID.randomUUID().toString();
        BDDMockito.given(getOrdersUseCase.execute(userId))
                .willThrow(new OrderException(OrderException.OrderExceptionCase.GET_NO_SUCH_ELEMENT));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/order-service/api/v1/orders/user/{userId}", userId)
                .header("Gateway-Pass", "pass"));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(OrderException.OrderExceptionCase.GET_NO_SUCH_ELEMENT.getMessage()));
    }
}
