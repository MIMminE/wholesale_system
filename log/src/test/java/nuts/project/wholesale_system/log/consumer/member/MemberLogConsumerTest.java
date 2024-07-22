package nuts.project.wholesale_system.log.consumer.member;

import nuts.project.wholesale_system.log.LogType;
import nuts.project.wholesale_system.log.consumer.member.support.LogTestSupport;
import nuts.project.wholesale_system.log.repository.order.OrderLog;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


class MemberLogConsumerTest extends LogTestSupport {

    @Test
    @DisplayName("orderLogListener 동작 성공 테스트")
    void orderLogListener() {
        // given
        String testLog = "test string log";
        rabbitTemplate.convertAndSend("order_log", "_key", Map.of("logType", LogType.error.name(), "log",testLog));

        // when
        OrderLog byLog = orderLogRepository.findByLog(testLog);
        System.out.printf(byLog.toString());
        // then
        Assertions.assertThat(byLog).extracting("logType", "log").contains(LogType.error, testLog);
    }

}