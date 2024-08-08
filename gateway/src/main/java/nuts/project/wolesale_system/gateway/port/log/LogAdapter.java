package nuts.project.wolesale_system.gateway.port.log;

import org.springframework.stereotype.Component;

@Component
public class LogAdapter implements LogPort{

    @Override
    public void send(String message, String queueName) {
        System.out.println(message + " " + queueName);
    }
}
