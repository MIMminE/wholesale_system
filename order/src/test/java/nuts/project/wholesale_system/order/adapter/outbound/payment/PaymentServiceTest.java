package nuts.project.wholesale_system.order.adapter.outbound.payment;

import nuts.lib.manager.fixture_manager.FixtureManager;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.ports.payment.request.PaymentCreateRequest;
import nuts.project.wholesale_system.order.domain.ports.payment.request.PaymentDeleteRequest;
import nuts.project.wholesale_system.order.domain.ports.payment.response.PaymentCreateResponse;
import nuts.project.wholesale_system.order.domain.ports.payment.response.PaymentDeleteResponse;
import nuts.project.wholesale_system.order.domain.ports.payment.response.PaymentResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static nuts.lib.manager.fixture_manager.FixtureManager.orderCustom;
import static nuts.project.wholesale_system.order.domain.exception.OrderException.OrderExceptionCase.PAYMENT_NOT_FOUND;
import static nuts.project.wholesale_system.order.domain.exception.OrderException.OrderExceptionCase.PAYMENT_SERVICE_FAIL;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@ActiveProfiles("test")
class PaymentServiceTest {

    FixtureManager fixtureManager = new FixtureManager(
            List.of(
                    OrderSheet.order(orderCustom(PaymentCreateRequest.class)
                            .set("userId", UUID.randomUUID().toString())
                            .set("orderId", UUID.randomUUID().toString()), 1),

                    OrderSheet.order(orderCustom(PaymentDeleteRequest.class)
                            .set("orderId", UUID.randomUUID().toString()), 1)
            )
    );

    @MockBean
    RestTemplate restTemplate;

    @Autowired
    PaymentServicePort paymentService;

    @DisplayName("결제 계좌 요청에서 결제 서비스와의 통신이 실패할 경우 예외가 발생한다.")
    @Test
    void requestPaymentServerException() {
        // given
        PaymentCreateRequest paymentCreateRequest = fixtureManager.getOrderObject(PaymentCreateRequest.class);
        BDDMockito.given(restTemplate.postForEntity(anyString(), eq(paymentCreateRequest), eq(Map.class))).willThrow(ResourceAccessException.class);

        // when then
        Assertions.assertThatThrownBy(() -> paymentService.requestPayment(paymentCreateRequest)).hasMessage(PAYMENT_SERVICE_FAIL.getMessage());
    }

    @DisplayName("결제 계좌 요청이 성공적으로 수행되고 결과를 반환한다.")
    @Test
    void requestPaymentSuccess() {
        // given
        String accountNumber = UUID.randomUUID().toString();
        int totalPrice = 15000;

        PaymentCreateRequest paymentCreateRequest = fixtureManager.getOrderObject(PaymentCreateRequest.class);
        BDDMockito.given(restTemplate.postForEntity(anyString(), eq(paymentCreateRequest), eq(Map.class)))
                .willReturn(ResponseEntity.ok(Map.of("accountNumber", accountNumber, "totalPrice", totalPrice)));

        // when
        PaymentCreateResponse paymentCreateResponse = paymentService.requestPayment(paymentCreateRequest);

        // then
        Assertions.assertThat(paymentCreateResponse).extracting("userId", "orderId", "accountNumber", "totalPrice")
                .contains(paymentCreateRequest.getUserId(), paymentCreateResponse.getOrderId(), accountNumber, totalPrice);
    }


    @DisplayName("결제 계좌 조회에서 결제 서비스와의 통신이 실패할 경우 예외가 발생한다.")
    @Test
    void getPaymentServerException() {
        // given
        String searchOrderId = UUID.randomUUID().toString();
        BDDMockito.given(restTemplate.getForEntity(BDDMockito.contains(searchOrderId), eq(Map.class))).willThrow(ResourceAccessException.class);

        // when then
        Assertions.assertThatThrownBy(() -> paymentService.getPayment(searchOrderId)).hasMessage(PAYMENT_SERVICE_FAIL.getMessage());
    }

    @DisplayName("결제 계좌 조회가 성공적으로 수행되고 결과를 반환한다.")
    @Test
    void getPaymentSuccess() {
        // given
        String userId = UUID.randomUUID().toString();
        String searchOrderId = UUID.randomUUID().toString();
        String accountNumber = UUID.randomUUID().toString();
        int totalPrice = 15000;

        BDDMockito.given(restTemplate.getForEntity(BDDMockito.contains(searchOrderId), eq(Map.class)))
                .willReturn(ResponseEntity.ok(Map.of("findPayment", true, "userId", userId, "accountNumber", accountNumber, "totalPrice", totalPrice)));

        // when
        PaymentResponse paymentResponse = paymentService.getPayment(searchOrderId);

        // then
        Assertions.assertThat(paymentResponse).extracting("userId", "orderId", "accountNumber", "totalPrice")
                .contains(userId, searchOrderId, accountNumber, totalPrice);
    }

    @DisplayName("결제 계좌 조회에서 조회할 대상이 없을 경우 예외가 발생한다.")
    @Test
    void getPaymentNotFoundException() {
        // given
        String searchOrderId = UUID.randomUUID().toString();

        BDDMockito.given(restTemplate.getForEntity(BDDMockito.contains(searchOrderId), eq(Map.class)))
                .willReturn(ResponseEntity.ok(Map.of("findPayment", false)));

        // when then
        Assertions.assertThatThrownBy(() -> paymentService.getPayment(searchOrderId)).hasMessage(PAYMENT_NOT_FOUND.getMessage());
    }


    @DisplayName("결제 정보 삭제 요청에서 결제 서비스와 통신이 실패할 경우 예외가 발생한다.")
    @Test
    void deletePaymentInformationServerException() {
        // given
        PaymentDeleteRequest paymentDeleteRequest = fixtureManager.getOrderObject(PaymentDeleteRequest.class);
        BDDMockito.given(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), eq(new HttpEntity<>(paymentDeleteRequest)), eq(Map.class)))
                .willThrow(ResourceAccessException.class);

        // when then
        Assertions.assertThatThrownBy(() -> paymentService.deletePaymentInformation(paymentDeleteRequest)).hasMessage(PAYMENT_SERVICE_FAIL.getMessage());

    }

    @DisplayName("결제 정보 삭제 요청이 성공적으로 수행되고 결과를 반환한다.")
    @Test
    void deletePaymentInformationSuccess() {
        // given
        PaymentDeleteRequest paymentDeleteRequest = fixtureManager.getOrderObject(PaymentDeleteRequest.class);
        BDDMockito.given(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), eq(new HttpEntity<>(paymentDeleteRequest)), eq(Map.class)))
                .willReturn(ResponseEntity.ok(Map.of("userId", UUID.randomUUID().toString(), "orderId", paymentDeleteRequest.getOrderId())));

        // when
        PaymentDeleteResponse paymentDeleteResponse = paymentService.deletePaymentInformation(paymentDeleteRequest);

        // then
        Assertions.assertThat(paymentDeleteResponse).extracting("orderId", "deleted")
                .contains(paymentDeleteRequest.getOrderId(), true);
    }
}