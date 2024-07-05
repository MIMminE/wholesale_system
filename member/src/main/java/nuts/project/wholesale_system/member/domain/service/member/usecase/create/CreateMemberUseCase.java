package nuts.project.wholesale_system.member.domain.service.member.usecase.create;

import nuts.project.wholesale_system.member.domain.model.Member;

public interface CreateMemberUseCase {

    Member execute(String name, String id, String password, String contactNumber, String CorporationId);
}
