package nuts.project.wholesale_system.log.consumer.member;

import nuts.project.wholesale_system.log.LogType;
import nuts.project.wholesale_system.log.consumer.member.support.LogTestSupport;
import nuts.project.wholesale_system.log.repository.member.MemberLog;
import nuts.project.wholesale_system.log.repository.order.OrderLog;
import nuts.project.wholesale_system.log.repository.stock.StockLog;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;

import java.util.Map;


class LogConsumerTest extends LogTestSupport {

    @Test
    @DisplayName("memberLogListener 동작 성공 테스트")
    @Commit
    void memberLogListener() {
        // given
        String testLog = "test string log";
        rabbitTemplate.convertAndSend("member_log", "_key", Map.of("logType", LogType.error.name(), "log", testLog));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // when
        MemberLog byLog = memberLogRepository.findByLog(testLog);
        // then
        Assertions.assertThat(byLog).extracting("logType", "log").contains(LogType.error, testLog);

        memberLogRepository.deleteAll();
    }

    @Test
    @DisplayName("orderLogListener 동작 성공 테스트")
    @Commit
    void orderLogListener() {
        // given
        String testLog = "test string log";
        rabbitTemplate.convertAndSend("order_log", "_key", Map.of("logType", LogType.error.name(), "log", testLog));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // when
        OrderLog byLog = orderLogRepository.findByLog(testLog);
        // then
        Assertions.assertThat(byLog).extracting("logType", "log").contains(LogType.error, testLog);

        orderLogRepository.deleteAll();
    }

    @Test
    @DisplayName("stockLogListener 동작 성공 테스트")
    @Commit
    void stockLogListener() {
        // given
        String testLog = "test string log";
        rabbitTemplate.convertAndSend("stock_log", "_key", Map.of("logType", LogType.error.name(), "log", testLog));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // when
        StockLog byLog = stockLogRepository.findByLog(testLog);
        // then
        Assertions.assertThat(byLog).extracting("logType", "log").contains(LogType.error, testLog);

        stockLogRepository.deleteAll();
    }
}