package nuts.project.wholesale_system.member.domain.service.member.usecase.update;

import nuts.project.wholesale_system.member.domain.model.Member;

public interface UpdateMemberUseCase {

    Member execute(String targetMemberId, Member updateMember);
}
