package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request;

import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsHolder;
import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsSnippet;
import nuts.lib.manager.restdocs_manager.domain.expression.FieldDescription;

@RestDocsHolder(RestDocsHolder.RestDocsHolderType.request)
public abstract class RequestRestDocs {

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "corporationName", description = "생성할 기관 이름"),
            @FieldDescription(name = "representative", description = "생성할 기관 대표자 이름"),
            @FieldDescription(name = "contactNumber", description = "생성할 기관 대표 연락처"),
            @FieldDescription(name = "businessNumber", description = "생성할 기관 사업자 번호"),
            @FieldDescription(name = "grade", description = "초기 등급")
    })
    public Object createCorporationRequest;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "corporationId", description = "변경 타겟 기관 ID"),
            @FieldDescription(name = "corporationName", description = "변경할 기관 이름"),
            @FieldDescription(name = "representative", description = "변경할 기관 대표자 이름"),
            @FieldDescription(name = "contactNumber", description = "변경할 기관 대표 연락처"),
            @FieldDescription(name = "businessNumber", description = "변경할 기관 사업자 번호"),
            @FieldDescription(name = "grade", description = "변경할 기관 등급")
    })
    public Object updateCorporationRequest;

    public Object deleteCorporationRequest;

    public Object searchCorporationRequest;

}
