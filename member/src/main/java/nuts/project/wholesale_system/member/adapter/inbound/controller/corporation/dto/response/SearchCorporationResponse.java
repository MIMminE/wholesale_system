package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import nuts.project.wholesale_system.member.domain.model.Corporation;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class SearchCorporationResponse {

    @NotNull
    private int resultCount;

    @NotNull
    private List<GetCorporationResponse> corporationResponses;


    public static SearchCorporationResponse fromCorporations(List<Corporation> corporations) {

        List<GetCorporationResponse> corporationResponses = corporations.stream().map(GetCorporationResponse::fromCorporation).toList();

        return SearchCorporationResponse.builder()
                .resultCount(corporationResponses.size())
                .corporationResponses(corporationResponses)
                .build();
    }

}
