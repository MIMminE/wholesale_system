package nuts.project.wholesale_system.member.domain.service.member;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.domain.service.member.dto.request.CreateMemberRequest;
import nuts.project.wholesale_system.member.domain.service.member.usecase.create.CreateMemberUseCase;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final CreateMemberUseCase createMemberUseCase;

    public void createMember(CreateMemberRequest request){
        String name = request.getName();
        String contactNumber = request.getContactNumber();
        String corporationId = request.getCorporationId();


    }

}
