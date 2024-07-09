package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nuts.project.wholesale_system.member.domain.model.Corporation;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class DeleteCorporationResponse {

    @NotBlank
    private String corporationId;

    @NotBlank
    private String corporationName;

    @NotBlank
    private String representative;

    @NotBlank
    private String businessNumber;

    public static DeleteCorporationResponse fromCorporation(Corporation corporation){
        return DeleteCorporationResponse.builder()
                .corporationId(corporation.getCorporationId())
                .corporationName(corporation.getCorporationName())
                .representative(corporation.getRepresentative())
                .businessNumber(corporation.getBusinessNumber())
                .build();
    }
}
