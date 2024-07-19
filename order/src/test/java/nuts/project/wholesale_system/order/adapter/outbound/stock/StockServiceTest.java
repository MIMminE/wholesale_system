package nuts.project.wholesale_system.order.adapter.outbound.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StockServiceTest {


    RestTemplate restTemplate = new RestTemplate();
//
//    @DisplayName("")
//    @Test
//    void test() {
//        // given
//        String url = "http://localhost:9811/stocks/sdasda";
//
//        Map forObject = restTemplate.getForObject(url, Map.class);
//        // when
//
//
//        // then
//    }

    @Test
    void postCreate() {
        // given
        String url = "http://localhost:9811/stocks";

        Map<String, String> requestJsonMap = Map.of("stockName", "test_stock",
                "category", "Fisheries");

        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(url, requestJsonMap, Map.class);

        // when
        System.out.println(mapResponseEntity);
        // then
    }
}