package nuts.project.wolesale_system.gateway.routing;

import lombok.RequiredArgsConstructor;
import nuts.project.wolesale_system.gateway.filter.order.OrderLogFilter;
import nuts.project.wolesale_system.gateway.filter.order.OrderAuthenticationFilter;
import nuts.project.wolesale_system.gateway.port.authentication.AuthenticationPort;
import nuts.project.wolesale_system.gateway.port.authentication.dto.ValidationResult;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    final OrderAuthenticationFilter orderAuthenticationFilter;
    final OrderLogFilter orderLogFilter;
    final AuthenticationPort authenticationAdapter;

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("order-service-create", this::orderCreateRoute)
                .route("order-service", this::orderServiceRoute)
                .build();
    }


    private Buildable<Route> orderCreateRoute(PredicateSpec r) {
        return r.path("/order-service/api/v1/orders").and().method(HttpMethod.POST)
                .filters(f -> f
                        .filter(new OrderAuthenticationFilter(authenticationAdapter) {
                            @Override
                            public void addCustomHeaders(ServerWebExchange exchange, ValidationResult validationResult) {
                                exchange.getRequest().mutate().header("User-Id", validationResult.getUserId());
                            }
                        })
                        .filter(orderLogFilter))
                .uri("http://localhost:9004/order-service");
    }

    private Buildable<Route> orderServiceRoute(PredicateSpec r) {
        return r.path("/order-service/api/v1/**")
                .filters(f -> f
                        .filter(orderAuthenticationFilter)
                        .filter(orderLogFilter))
                .uri("http://localhost:9004/order-service");
    }

}

