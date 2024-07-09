package nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request;


import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsHolder;
import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsSnippet;
import nuts.lib.manager.restdocs_manager.domain.expression.FieldDescription;

@RestDocsHolder(RestDocsHolder.RestDocsHolderType.request)
public abstract class RequestRestDocs {

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "name", description = "멤버 이름"),
            @FieldDescription(name = "id", description = "요청 아이디"),
            @FieldDescription(name = "password", description = "요청 패스워드"),
            @FieldDescription(name = "contactNumber", description = "멤버 연락처"),
            @FieldDescription(name = "corporationId", description = "소속 기관 ID")
    })
    public String createMember;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "id", description = "삭제할 멤버 ID"),
    })
    public String deleteMember;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "requestId", description = "요청 아이디"),
            @FieldDescription(name = "newName", description = "새로운 멤버 이름"),
            @FieldDescription(name = "newPassword", description = "새로운 패스워드"),
            @FieldDescription(name = "newContactNumber", description = "새로운 연락처"),
            @FieldDescription(name = "newCorporationId", description = "새로운 소속 기관 ID")
    })
    public String updateMember;

}
