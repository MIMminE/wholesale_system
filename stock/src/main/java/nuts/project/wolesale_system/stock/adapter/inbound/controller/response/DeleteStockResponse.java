package nuts.project.wolesale_system.stock.adapter.inbound.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeleteStockResponse {
    private String requestStockId;
    private boolean result;
}
