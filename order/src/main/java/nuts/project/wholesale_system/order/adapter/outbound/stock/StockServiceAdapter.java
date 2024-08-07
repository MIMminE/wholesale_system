package nuts.project.wholesale_system.order.adapter.outbound.stock;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequestType;
import nuts.project.wholesale_system.order.domain.ports.stock.StockResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockServicePort;
import nuts.project.wholesale_system.order.domain.ports.stock.request.StockDeductRequest;
import nuts.project.wholesale_system.order.domain.ports.stock.request.StockReturnRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class StockServiceAdapter implements StockServicePort {

    private final RestTemplate restTemplate;

    @Value("${stock_server_url}")
    private String stockSeverUrl;

    @Override
    public StockResponse deductStock(StockDeductRequest request) {

        try {
            ResponseEntity<Map> result = restTemplate.exchange(stockSeverUrl + "stocks/deduct",
                    HttpMethod.PUT, new HttpEntity<>(request), Map.class);

            Boolean resultBoolean = (Boolean) Objects.requireNonNull(result.getBody()).get("result");

            if (resultBoolean)
                return new StockResponse(StockRequestType.Deduct, true);
            else
                throw new OrderException(OrderException.OrderExceptionCase.OUT_OF_STOCK);

        } catch (ResourceAccessException e) {
            throw new OrderException(OrderException.OrderExceptionCase.STOCK_SERVICE_FAIL);
        }
    }

    @Override
    public StockResponse returnStock(StockReturnRequest request) {
        try {
            ResponseEntity<Map> result = restTemplate.exchange(stockSeverUrl + "stocks/add",
                    HttpMethod.PUT, new HttpEntity<>(request), Map.class);

            Boolean resultBoolean = (Boolean) Objects.requireNonNull(result.getBody()).get("result");
            return new StockResponse(StockRequestType.Return, true);

        } catch (ResourceAccessException e) {
            throw new OrderException(OrderException.OrderExceptionCase.STOCK_SERVICE_FAIL);
        }
    }
}