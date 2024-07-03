package nuts.project.wolesale_system.gateway.routing;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class GatewayConfig {
//
//    @Bean
//    RouteLocator routeLocator(RouteLocatorBuilder builder){
//        return builder.routes()
//                .route("/test", r-> r.path("/test")
//                        .filters(f->f.filter((exchange, chain) -> {
//                            System.out.println(exchange.getRequest().getHeaders());
//                            return chain.filter(exchange);
//                        })).uri(URI.create("http://localhost:9004/orders/harunuts"))).build();
//    }
}

