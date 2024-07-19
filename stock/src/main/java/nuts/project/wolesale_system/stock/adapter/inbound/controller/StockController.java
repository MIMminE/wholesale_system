package nuts.project.wolesale_system.stock.adapter.inbound.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.CreateStockRequest;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.DeleteStockRequest;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.UpdateStockRequest;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.response.CreateStockResponse;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.response.DeleteStockResponse;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.response.GetStockResponse;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.response.UpdateStockResponse;
import nuts.project.wolesale_system.stock.domain.service.StockService;
import nuts.project.wolesale_system.stock.domain.service.dto.CreateStockResultDto;
import nuts.project.wolesale_system.stock.domain.service.dto.GetStockResultDto;
import nuts.project.wolesale_system.stock.domain.service.dto.UpdateStockResultDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;


    @GetMapping("/stocks/{stockId}")
    public ResponseEntity<GetStockResponse> getStock(@PathVariable("stockId") String stockId) {
        GetStockResultDto getResult = stockService.getStock(stockId);

        return ResponseEntity.ok()
                .body(new GetStockResponse(getResult.getStockId(), getResult.getStockName(),
                        getResult.getCategory(), getResult.getQuantity()));

    }

    @PostMapping("/stocks")
    public ResponseEntity<CreateStockResponse> createStock(@RequestBody @Valid CreateStockRequest request) {

        CreateStockResultDto createResult = stockService.createStock(request.getStockName(), request.getCategory());

        return ResponseEntity.ok()
                .body(new CreateStockResponse(createResult.getStockId(),
                        createResult.getStockName(), createResult.getCategory()));
    }

    @PutMapping("/stocks/add")
    public ResponseEntity<UpdateStockResponse> addStock(@RequestBody @Valid UpdateStockRequest request) {
        UpdateStockResultDto updateResult = stockService.addStock(request.getStockId(), request.getQuantity());

        return ResponseEntity.ok()
                .body(new UpdateStockResponse(updateResult.getStockId(), updateResult.getStockName(), updateResult.getCategory(),
                        updateResult.getBeforeQuantity(), updateResult.getAfterQuantity()));
    }

    @PutMapping("/stocks/deduct")
    public ResponseEntity<UpdateStockResponse> deductStock(@RequestBody @Valid UpdateStockRequest request) {
        UpdateStockResultDto updateResult = stockService.deductStock(request.getStockId(), request.getQuantity());

        return ResponseEntity.ok()
                .body(new UpdateStockResponse(updateResult.getStockId(), updateResult.getStockName(), updateResult.getCategory(),
                        updateResult.getBeforeQuantity(), updateResult.getAfterQuantity()));
    }

    @DeleteMapping("/stocks")
    public ResponseEntity<DeleteStockResponse> deleteStock(@RequestBody @Valid DeleteStockRequest request) {
        stockService.deleteStock(request.getStockId());

        return ResponseEntity.ok().body(new DeleteStockResponse(request.getStockId(), true));
    }
}
