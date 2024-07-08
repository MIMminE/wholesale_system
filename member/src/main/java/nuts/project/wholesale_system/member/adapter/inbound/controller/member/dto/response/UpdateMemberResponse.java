package nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMemberResponse {

    private GetMemberResponse beforeMember;
    private GetMemberResponse updatedMember;
}
