package nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberRequest {

    @Size(min = 5)
    private String requestId;

    @Size(min = 2)
    private String newName;

    @Size(min = 5)
    private String newPassword;

    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    private String newContactNumber;

    @NotBlank
    private String newCorporationId;
}
