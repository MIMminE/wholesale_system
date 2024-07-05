package nuts.project.wholesale_system.member.domain.service.member.usecase.delete;

import nuts.project.wholesale_system.member.SpringTestSupport;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class DeleteMemberUseCaseTest extends SpringTestSupport {

    @DisplayName("deleteMemberUseCase 동작 성공 테스트")
    @Test
    void successDeleteMemberUseCase() {

        // given
        MemberEntity memberEntity = getOrderedObject(MemberEntity.class).get(0);
        memberRepository.save(memberEntity);

        // when
        deleteMemberUseCase.execute(memberEntity.getMemberId());

        // then
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            memberRepository.findById(memberEntity.getMemberId()).orElseThrow();
        });
    }

}