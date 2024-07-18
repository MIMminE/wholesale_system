package nuts.project.wolesale_system.stock.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Stock {

    private String stockId;

    private String stockName;

    private StockCategory category;

    private int quantity;
}
