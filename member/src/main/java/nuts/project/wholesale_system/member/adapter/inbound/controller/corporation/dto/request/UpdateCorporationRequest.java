package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@Builder
public class UpdateCorporationRequest {

    @NotBlank
    private String corporationId;
    private String corporationName;
    private String representative;

    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    private String contactNumber;
    private String businessNumber;
    private String grade;
}
