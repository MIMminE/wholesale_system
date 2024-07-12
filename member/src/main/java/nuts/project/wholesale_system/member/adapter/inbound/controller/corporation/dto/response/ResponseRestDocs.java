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
            @FieldDescription(name = "beforeCorporation.corporationId", description = "변경전 기관 ID"),
            @FieldDescription(name = "beforeCorporation.corporationName", description = "변경전 기관 이름"),
            @FieldDescription(name = "beforeCorporation.representative", description = "변경전 기관 대표자 이름"),
            @FieldDescription(name = "beforeCorporation.contactNumber", description = "변경전 기관 대표 연락처"),
            @FieldDescription(name = "beforeCorporation.businessNumber", description = "변경전 기관 사업자 번호"),
            @FieldDescription(name = "beforeCorporation.grade", description = "변경전 기관 등급"),
            @FieldDescription(name = "afterCorporation", description = "변경후 기관 정보"),
            @FieldDescription(name = "afterCorporation.corporationId", description = "변경후 기관 ID"),
            @FieldDescription(name = "afterCorporation.corporationName", description = "변경후 기관 이름"),
            @FieldDescription(name = "afterCorporation.representative", description = "변경후 기관 대표자 이름"),
            @FieldDescription(name = "afterCorporation.contactNumber", description = "변경후 기관 대표 연락처"),
            @FieldDescription(name = "afterCorporation.businessNumber", description = "변경후 기관 사업자 번호"),
            @FieldDescription(name = "afterCorporation.grade", description = "변경후 기관 등급"),
    })
    public Object updateCorporationResponse;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "corporationId", description = "조회된 기관에 부여된 ID"),
            @FieldDescription(name = "corporationName", description = "조회된 기관 이름"),
            @FieldDescription(name = "representative", description = "조회된 기관 대표자 이름"),
            @FieldDescription(name = "contactNumber", description = "조회된 기관 대표 연락처"),
            @FieldDescription(name = "businessNumber", description = "조회된 기관 사업자 번호"),
            @FieldDescription(name = "grade", description = "조회된 기관 등급")
    })
    public Object getCorporationResponse;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "corporationId", description = "삭제된 기관에 부여된 ID"),
            @FieldDescription(name = "corporationName", description = "삭제된 기관 이름"),
            @FieldDescription(name = "representative", description = "삭제된 기관 대표자 이름"),
            @FieldDescription(name = "businessNumber", description = "삭제된 기관 사업자 번호"),
    })
    public Object deleteCorporationResponse;

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
