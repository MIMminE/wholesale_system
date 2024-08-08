package nuts.project.wolesale_system.gateway.filter.order;

import lombok.RequiredArgsConstructor;
import nuts.project.wolesale_system.gateway.port.authentication.AuthenticationPort;
import nuts.project.wolesale_system.gateway.port.authentication.dto.ValidationResult;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrderAuthenticationFilter implements GatewayFilter {

    private final AuthenticationPort authenticationAdapter;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String simpleJwt = Objects.requireNonNull(exchange.getRequest().getHeaders().get("Jwt-Token")).get(0);
        ValidationResult validationResult = authenticationAdapter.tokenValidation(simpleJwt);
        addCustomHeaders(exchange, validationResult);
        exchange.getRequest().mutate().header("User-Role", validationResult.getRoleName());
        exchange.getRequest().mutate().header("Gateway-Sign", UUID.randomUUID().toString());

        return chain.filter(exchange);
    }

    public void addCustomHeaders(ServerWebExchange exchange, ValidationResult validationResult) {
    }
}
