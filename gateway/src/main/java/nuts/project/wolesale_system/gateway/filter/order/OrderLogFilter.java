package nuts.project.wolesale_system.gateway.filter.order;

import lombok.RequiredArgsConstructor;
import nuts.project.wolesale_system.gateway.port.log.LogPort;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

import static nuts.project.wolesale_system.gateway.config.RabbitConfig.EXCHANGE_NAME;
import static nuts.project.wolesale_system.gateway.config.RabbitConfig.ORDER_ROUTING_KEY;

@RequiredArgsConstructor
@Component
public class OrderLogFilter implements GatewayFilter {

    private final LogPort logAdapter;
    private final String exchange = EXCHANGE_NAME;
    private final String routingKey = ORDER_ROUTING_KEY;

    private void send(Map<String, String> message) {
        logAdapter.send(routingKey, exchange, message);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String uri = exchange.getRequest().getURI().toString();
        String method = exchange.getRequest().getMethod().name();
        String requestId = exchange.getRequest().getId();

        Map<String, String> message = Map.of(
                "requestId", requestId,
                "uri", uri,
                "method", method,
                "logType", "request"
        );
        send(message);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpStatusCode statusCode = exchange.getResponse().getStatusCode();
            Map<String, String> responseMessage = Map.of("requestId", requestId, "statusCode", statusCode.toString());
            send(responseMessage);
        }));
    }
}
