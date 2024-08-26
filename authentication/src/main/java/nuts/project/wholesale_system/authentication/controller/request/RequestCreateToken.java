package nuts.project.wholesale_system.authentication.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RequestCreateToken {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

}
