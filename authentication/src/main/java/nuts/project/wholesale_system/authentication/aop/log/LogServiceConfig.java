package nuts.project.wholesale_system.authentication.aop.log;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogServiceConfig {

    public static String AUTHENTICATION_LOG_QUEUE = "member_log";

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue memberLogQueue() {
        return new Queue(AUTHENTICATION_LOG_QUEUE, false);
    }

    @Bean
    DirectExchange memberLogExchange() {
        return new DirectExchange(AUTHENTICATION_LOG_QUEUE);
    }

    @Bean
    Binding bindingMemberLogQueue() {
        return BindingBuilder.bind(memberLogQueue()).to(memberLogExchange()).with("_key");
    }
}
