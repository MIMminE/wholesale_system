package nuts.project.wolesale_system.order;

import lombok.RequiredArgsConstructor;
import nuts.project.wolesale_system.order.event.Publisher;
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
    @RequiredArgsConstructor
    static class Runner implements CommandLineRunner {
        private final Publisher publisher;

        @Override
        public void run(String... args) throws Exception {
            publisher.test();
        }
    }

}
