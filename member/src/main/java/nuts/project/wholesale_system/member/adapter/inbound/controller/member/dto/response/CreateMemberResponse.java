package nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class CreateMemberResponse {

    private String id;
    private String name;
    private String corporationId;
}
