package nuts.project.wholesale_system.member.domain.service.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateMemberRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String contactNumber;

    @NotBlank
    private String corporationId;
}
