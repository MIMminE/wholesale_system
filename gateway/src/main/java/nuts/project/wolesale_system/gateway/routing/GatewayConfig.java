package nuts.project.wolesale_system.gateway.routing;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayConfig {

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("order-service", r -> r.path("/order-service/**")
                        .filters(f -> f.filter(((exchange, chain) -> {
                            String simpleJwt = exchange.getRequest().getHeaders().get("Simple-Jwt").get(0);
                            exchange.getRequest().mutate().header("Jwt-Token-id", simpleJwt);
                            System.out.println("filter On");
                            return chain.filter(exchange);
                        })))
                        .uri("http://localhost:9004/order-service"))
                .build();
    }

}

