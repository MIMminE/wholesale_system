package nuts.project.wholesale_system.order.event;

import nuts.lib.manager.broker_manager.rabbitmq.RabbitMqProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitProducer {

    private final RabbitMqProducer rabbitMqProducer;
    private static final String EXCHANGE_NAME = "wholesale_system";

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitMqProducer = new RabbitMqProducer(rabbitTemplate, "sample.exchange", "routingKey");
    }

    public void test() {
        rabbitMqProducer.send("12345678910");
    }


}
