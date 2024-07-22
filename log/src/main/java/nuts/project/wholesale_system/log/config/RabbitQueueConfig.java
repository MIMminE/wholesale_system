package nuts.project.wholesale_system.log.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfig {

    public static String MEMBER_LOG_QUEUE = "member_log";
    public static String ORDER_LOG_QUEUE = "order_log";
    public static String STOCK_LOG_QUEUE = "stock_log";

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue memberLogQueue() {
        return new Queue(MEMBER_LOG_QUEUE, true);
    }

    @Bean
    DirectExchange memberLogExchange() {
        return new DirectExchange(MEMBER_LOG_QUEUE);
    }

    @Bean
    Binding bindingMemberLogQueue() {
        return BindingBuilder.bind(memberLogQueue()).to(memberLogExchange()).with("_key");
    }


    @Bean
    Queue orderLogQueue() {
        return new Queue(ORDER_LOG_QUEUE, true);
    }

    @Bean
    DirectExchange orderLogExchange() {
        return new DirectExchange(ORDER_LOG_QUEUE);
    }

    @Bean
    Binding bindingOrderLogQueue() {
        return BindingBuilder.bind(orderLogQueue()).to(orderLogExchange()).with("_key");
    }


    @Bean
    Queue stockLogQueue() {
        return new Queue(STOCK_LOG_QUEUE, true);
    }

    @Bean
    DirectExchange stockLogExchange() {
        return new DirectExchange(STOCK_LOG_QUEUE);
    }

    @Bean
    Binding bindingStockLogQueue() {
        return BindingBuilder.bind(stockLogQueue()).to(stockLogExchange()).with("_key");
    }
}
