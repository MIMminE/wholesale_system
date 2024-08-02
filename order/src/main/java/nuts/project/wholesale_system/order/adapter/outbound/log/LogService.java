package nuts.project.wholesale_system.order.adapter.outbound.log;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.ports.log.LogServicePort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

import static nuts.project.wholesale_system.order.adapter.outbound.log.LogServiceConfig.ORDER_LOG_QUEUE;

@Service
@RequiredArgsConstructor
public class LogService implements LogServicePort {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishing(Map<String, Object> message) {
        rabbitTemplate.convertAndSend(ORDER_LOG_QUEUE, "_key", message);
    }
}
