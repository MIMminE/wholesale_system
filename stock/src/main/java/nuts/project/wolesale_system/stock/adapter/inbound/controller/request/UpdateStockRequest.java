package nuts.project.wolesale_system.stock.adapter.inbound.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateStockRequest {

    @NotBlank
    private String stockId;

    @Min(1)
    private int quantity;

}
