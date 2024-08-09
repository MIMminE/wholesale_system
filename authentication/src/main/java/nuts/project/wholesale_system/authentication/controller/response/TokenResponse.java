package nuts.project.wholesale_system.authentication.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class TokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_expires_in")
    private int refreshExpiresIn;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("not_before_policy")
    private int notBeforePolicy;

    private String scope;
}
