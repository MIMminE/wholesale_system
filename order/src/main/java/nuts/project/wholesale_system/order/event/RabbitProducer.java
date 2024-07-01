package nuts.project.wholesale_system.order.event;

import nuts.lib.manager.broker_manager.rabbitmq.RabbitMqProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RabbitProducer extends RabbitMqProducer implements CommandLineRunner {

    private static final String EXCHANGE_NAME = "wholesale_system";
    private static final String ROUTING_KEY = "order_log.routing";

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate, EXCHANGE_NAME, ROUTING_KEY);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            Thread.sleep(2000);
            this.send("hello");
        }
    }
}
