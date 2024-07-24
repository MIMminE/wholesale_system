package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nuts.project.wholesale_system.member.domain.model.Corporation;

import java.io.Serializable;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetCorporationResponse implements Serializable {
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

    public static GetCorporationResponse fromCorporation(Corporation corporation) {

        return GetCorporationResponse.builder()
                .corporationId(corporation.getCorporationId())
                .corporationName(corporation.getCorporationName())
                .representative(corporation.getRepresentative())
                .contactNumber(corporation.getContactNumber())
                .businessNumber(corporation.getBusinessNumber())
                .grade(corporation.getGrade().toString())
                .build();
    }
}
