package nuts.project.wolesale_system.gateway.filter.global;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Order(0)
public class GatewayPassGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("global filter run");
        exchange.getRequest().mutate().header("Gateway-Sign", UUID.randomUUID().toString());

        return chain.filter(exchange).doAfterTerminate(() -> {

            System.out.println("global filter after run");
        });
    }
}
