package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UpdateCorporationRequest {

    @NotBlank
    private String corporationId;
    private String corporationName;
    private String representative;

    private String contactNumber;
    private String businessNumber;
    private String grade;
}
