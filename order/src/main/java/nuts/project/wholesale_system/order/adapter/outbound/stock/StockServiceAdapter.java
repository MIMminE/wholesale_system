package nuts.project.wholesale_system.order.adapter.outbound.stock;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.exception.StockException;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.ports.stock.StockResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockServicePort;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static nuts.project.wholesale_system.order.domain.exception.StockException.StockExceptionCase.*;

@Component
@RequiredArgsConstructor
public class StockServiceAdapter implements StockServicePort {

    private final RestTemplate restTemplate;

    @Value("${stock_server_url}")
    private String stockSeverUrl;

    @Override
    public Optional<StockResponse> deductStock(StockRequest request) {

        List<OrderItem> requestItems = request.getRequestItems();
        for (OrderItem requestItem : requestItems) {

            String productId = requestItem.getProductId();
            int quantity = requestItem.getQuantity();
            Map<String, ? extends Serializable> requestObject = Map.of("stockId", productId,
                    "quantity", quantity);

            ResponseEntity<Map> exchange = restTemplate.exchange(stockSeverUrl + "stocks/deduct",
                    HttpMethod.PUT,
                    new HttpEntity<>(requestObject), Map.class);


        }


        try {
            //TODO
            System.out.println("stock deduct");
            return Optional.empty();
        } catch (RuntimeException e) {
            throw new StockException(STOCK_SERVICE_FAIL);
        }
    }

    @Override
    public Optional<StockResponse> returnStock(StockRequest request) {
        try {
            //TODO
            System.out.println("stock return");
            return Optional.empty();
        } catch (RuntimeException e) {
            throw new StockException(STOCK_SERVICE_FAIL);
        }
    }

    @Override
    public Optional<StockResponse> updateStock(StockRequest request) throws StockException {
        return Optional.empty();
    }
}
