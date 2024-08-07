package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.response;

import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsHolder;
import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsSnippet;
import nuts.lib.manager.restdocs_manager.domain.expression.FieldDescription;

@RestDocsHolder(RestDocsHolder.RestDocsHolderType.response)
public abstract class ResponseRestDocs {

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "orderId", description = "생성된 주문 ID"),
            @FieldDescription(name = "userId", description = "주문한 유저 ID"),
            @FieldDescription(name = "payment.userId", description = "결제 정보에 포함된 유저 ID"),
            @FieldDescription(name = "payment.orderId", description = "결제 정보에 포함된 주문 ID"),
            @FieldDescription(name = "payment.accountNumber", description = "결제 계좌 번호"),
            @FieldDescription(name = "orderItems", description = "주문 상품 리스트"),
            @FieldDescription(name = "orderItems[].productId", description = "주문 상품 ID"),
            @FieldDescription(name = "orderItems[].quantity", description = "주문 상품 개수")
    })
    public Object createOrderResponse;


    @RestDocsSnippet(fields = {
            @FieldDescription(name = "deleteOrderId", description = "삭제 주문 ID"),
            @FieldDescription(name = "deleteOrderItems", description = "삭제된 주문 상품 리스트"),
            @FieldDescription(name = "deleteOrderItems[].productId", description = "삭제된 상품 ID"),
            @FieldDescription(name = "deleteOrderItems[].quantity", description = "삭제된 상품 개수")
    })
    public Object deleteOrderResponse;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "orderId", description = "조회된 주문 ID"),
            @FieldDescription(name = "userId", description = "주문한 유저 ID"),
            @FieldDescription(name = "payment.userId", description = "결제 정보에 포함된 유저 ID"),
            @FieldDescription(name = "payment.orderId", description = "결제 정보에 포함된 주문 ID"),
            @FieldDescription(name = "payment.accountNumber", description = "결제 계좌 번호"),
            @FieldDescription(name = "orderItems", description = "주문 상품 리스트"),
            @FieldDescription(name = "orderItems[].productId", description = "주문 상품 ID"),
            @FieldDescription(name = "orderItems[].quantity", description = "주문 상품 개수")
    })
    public Object getOrderResponse;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "count", description = "조회된 주문 개수"),
            @FieldDescription(name = "orderResponses", description = "주문 리스트"),
            @FieldDescription(name = "orderResponses[].orderId", description = "주문 ID"),
            @FieldDescription(name = "orderResponses[].userId", description = "유저 ID"),
            @FieldDescription(name = "orderResponses[].payment.userId", description = "결제 정보에 포함된 유저 ID"),
            @FieldDescription(name = "orderResponses[].payment.orderId", description = "결제 정보에 포함된 주문 ID"),
            @FieldDescription(name = "orderResponses[].payment.accountNumber", description = "결제 계좌 번호"),
            @FieldDescription(name = "orderResponses[].orderItems", description = "주문 상품 리스트"),
            @FieldDescription(name = "orderResponses[].orderItems[].productId", description = "주문 상품 ID"),
            @FieldDescription(name = "orderResponses[].orderItems[].quantity", description = "주문 상품 개수")
    })
    public Object getOrdersResponse;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "orderId", description = "주문 ID"),
            @FieldDescription(name = "beforeStatus", description = "변경 전 상태"),
            @FieldDescription(name = "afterStatus", description = "변경 후 상태")
    })
    public Object updateOrderStatusResponse;
}
