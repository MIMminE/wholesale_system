package nuts.project.wholesale_system.order.adapter.outbound.log;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogServiceConfig {

    public static String ORDER_LOG_QUEUE = "order_log";

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue memberLogQueue() {
        return new Queue(ORDER_LOG_QUEUE, false);
    }

    @Bean
    DirectExchange memberLogExchange() {
        return new DirectExchange(ORDER_LOG_QUEUE);
    }

    @Bean
    Binding bindingMemberLogQueue() {
        return BindingBuilder.bind(memberLogQueue()).to(memberLogExchange()).with("_key");
    }
}
