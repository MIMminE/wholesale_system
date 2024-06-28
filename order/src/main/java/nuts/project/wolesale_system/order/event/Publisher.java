package nuts.project.wolesale_system.order.event;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Publisher {

    private final String EXCHANGE_NAME = "sample.exchange";

    private final RabbitTemplate rabbitTemplate;

    public void test(){
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "sample.queue.#", "test");
    }

}
