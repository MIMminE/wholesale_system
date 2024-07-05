package nuts.project.wholesale_system.member.domain.service.member.usecase.get;


import nuts.project.wholesale_system.member.domain.model.Member;

public interface GetMemberUseCase {

    Member executor(String id, String password);
}
