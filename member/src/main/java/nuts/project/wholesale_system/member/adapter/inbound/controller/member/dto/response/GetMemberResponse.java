package nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class GetMemberResponse {

    private String id;
    private String name;
    private String contactNumber;
    private String corporationId;
}
