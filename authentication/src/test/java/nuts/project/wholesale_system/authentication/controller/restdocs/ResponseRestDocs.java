package nuts.project.wholesale_system.authentication.controller.restdocs;

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
    public Object createToken;


    @RestDocsSnippet(fields = {
            @FieldDescription(name = "id", description = "유저 Id"),
            @FieldDescription(name = "username", description = "유저 이름"),
            @FieldDescription(name = "email", description = "유저 이메일")
    })
    public Object createUsers;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "count", description = "조회된 유저 개수"),
            @FieldDescription(name = "userInformationList[].id", description = "조회된 유저 ID"),
            @FieldDescription(name = "userInformationList[].username", description = "조회된 유저 이름"),
            @FieldDescription(name = "userInformationList[].email", description = "조회된 유저 Email")
    })
    public Object getUserTable;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "id", description = "유저 Id"),
            @FieldDescription(name = "username", description = "유저 이름"),
            @FieldDescription(name = "email", description = "유저 이메일")
    })
    public Object getUser;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "keys[].kid", description = "kid 값"),
            @FieldDescription(name = "keys[].kty", description = "kty 값"),
            @FieldDescription(name = "keys[].alg", description = "alg 값"),
            @FieldDescription(name = "keys[].use", description = "use 값"),
            @FieldDescription(name = "keys[].n", description = "n 값"),
            @FieldDescription(name = "keys[].e", description = "e 값"),
            @FieldDescription(name = "keys[].x5c[]", description = "x5c 리스트"),
            @FieldDescription(name = "keys[].x5t", description = "x5t 값"),
            @FieldDescription(name = "keys[].x5t#S256", description = "x5ts256 값")

    })
    public Object getJwkSet;

}
