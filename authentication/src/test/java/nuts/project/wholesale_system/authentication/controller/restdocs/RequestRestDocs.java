package nuts.project.wholesale_system.authentication.controller.restdocs;

import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsHolder;
import nuts.lib.manager.restdocs_manager.domain.annotation.RestDocsSnippet;
import nuts.lib.manager.restdocs_manager.domain.expression.FieldDescription;

@RestDocsHolder(RestDocsHolder.RestDocsHolderType.request)
public abstract class RequestRestDocs {

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "userName", description = "토큰 요청 유저 이름"),
            @FieldDescription(name = "password", description = "토큰 요청 유저 패스워드")
    })
    public Object createToken;


    @RestDocsSnippet(fields = {
            @FieldDescription(name = "userName", description = "토큰 요청 유저 이름"),
            @FieldDescription(name = "firstName", description = "토큰 요청 유저 first name"),
            @FieldDescription(name = "lastName", description = "토큰 요청 유저 last name"),
            @FieldDescription(name = "email", description = "토큰 요청 유저 email"),
            @FieldDescription(name = "password", description = "토큰 요청 유저 패스워드")
    })
    public Object createUsers;

    @RestDocsSnippet(fields = {
            @FieldDescription(name = "userName", description = "토큰 요청 유저 이름")
    })
    public Object deleteUsers;

}
