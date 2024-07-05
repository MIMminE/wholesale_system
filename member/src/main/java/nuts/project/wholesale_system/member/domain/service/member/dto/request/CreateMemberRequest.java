package nuts.project.wholesale_system.member.domain.service.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CreateMemberRequest {

    @Size(min = 2)
    private String name;

    @NotBlank
    @Size(min = 5, max = 15)
    private String id;

    @NotBlank
    @Size(min = 5, max = 15)
    private String password;

    @NotBlank
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    private String contactNumber;

    @NotBlank
    private String corporationId;
}
