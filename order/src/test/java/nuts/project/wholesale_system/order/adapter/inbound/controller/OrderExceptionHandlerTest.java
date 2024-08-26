package nuts.project.wholesale_system.order.adapter.inbound.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nuts.lib.manager.fixture_manager.FixtureManager;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust.CreateOrderRequest;
import nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust.DeleteOrderRequest;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.ports.payment.request.PaymentCreateRequest;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.ports.payment.response.PaymentCreateResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequestType;
import nuts.project.wholesale_system.order.domain.ports.stock.StockResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockServicePort;
import nuts.project.wholesale_system.order.domain.ports.stock.request.StockDeductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static nuts.lib.manager.fixture_manager.FixtureManager.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Profile("test")
@SpringBootTest
@AutoConfigureMockMvc
class OrderExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StockServicePort stockService;

    @MockBean
    PaymentServicePort paymentService;

    ObjectMapper mapper = new ObjectMapper();

    FixtureManager fixtureManager = new FixtureManager(
            List.of(
                    OrderSheet.order(orderCustom(CreateOrderRequest.class).minSize("orderItems", 1), 1),
                    OrderSheet.order(orderCustom(DeleteOrderRequest.class).set("orderId", UUID.randomUUID().toString()), 1)
            )
    );

    @DisplayName("재고 서비스와의 통신이 실패한 경우 발생한 예외를 핸들링한다.")
    @Test
    void stockServiceExceptionHandler() throws Exception {
        // given
        CreateOrderRequest createOrderRequest = fixtureManager.getOrderObject(CreateOrderRequest.class);
        BDDMockito.given(paymentService.requestPayment(any(PaymentCreateRequest.class)))
                .willReturn(new PaymentCreateResponse(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), 10000));

        BDDMockito.given(stockService.deductStock(any(StockDeductRequest.class)))
                .willThrow(new OrderException(OrderException.OrderExceptionCase.STOCK_SERVICE_FAIL));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/order-service/api/v1/orders")
                .header("Gateway-Pass", "pass")
                .header("User-Id", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(createOrderRequest)));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(OrderException.OrderExceptionCase.STOCK_SERVICE_FAIL.getMessage()));

    }

    @DisplayName("결제 서비스와의 통신이 실패한 경우 발생한 예외를 핸들링한다.")
    @Test
    void paymentServiceExceptionHandler() throws Exception {
        // given
        CreateOrderRequest createOrderRequest = fixtureManager.getOrderObject(CreateOrderRequest.class);
        BDDMockito.given(paymentService.requestPayment(any(PaymentCreateRequest.class)))
                .willThrow(new OrderException(OrderException.OrderExceptionCase.PAYMENT_SERVICE_FAIL));

        BDDMockito.given(stockService.deductStock(any(StockDeductRequest.class)))
                .willReturn(new StockResponse(StockRequestType.Deduct, true));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/order-service/api/v1/orders")
                .header("Gateway-Pass", "pass")
                .header("User-Id", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(createOrderRequest)));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(OrderException.OrderExceptionCase.PAYMENT_SERVICE_FAIL.getMessage()));

    }
}