package nuts.project.wholesale_system.order.event;

import lombok.extern.slf4j.Slf4j;
import nuts.lib.manager.broker_manager.rabbitmq.AbstractRabbitMqConsumer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.function.Consumer;

@Slf4j
public class RabbitConsumer extends AbstractRabbitMqConsumer {

    public RabbitConsumer(RabbitTemplate rabbitTemplate, String queueName) {
        super(rabbitTemplate, queueName, 2);
    }

    @Override
    protected Consumer<Object> getCallback() {

        return (o)-> log.info("{}",o);
    }
}
