package nuts.project.wholesale_system.authentication.controller.response;

import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsHolder;
import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsSnippet;
import nuts.lib.manager.restdocs_manager.domain.expression.FieldDescription;

@RestDocsHolder(RestDocsHolder.RestDocsHolderType.response)
public abstract class ResponseRestDocs {

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "access_token", description = "엑세스 토큰"),
            @FieldDescription(name = "expires_in", description = "만료시간"),
            @FieldDescription(name = "refresh_expires_in", description = "갱신가능 시간"),
            @FieldDescription(name = "token_type", description = "토큰 타입"),
            @FieldDescription(name = "not_before_policy", description = "이전 정책"),
            @FieldDescription(name = "scope", description = "스코프")
    })
    public Object tokenResponse;


    @RestDocsSnippet(fields = {
            @FieldDescription(name = "id", description = "유저 Id"),
            @FieldDescription(name = "username", description = "유저 이름"),
            @FieldDescription(name = "email", description = "유저 이메일")
    })
    public Object responseCreateUsers;
}
