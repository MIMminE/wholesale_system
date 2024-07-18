package nuts.project.wolesale_system.stock.support;

import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockEntity;
import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockRepository;
import nuts.project.wolesale_system.stock.domain.model.Stock;
import nuts.project.wolesale_system.stock.domain.service.usecase.create.CreateStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.delete.DeleteStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.get.GetStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.update.AddStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.update.DeductStockUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StockUseCaseTestSupport extends FixtureGenerateSupport {

    @Autowired
    protected CreateStockUseCase createStockUseCase;

    @Autowired
    protected DeleteStockUseCase deleteStockUseCase;

    @Autowired
    protected GetStockUseCase getStockUseCase;

    @Autowired
    protected AddStockUseCase addStockUseCase;

    @Autowired
    protected DeductStockUseCase deductStockUseCase;

    @Autowired
    protected StockRepository stockRepository;

    @Override
    protected List<OrderSheet> ordersObject() {

        return List.of(
                OrderSheet.order(orderCustom(Stock.class)
//                                .set("stockId", UUID.randomUUID().toString())
                                .set("stockName", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                                .set("quantity", Arbitraries.integers().between(10, 50))
                        , 3),
                OrderSheet.order(orderCustom(StockEntity.class)
                                .set("stockId", UUID.randomUUID().toString())
                                .set("stockName", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                                .set("quantity", Arbitraries.integers().between(10, 50))
                        , 3)
        );
    }
}
