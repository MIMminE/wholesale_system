package nuts.project.wholesale_system.order.adapter.outbound.repository.order;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order_item.OrderItemEntity;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "orders")
@ToString
public class OrderEntity {

    @Id
    private String orderId;

    private String userId;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public OrderEntity(String orderId, String customerId, OrderStatus orderStatus) {
        this();
        this.orderId = orderId;
        this.userId = customerId;
        this.orderStatus = orderStatus;
    }

    public OrderEntity() {
        this.createdAt = LocalDateTime.now();
    }

    public void addItem(OrderItemEntity item) {
        items.add(item);
        item.setOrderEntity(this);
    }

    public void deleteItem(OrderItemEntity item) {
        items.remove(item);
        item.setOrderEntity(null);
    }

    static public OrderEntity fromOrder(Order order) {
        String orderId = order.getOrderId();
        String userId = order.getUserId();
        OrderStatus orderStatus = order.getOrderStatus();

        List<OrderItemEntity> itemEntities = order.getItems().stream()
                .map(OrderItemEntity::fromOrderItem).toList();

        OrderEntity entity = new OrderEntity(orderId, userId, orderStatus);

        for (OrderItemEntity itemEntity : itemEntities) {

            entity.addItem(itemEntity);
        }

        return entity;
    }

    static public Order toOrder(OrderEntity orderEntity) {
        String orderId = orderEntity.getOrderId();
        String customerId = orderEntity.getUserId();
        OrderStatus orderStatus = orderEntity.getOrderStatus();
        List<OrderItem> orderItems = orderEntity.getItems().stream()
                .map(OrderItemEntity::toOrderItem).toList();

        return new Order(orderId, customerId, orderItems, orderStatus);
    }


}
