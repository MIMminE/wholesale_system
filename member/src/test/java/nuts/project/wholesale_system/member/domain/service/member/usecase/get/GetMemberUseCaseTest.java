package nuts.project.wholesale_system.member.domain.service.member.usecase.get;

import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberEntity;
import nuts.project.wholesale_system.member.domain.model.Member;
import nuts.project.wholesale_system.member.support.MemberUseCaseTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GetMemberUseCaseTest extends MemberUseCaseTestSupport {

    @DisplayName("[ Member UseCase ] - getMember Success")
    @Test
    void getMemberUseCaseSuccess() {
        // given
        MemberEntity memberEntity = memberRepository.save(getOrderedObject(MemberEntity.class).get(0));
        System.out.println(memberEntity);
        String memberId = memberEntity.getMemberId();
        String name = memberEntity.getName();
        String contactNumber = memberEntity.getContactNumber();
        // when
        Member result = getMemberUseCase.execute(memberId);

        // then
        Assertions.assertThat(result)
                .extracting("name", "contactNumber")
                .contains(name, contactNumber);
    }
}