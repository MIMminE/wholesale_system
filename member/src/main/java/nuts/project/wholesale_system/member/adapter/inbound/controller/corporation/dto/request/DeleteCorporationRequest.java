package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DeleteCorporationRequest {

    @NotBlank
    private String corporationId;
}
