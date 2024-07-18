package nuts.project.wolesale_system.stock.domain.service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetStockResultDto {
    private String stockId;
    private String stockName;
    private String category;
    private int stockQuantity;
}
