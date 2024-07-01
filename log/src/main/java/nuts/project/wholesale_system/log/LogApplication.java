package nuts.project.wholesale_system.log;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.log.consumer.OrderLogConsumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class LogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }

    @Component
    @RequiredArgsConstructor
    static class Runner implements CommandLineRunner {

        private final OrderLogConsumer orderLogConsumer;

        @Override
        public void run(String... args) throws Exception {
            orderLogConsumer.receive();
        }
    }
}
