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
            @FieldDescription(name = "corporationId", description = "삭제된 기관에 부여된 ID"),
            @FieldDescription(name = "corporationName", description = "삭제된 기관 이름"),
            @FieldDescription(name = "representative", description = "삭제된 기관 대표자 이름"),
            @FieldDescription(name = "businessNumber", description = "삭제된 기관 사업자 번호"),
    })
    public Object getOrdersResponse;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "resultCount", description = "조회된 기관 개수"),
            @FieldDescription(name = "corporationResponses", description = "조회된 기관 정보 목록"),
            @FieldDescription(name = "corporationResponses[].corporationId", description = "조회된 기관 ID"),
            @FieldDescription(name = "corporationResponses[].corporationName", description = "조회된 기관 이름"),
            @FieldDescription(name = "corporationResponses[].representative", description = "조회된 기관 대표자 이름"),
            @FieldDescription(name = "corporationResponses[].contactNumber", description = "조회된 기관 대표 연락처"),
            @FieldDescription(name = "corporationResponses[].businessNumber", description = "조회된 기관 사업자 번호"),
            @FieldDescription(name = "corporationResponses[].grade", description = "조회된 기관 등급")
    })
    public Object searchCorporationResponse;
}
