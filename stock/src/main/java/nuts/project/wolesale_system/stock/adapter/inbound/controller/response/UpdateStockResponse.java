package nuts.project.wolesale_system.stock.adapter.inbound.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateStockResponse {
    private String stockId;

    private String stockName;

    private String category;

    private int beforeQuantity;

    private int afterQuantity;

}
