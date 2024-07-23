package nuts.project.wholesale_system.log.consumer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.log.LogType;
import nuts.project.wholesale_system.log.repository.member.MemberLog;
import nuts.project.wholesale_system.log.repository.member.MemberLogRepository;
import nuts.project.wholesale_system.log.repository.order.OrderLog;
import nuts.project.wholesale_system.log.repository.order.OrderLogRepository;
import nuts.project.wholesale_system.log.repository.stock.StockLog;
import nuts.project.wholesale_system.log.repository.stock.StockLogRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class LogConsumer {

    private final MemberLogRepository memberRepository;
    private final OrderLogRepository orderLogRepository;
    private final StockLogRepository stockRepository;

    @RabbitListener(queues = "member_log")
    public void memberLogHandle(Map<String, Object> message) {
        try {
            String logType = message.get("logType").toString();
            String logMessage = message.get("log").toString();

            MemberLog memberLog = new MemberLog(UUID.randomUUID().toString(), LogType.valueOf(logType), logMessage);

            memberRepository.save(memberLog);
        } catch (Exception e) {
            System.out.printf("예외");
        }
    }

    @RabbitListener(queues = "order_log")
    public void orderLogHandle(Map<String, Object> message) {
        try {
            String logType = message.get("logType").toString();
            String logMessage = message.get("log").toString();

            OrderLog orderLog = new OrderLog(UUID.randomUUID().toString(), LogType.valueOf(logType), logMessage);

            orderLogRepository.save(orderLog);
        } catch (Exception e) {
            System.out.printf("예외");
        }
    }

    @RabbitListener(queues = "stock_log")
    public void stockLogHandle(Map<String, Object> message) {

        try {
            String logType = message.get("logType").toString();
            String logMessage = message.get("log").toString();

            StockLog stockLog = new StockLog(UUID.randomUUID().toString(), LogType.valueOf(logType), logMessage);

            stockRepository.save(stockLog);
        } catch (Exception e) {
            System.out.printf("예외");
        }
    }
}
