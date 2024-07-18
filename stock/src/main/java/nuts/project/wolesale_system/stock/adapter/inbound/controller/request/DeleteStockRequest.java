package nuts.project.wolesale_system.stock.adapter.inbound.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeleteStockRequest {

    @NotBlank
    private String stockId;
}
