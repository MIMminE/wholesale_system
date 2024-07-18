package nuts.project.wolesale_system.stock.adapter.outbound.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import nuts.project.wolesale_system.stock.domain.model.StockCategory;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class StockEntity {

    @Id
    private String stockId;

    @Column(nullable = false)
    private String stockName;

    @Column(nullable = false)
    private StockCategory category;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    public StockEntity() {
        this.createAt = LocalDateTime.now();
    }

    public StockEntity(String stockId, String stockName, StockCategory category, int quantity) {
        this();
        this.stockId = stockId;
        this.stockName = stockName;
        this.category = category;
        this.quantity = quantity;
    }
}
