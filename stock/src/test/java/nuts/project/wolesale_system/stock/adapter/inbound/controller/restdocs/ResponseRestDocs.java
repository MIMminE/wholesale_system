package nuts.project.wolesale_system.stock.adapter.inbound.controller.restdocs;


import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsHolder;
import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsSnippet;
import nuts.lib.manager.restdocs_manager.domain.expression.FieldDescription;

@RestDocsHolder(RestDocsHolder.RestDocsHolderType.response)
public abstract class ResponseRestDocs {

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "stockId", description = "조회 재고 ID"),
            @FieldDescription(name = "stockName", description = "조회 재고 이름"),
            @FieldDescription(name = "category", description = "조회 재고 카테고리"),
            @FieldDescription(name = "quantity", description = "조회 재고 수량")
    })
    public String getStock;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "stockId", description = "생성된 재고 ID"),
            @FieldDescription(name = "stockName", description = "생성된 재고 이름"),
            @FieldDescription(name = "category", description = "생성된 재고 카테고리")
    })
    public String createStock;


    @RestDocsSnippet(fields = {
            @FieldDescription(name = "stockId", description = "수량을 증가시킨 재고 ID"),
            @FieldDescription(name = "stockName", description = "수량을 증가시킨 재고 이름"),
            @FieldDescription(name = "category", description = "수량을 증가시킨 재고 카테고리"),
            @FieldDescription(name = "beforeQuantity", description = "수량을 증가시키기 전 재고 수량"),
            @FieldDescription(name = "afterQuantity", description = "수량을 증가시킨 후 재고 수량")
    })
    public String addStock;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "stockId", description = "수량을 감소시킨 재고 ID"),
            @FieldDescription(name = "stockName", description = "수량을 감소시킨 재고 이름"),
            @FieldDescription(name = "category", description = "수량을 감소시킨 재고 카테고리"),
            @FieldDescription(name = "beforeQuantity", description = "수량을 감소시키기 전 재고 수량"),
            @FieldDescription(name = "afterQuantity", description = "수량을 감소시킨 후 재고 수량")
    })
    public String deductStock;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "requestStockId", description = "삭제 요청 재고 ID"),
            @FieldDescription(name = "result", description = "삭제 결과")

    })
    public String deleteStock;
}
