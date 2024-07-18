package nuts.project.wholesale_system.order.support;

import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order_item.OrderItemRepository;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.ports.stock.StockServicePort;
import nuts.project.wholesale_system.order.domain.service.usecase.create.CreateOrderUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.delete.DeleteOrderIdUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.get.GetOrderUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.get.GetOrdersUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.update.UpdateOrderStatusUseCase;
import nuts.project.wholesale_system.order.domain.service.usecase.update.UpdateOrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UseCaseTestSupport extends FixtureGenerateSupport {

    @Autowired
    protected CreateOrderUseCase createOrderUseCase;

    @Autowired
    protected DeleteOrderIdUseCase deleteOrderIdUseCase;

    @MockBean
    protected PaymentServicePort paymentService;

    @MockBean
    protected StockServicePort stockService;

    @Autowired
    protected GetOrdersUseCase getOrdersUseCase;

    @Autowired
    protected GetOrderUseCase getOrderUseCase;

    @Autowired
    protected UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @Autowired
    protected UpdateOrderUseCase updateOrderUseCase;

    @Autowired
    protected OrderItemRepository orderItemRepository;

    @Autowired
    protected OrderRepository orderRepository;

    @Override
    protected List<OrderSheet> ordersObject() {

        String orderId = UUID.randomUUID().toString();

        return List.of(
                OrderSheet.order(orderCustom(Order.class)
                                .set("orderId", UUID.randomUUID().toString())
                                .set("userId", UUID.randomUUID().toString())
                                .size("items", 3, 5)
                                .set("items[*].quantity", Arbitraries.integers().between(2, 10))
                        , 3),
                OrderSheet.order(orderCustom(OrderEntity.class)
                                .set("orderId", orderId)
                                .set("userId", UUID.randomUUID().toString())
                                .size("items", 2, 5)
                                .set("items[*].orderEntity.orderId", orderId),
                        2),
                OrderSheet.order(orderCustom(OrderItem.class)
                                .set("productId", UUID.randomUUID().toString())
                                .set("quantity", Arbitraries.integers().between(5, 15)),
                        5)
        );
    }
}
