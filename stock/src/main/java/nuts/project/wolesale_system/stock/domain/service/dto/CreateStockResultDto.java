package nuts.project.wolesale_system.stock.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateStockResultDto {
    private String stockId;
    private String stockName;
    private String category;
}
