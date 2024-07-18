package nuts.project.wolesale_system.stock.adapter.outbound.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import nuts.project.wolesale_system.stock.domain.model.StockCategory;

@Entity
public class StockEntity {

    @Id
    private String stockId;

    @Column(nullable = false)
    private String stockName;

    @Column(nullable = false)
    private StockCategory category;

    @Column(nullable = false)
    @Min(1)
    private int quantity;
}
