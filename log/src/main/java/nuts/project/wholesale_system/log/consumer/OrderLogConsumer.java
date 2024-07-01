package nuts.project.wholesale_system.log.consumer;

import lombok.extern.slf4j.Slf4j;
import nuts.lib.manager.broker_manager.rabbitmq.AbstractRabbitMqConsumer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service @Slf4j
public class OrderLogConsumer extends AbstractRabbitMqConsumer {

    private static final String ORDER_LOG_QUEUE_NAME = "order_log";

    public OrderLogConsumer(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate, ORDER_LOG_QUEUE_NAME);
    }

    @Override
    protected Consumer<Object> getCallback() {
        return System.out::println;
    }
}
