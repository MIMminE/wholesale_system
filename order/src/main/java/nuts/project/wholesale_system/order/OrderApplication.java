package nuts.project.wholesale_system.order;

import nuts.project.wholesale_system.order.event.RabbitConsumer;
import nuts.project.wholesale_system.order.event.RabbitProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);

    }

    @Component
    static class Runner implements CommandLineRunner {
        private final RabbitProducer publisher;
        private final RabbitTemplate rabbitTemplate;
        private final RabbitConsumer rabbitConsumer;

        public Runner(RabbitProducer publisher, RabbitTemplate rabbitTemplate) {
            this.publisher = publisher;
            this.rabbitTemplate = rabbitTemplate;
            this.rabbitConsumer = new RabbitConsumer(rabbitTemplate, "test_de_queue");
        }

        @Override
        public void run(String... args) throws Exception {
            try {

                rabbitConsumer.receive();
            } catch (Exception e) {
                System.out.println(e);
            }
            publisher.test();
        }
    }

}
