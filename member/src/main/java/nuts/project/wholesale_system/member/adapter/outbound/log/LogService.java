package nuts.project.wholesale_system.member.adapter.outbound.log;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.domain.port.log.LogServicePort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

import static nuts.project.wholesale_system.member.adapter.outbound.log.LogServiceConfig.MEMBER_LOG_QUEUE;

@Service
@RequiredArgsConstructor
public class LogService implements LogServicePort {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishing(Map<String, Object> message) {
        rabbitTemplate.convertAndSend(MEMBER_LOG_QUEUE, "_key", message);
    }
}
