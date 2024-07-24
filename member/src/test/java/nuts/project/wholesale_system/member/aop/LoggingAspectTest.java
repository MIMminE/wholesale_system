package nuts.project.wholesale_system.member.aop;

import jakarta.transaction.Transactional;
import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationRepository;
import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.service.corporation.CorporationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static nuts.project.wholesale_system.member.adapter.outbound.log.LogServiceConfig.MEMBER_LOG_QUEUE;
import static nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException.CorporationExceptionCase.GET_NO_SUCH_ELEMENT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ExtendWith(MockitoExtension.class)
class LoggingAspectTest extends FixtureGenerateSupport {

    @LocalServerPort
    private int port;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    CorporationRepository corporationRepository;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    LoggingAspect loggingAspect;

    @MockBean
    CorporationService corporationService;

    @Test
    @Order(1)
    @DisplayName("[ Log Service Test ] : Successful Test")
    void afterAdviceSuccessLog() {
        Corporation corporation = getOrderedObject(Corporation.class).get(0);
        String corporationId = UUID.randomUUID().toString();
        changeFieldValue(corporation, "corporationId", corporationId);

        BDDMockito.given(corporationService.getCorporation(corporationId)).willReturn(corporation);

        Map forObject = restTemplate.getForObject("http://localhost:" + port + "/api/v1/corporations/%s".formatted(corporationId), Map.class);


        Map<String, Object> requestLost = rabbitTemplate.receiveAndConvert(MEMBER_LOG_QUEUE, ParameterizedTypeReference.forType(Map.class));
        Map<String, Object> responseLog = rabbitTemplate.receiveAndConvert(MEMBER_LOG_QUEUE, ParameterizedTypeReference.forType(Map.class));

        Assertions.assertThat(responseLog).containsKeys("requestId", "logType", "log");
        Assertions.assertThat(Objects.requireNonNull(responseLog).get("logType")).isEqualTo("info");
    }

    @Test
    @Order(2)
    @DisplayName("[ Log Service Test ] : Exception Test")
    void afterAdviceExceptionLog() {
        String corporationId = UUID.randomUUID().toString();

        BDDMockito.given(corporationService.getCorporation(corporationId)).willThrow(new CorporationUseCaseException(GET_NO_SUCH_ELEMENT));

        Map forObject = restTemplate.getForObject("http://localhost:" + port + "/api/v1/corporations/%s".formatted(corporationId), Map.class);

        Map<String, Object> requestLost = rabbitTemplate.receiveAndConvert(MEMBER_LOG_QUEUE, ParameterizedTypeReference.forType(Map.class));
        Map<String, Object> responseLog = rabbitTemplate.receiveAndConvert(MEMBER_LOG_QUEUE, ParameterizedTypeReference.forType(Map.class));


        Assertions.assertThat(responseLog).containsKeys("requestId", "logType", "log");
        Assertions.assertThat(Objects.requireNonNull(responseLog).get("logType")).isEqualTo("error");
    }

    @Test
    @Order(3)
    @DisplayName("[ Log Service Test ] : Input Log Test")
    void afterAdvice() {
        String corporationId = UUID.randomUUID().toString();

        BDDMockito.given(corporationService.getCorporation(corporationId)).willThrow(new CorporationUseCaseException(GET_NO_SUCH_ELEMENT));

        Map forObject = restTemplate.getForObject("http://localhost:" + port + "/api/v1/corporations/%s".formatted(corporationId), Map.class);

        Map<String, Object> requestLost = rabbitTemplate.receiveAndConvert(MEMBER_LOG_QUEUE, ParameterizedTypeReference.forType(Map.class));
        Map<String, Object> responseLog = rabbitTemplate.receiveAndConvert(MEMBER_LOG_QUEUE, ParameterizedTypeReference.forType(Map.class));

        Assertions.assertThat(requestLost).containsKeys("requestId", "logType", "log");
        Assertions.assertThat(Objects.requireNonNull(requestLost).get("logType")).isEqualTo("info");

    }

    @Override
    protected List<OrderSheet> ordersObject() {

        String regex = "010-\\d{4}-\\d{4}";

        return List.of(OrderSheet.order(orderCustom(Corporation.class).set("contactNumber", Arbitraries.strings()
                .withChars("0123456789")
                .ofLength(10)
                .map(s -> "010-" + s.substring(0, 4) + "-" + s.substring(4, 8))
                .filter(s -> s.matches(regex))), 5));

    }
}