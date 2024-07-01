package nuts.project.wholesale_system.order.event;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private static final String EXCHANGE_NAME = "sample.exchange3";
    private static final String QUEUE_NAME = "sample.queue213";
    private static final String ROUTING_KEY = "sample.routing";

//    @Bean
//    DirectExchange exchange() {
//        return new DirectExchange(EXCHANGE_NAME);
//    }
//
//    @Bean
//    Queue queue() {
//        return new Queue(QUEUE_NAME, true);
//    }
//
//    @Bean
//    Binding binding(Queue queue, DirectExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
//    }

//    @Bean
//    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//
//        return rabbitTemplate;
//    }
}
