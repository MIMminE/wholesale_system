package nuts.project.wholesale_system.authentication.aop.log;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

import static nuts.project.wholesale_system.authentication.aop.log.LogServiceConfig.AUTHENTICATION_LOG_QUEUE;


@Component
@RequiredArgsConstructor
public class LogServiceAdapter implements LogServicePort{

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishing(Map<String, Object> message) {
           rabbitTemplate.convertAndSend(AUTHENTICATION_LOG_QUEUE, "_key", message);
    }
}
