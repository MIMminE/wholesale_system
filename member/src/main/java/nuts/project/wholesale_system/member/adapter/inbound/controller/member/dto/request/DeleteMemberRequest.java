package nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMemberRequest {

    @NotBlank
    private String id;

}
