package nuts.project.wolesale_system.gateway.port.log;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogAdapter implements LogPort {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(String routingKey, String exchange, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
