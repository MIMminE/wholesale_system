package nuts.project.wholesale_system.order.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class OrderResponse {
    private ResponseType responseType;
    private String userId;
    private LocalDateTime processAt;
    private Map<String, Object> orderInformation = new HashMap<>();

    @Builder
    private OrderResponse(String userId, LocalDateTime processAt, ResponseType responseType) {
        this.userId = userId;
        this.responseType = responseType;
        this.processAt = processAt;
    }

    public void addOrderInformation(String key, Object value) {
        this.orderInformation.put(key, value);
    }
}

