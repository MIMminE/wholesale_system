package nuts.project.wholesale_system.order.adapter.outbound.repository.order_item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.domain.model.OrderItem;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "order_items")
@ToString(exclude = "orderEntity")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productId;

    private int quantity;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    public OrderItemEntity(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    static public OrderItem toOrderItem(OrderItemEntity orderItemEntity) {

        String productId = orderItemEntity.getProductId();
        int quantity = orderItemEntity.getQuantity();

        return OrderItem.createOrderItem(productId, quantity);
    }

    static public OrderItemEntity fromOrderItem(OrderItem orderItem) {

        String productId = orderItem.getProductId();
        int quantity = orderItem.getQuantity();

        return new OrderItemEntity(productId, quantity);
    }
}
