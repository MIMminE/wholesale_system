package nuts.project.wholesale_system.order.domain.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
    pendPayment("Pending payment status"),
    completeOrder("Order complete Status"),
    prepareProduct("Product readiness status"),
    delivering("During delivery status"),
    deliveringComplete("Delivery complete status"),
    cancelled("Order cancellation");

    private final String description;
}
