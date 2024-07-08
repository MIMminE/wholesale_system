package nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.response;


import nuts.lib.manager.restdocs_manager.annotation.RestDocsHolder;
import nuts.lib.manager.restdocs_manager.annotation.RestDocsSnippet;
import nuts.lib.manager.restdocs_manager.expression.FieldDescription;
import nuts.lib.manager.restdocs_manager.expression.child.ChildSection;

@RestDocsHolder(RestDocsHolder.RestDocsHolderType.response)
public abstract class ResponseRestDocs {

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "id", description = "조회된 멤버 아이디"),
            @FieldDescription(name = "name", description = "조회된 멤버 이름"),
            @FieldDescription(name = "contactNumber", description = "조회된 멤버 연락처"),
            @FieldDescription(name = "corporationId", description = "조회된 멤버 소속 기관 ID")
    })
    public String getMemberResponse;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "id", description = "생성된 멤버 아이디"),
            @FieldDescription(name = "name", description = "생성된 멤버 이름"),
            @FieldDescription(name = "contactNumber", description = "생성된 멤버 연락처"),
            @FieldDescription(name = "corporationId", description = "소속 기관 ID")
    })
    public String createMemberResponse;


    @RestDocsSnippet(fields = {
            @FieldDescription(name = "id", description = "삭제된 멤버 아이디"),
            @FieldDescription(name = "name", description = "삭제된 멤버 이름"),
            @FieldDescription(name = "contactNumber", description = "삭제된 멤버 연락처"),
            @FieldDescription(name = "corporationId", description = "삭제된 멤버의 소속 기관 ID")
    })
    public String deleteMemberResponse;


    @RestDocsSnippet(fields = {
            @FieldDescription(name = "beforeMember", description = "변경전 멤버 정보"),
            @FieldDescription(name = "beforeMember.id", description = "변경전 멤버 아이디"),
            @FieldDescription(name = "beforeMember.name", description = "변경전 멤버 이름"),
            @FieldDescription(name = "beforeMember.contactNumber", description = "변경전 멤버 번호"),
            @FieldDescription(name = "beforeMember.corporationId", description = "변경전 멤버 소속 기관 ID"),

            @FieldDescription(name = "updatedMember", description = "변경후 멤버 정보"),
            @FieldDescription(name = "updatedMember.id", description = "변경후 멤버 아이디"),
            @FieldDescription(name = "updatedMember.name", description = "변경후 멤버 이름"),
            @FieldDescription(name = "updatedMember.contactNumber", description = "변경후 멤버 번호"),
            @FieldDescription(name = "updatedMember.corporationId", description = "변경후 멤버 소속 기관 ID")
    })
    public String updateMemberResponse;
}
