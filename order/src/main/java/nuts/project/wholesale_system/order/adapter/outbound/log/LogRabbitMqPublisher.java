package nuts.project.wholesale_system.order.adapter.outbound.log;

import nuts.lib.manager.broker_manager.rabbitmq.RabbitMqProducer;
import nuts.project.wholesale_system.order.domain.ports.log.LogPublisherPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogRabbitMqPublisher extends RabbitMqProducer implements LogPublisherPort {

    private static final String EXCHANGE_NAME = "wholesale_system";
    private static final String ROUTING_KEY = "order_log.routing";

    public LogRabbitMqPublisher(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate, EXCHANGE_NAME, ROUTING_KEY);
    }

    @Override
    public void publishing(Object message) {
        this.send(message);
    }
}
