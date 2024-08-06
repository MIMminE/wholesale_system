package nuts.project.wholesale_system.order.adapter.outbound.stock;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.ports.stock.StockResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockServicePort;
import nuts.project.wholesale_system.order.domain.ports.stock.request.RequestItem;
import nuts.project.wholesale_system.order.domain.ports.stock.request.StockDeductRequest;
import nuts.project.wholesale_system.order.domain.ports.stock.request.StockReturnRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class StockServiceAdapter implements StockServicePort {

    private final RestTemplate restTemplate;

    @Value("${stock_server_url}")
    private String stockSeverUrl;

    @Override
    public StockResponse deductStock(StockDeductRequest request) {
        List<RequestItem> items = request.getItems();
        for (RequestItem requestItem : items) {

            String productId = requestItem.getProductId();
            int quantity = requestItem.getQuantity();
            Map<String, ? extends Serializable> requestObject = Map.of("stockId", productId,
                    "quantity", quantity);

            ResponseEntity<Map> exchange = restTemplate.exchange(stockSeverUrl + "stocks/deduct",
                    HttpMethod.PUT,
                    new HttpEntity<>(requestObject), Map.class);
        }


        //TODO
        System.out.println("stock deduct");
        return null;
    }

    @Override
    public StockResponse returnStock(StockReturnRequest request) {

        return null;
    }
}