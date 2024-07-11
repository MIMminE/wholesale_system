package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.response;

import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsHolder;
import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsSnippet;
import nuts.lib.manager.restdocs_manager.domain.expression.FieldDescription;

@RestDocsHolder(RestDocsHolder.RestDocsHolderType.response)
public abstract class ResponseRestDocs {

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "corporationId", description = "생성된 기관에 부여된 ID"),
            @FieldDescription(name = "corporationName", description = "생성된 기관 이름"),
            @FieldDescription(name = "representative", description = "생성된 기관 대표자 이름"),
            @FieldDescription(name = "contactNumber", description = "생성된 기관 대표 연락처"),
            @FieldDescription(name = "businessNumber", description = "생성된 기관 사업자 번호"),
            @FieldDescription(name = "grade", description = "생성된 기관 등급")
    })
    public Object createCorporationResponse;


    @RestDocsSnippet(fields = {
            @FieldDescription(name = "beforeCorporation", description = "변경전 기관 정보"),


            @FieldDescription(name = "afterCorporation", description = "변경후 기관 정보"),




            @FieldDescription(name = "representative", description = "생성된 기관 대표자 이름"),
            @FieldDescription(name = "contactNumber", description = "생성된 기관 대표 연락처"),
            @FieldDescription(name = "businessNumber", description = "생성된 기관 사업자 번호"),
            @FieldDescription(name = "grade", description = "생성된 기관 등급")
    })
    public Object updateCorporationResponse;
}
