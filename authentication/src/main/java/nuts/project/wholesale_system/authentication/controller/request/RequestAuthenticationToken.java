package nuts.project.wholesale_system.authentication.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RequestAuthenticationToken {

    @NotBlank
    private final String userName;

    @NotBlank
    private final String password;

}
