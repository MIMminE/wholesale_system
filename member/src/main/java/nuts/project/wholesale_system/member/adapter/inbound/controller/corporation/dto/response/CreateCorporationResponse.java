package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CreateCorporationResponse {

    @NotBlank
    private String corporationId;

    @NotBlank
    private String corporationName;

    @NotBlank
    private String representative;

    @NotBlank
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    private String contactNumber;

    @NotBlank
    private String businessNumber;

    @NotBlank
    private String grade;

    public static CreateCorporationResponse fromCorporation(Corporation corporation) {

        return CreateCorporationResponse.builder()
                .corporationId(corporation.getCorporationId())
                .corporationName(corporation.getCorporationName())
                .representative(corporation.getRepresentative())
                .contactNumber(corporation.getContactNumber())
                .businessNumber(corporation.getBusinessNumber())
                .grade(corporation.getGrade().toString())
                .build();
    }
}
