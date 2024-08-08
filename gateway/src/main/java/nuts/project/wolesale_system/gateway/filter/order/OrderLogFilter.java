package nuts.project.wolesale_system.gateway.filter.order;

import lombok.RequiredArgsConstructor;
import nuts.project.wolesale_system.gateway.port.log.LogPort;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class OrderLogFilter implements GatewayFilter {

    private final LogPort logAdapter;
    private final String queueName = "orderQueue";

    private void send(String message) {
        logAdapter.send(message, queueName);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String string = exchange.getRequest().getURI().toString();
        System.out.println("OrderLogFilter: " + string);
        send(string);
        return  chain.filter(exchange);
    }
}
