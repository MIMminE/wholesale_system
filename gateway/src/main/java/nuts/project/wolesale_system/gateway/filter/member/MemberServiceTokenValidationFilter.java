package nuts.project.wolesale_system.gateway.filter.member;

import nuts.project.wolesale_system.gateway.filter.TokenValidationFilter;
import nuts.project.wolesale_system.gateway.port.authentication.AuthenticationPort;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class MemberServiceTokenValidationFilter extends TokenValidationFilter {

    public MemberServiceTokenValidationFilter(String requiredRole, AuthenticationPort authenticationPort) {
        super(requiredRole, authenticationPort);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        Optional<String> optionalAuth = Optional.ofNullable(exchange.getRequest().getHeaders().get("Authorization").get(0));
        String authorization = optionalAuth.orElseThrow().substring(7);

        this.authenticationPort.tokenValidation(authorization);

        return chain.filter(exchange);
    }
}