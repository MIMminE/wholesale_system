package nuts.project.wholesale_system.authentication.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserInformation {
    @JsonProperty("id")
    private String userId;

    @JsonProperty("username")
    private String userName;
    private String email;
}
