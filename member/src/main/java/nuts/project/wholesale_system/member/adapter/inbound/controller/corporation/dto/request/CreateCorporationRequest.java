package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import nuts.project.wholesale_system.member.domain.model.Grade;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CreateCorporationRequest {

    @NotBlank
    String corporationName;

    @NotBlank
    String representative;

    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    String contactNumber;

    @NotBlank
    String businessNumber;

    @NotBlank
    String grade;
}
