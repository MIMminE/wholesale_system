package nuts.project.wolesale_system.gateway.routing;

import lombok.RequiredArgsConstructor;
import nuts.project.wolesale_system.gateway.filter.member.MemberTokenFilter;
import nuts.project.wolesale_system.gateway.filter.order.OrderLogFilter;
import nuts.project.wolesale_system.gateway.filter.order.OrderAuthenticationFilter;
import nuts.project.wolesale_system.gateway.port.authentication.AuthenticationPort;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RoutingMapping {

    final OrderAuthenticationFilter orderAuthenticationFilter;
    final OrderLogFilter orderLogFilter;
    final AuthenticationPort authenticationAdapter;
    final RoutingConfig routingConfig;

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("member-service/**", this::memberServiceRoute)
                .route("order-service/**", this::orderServiceRoute)
                .route("stock-service/**", this::stockServiceRoute)
                .build();
    }

    private Buildable<Route> memberServiceRoute(PredicateSpec r) {

        String targetUrl = createUrl("member-service");
        return r.path("/member-service/api/v1/**")
                .filters(f -> f
                        .filter(new MemberTokenFilter()))
//                .filters(f -> f
//                        .filter(orderAuthenticationFilter)
//                        .filter(orderLogFilter))
                .uri(targetUrl);
    }


    private Buildable<Route> orderServiceRoute(PredicateSpec r) {

        String targetUrl = createUrl("order-service");
        return r.path("/order-service/api/v1/**")
//                .filters(f -> f
//                        .filter(orderAuthenticationFilter)
//                        .filter(orderLogFilter))
                .uri(targetUrl);
    }

    private Buildable<Route> stockServiceRoute(PredicateSpec r) {

        String targetUrl = createUrl("stock-service");
        return r.path("/stock-service/**")
//                .filters(f -> f
//                        .filter(new MemberTokenFilter()))
                .uri(targetUrl);
    }

    private String createUrl(String routingKey) {
        return String.format("http://%s:%s/%s", routingConfig.getRoutingTable().get(routingKey).getUrl(),
                routingConfig.getRoutingTable().get(routingKey).getPort(), routingKey);
    }
}

