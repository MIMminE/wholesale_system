package nuts.project.wolesale_system.stock.adapter.inbound.controller.restdocs;


import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsHolder;
import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsSnippet;
import nuts.lib.manager.restdocs_manager.domain.expression.FieldDescription;

@RestDocsHolder(RestDocsHolder.RestDocsHolderType.request)
public abstract class RequestRestDocs {

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "stockName", description = "생성 요청 재고 이름"),
            @FieldDescription(name = "category", description = "생성 재고 카테고리")
    })
    public String createStock;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "stockId", description = "수량을 증가시킬 재고 ID"),
            @FieldDescription(name = "quantity", description = "증가시킬 수량")
    })
    public String addStock;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "stockId", description = "수량을 감소시킬 재고 ID"),
            @FieldDescription(name = "quantity", description = "감소시킬 수량")
    })
    public String deductStock;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "stockId", description = "삭제할 재고 ID")
    })
    public String deleteStock;

}
