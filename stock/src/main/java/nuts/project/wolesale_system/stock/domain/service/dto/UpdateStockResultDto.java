package nuts.project.wolesale_system.stock.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateStockResultDto {

    private String stockId;
    private String stockName;
    private String category;
    private int beforeQuantity;
    private int afterQuantity;
}
