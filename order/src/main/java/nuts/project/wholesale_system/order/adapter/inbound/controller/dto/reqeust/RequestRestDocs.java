package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust;

import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsHolder;
import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsSnippet;
import nuts.lib.manager.restdocs_manager.domain.expression.FieldDescription;

@RestDocsHolder(RestDocsHolder.RestDocsHolderType.request)
public abstract class RequestRestDocs {

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "orderItems", description = "주문 상품 리스트"),
            @FieldDescription(name = "orderItems[].productId", description = "주문 상품 ID"),
            @FieldDescription(name = "orderItems[].quantity", description = "주문 상품 개수"),
    })
    public Object createOrderRequest;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "orderId", description = "삭제할 주문 ID"),
    })
    public Object deleteOrderRequest;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "orderId", description = "업데이트할 주문 ID"),
            @FieldDescription(name = "orderStatus", description = "업데이트할 주문 상태")
    })
    public Object updateOrderStatusRequest;
}
