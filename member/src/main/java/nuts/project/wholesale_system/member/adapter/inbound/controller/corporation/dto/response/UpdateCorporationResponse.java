package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.update.UpdateCorporationResultSet;

import static nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.response.GetCorporationResponse.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UpdateCorporationResponse {

    @NotNull
    GetCorporationResponse beforeCorporation;

    @NotNull
    GetCorporationResponse afterCorporation;

    public static UpdateCorporationResponse fromUpdateCorporationResultSet(UpdateCorporationResultSet resultSet) {

        return UpdateCorporationResponse.builder()
                .beforeCorporation(fromCorporation(resultSet.getBeforeCorporation()))
                .afterCorporation(fromCorporation(resultSet.getAfterCorporation()))
                .build();
    }
}
