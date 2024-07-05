package nuts.project.wholesale_system.member.domain.service.member.usecase.delete;

import nuts.project.wholesale_system.member.domain.model.Member;

public interface DeleteMemberUseCase {
    Member execute(String memberId);
}
