package nuts.project.wholesale_system.member.domain.service.corporation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import nuts.project.wholesale_system.member.domain.model.Grade;

@ToString
@Getter
@AllArgsConstructor
public class CreateCorporationRequest {

    @NotBlank
    private String corporationName;

    @NotBlank
    private String representative;

    @NotBlank
    private String contactNumber;

    @NotBlank
    private String businessNumber;

    @NotNull
    private Grade grade;
}
