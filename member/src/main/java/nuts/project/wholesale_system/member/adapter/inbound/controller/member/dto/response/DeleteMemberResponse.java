package nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DeleteMemberResponse {
    @Size(min = 5)
    private String id;

    @Size(min = 2)
    private String name;

    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    private String contactNumber;

    @NotBlank
    private String corporationId;
}

