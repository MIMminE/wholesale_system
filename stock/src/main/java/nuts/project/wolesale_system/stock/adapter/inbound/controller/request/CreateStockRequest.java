package nuts.project.wolesale_system.stock.adapter.inbound.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateStockRequest {

    @NotBlank
    private String stockName;

    @NotBlank
    private String category;
}
