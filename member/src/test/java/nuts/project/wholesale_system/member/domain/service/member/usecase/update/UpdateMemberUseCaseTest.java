package nuts.project.wholesale_system.member.domain.service.member.usecase.update;

import nuts.project.wholesale_system.member.support.MemberUseCaseTestSupport;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberEntity;
import nuts.project.wholesale_system.member.domain.model.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class UpdateMemberUseCaseTest extends MemberUseCaseTestSupport {

    @DisplayName("updateMemberUseCase 동작 성공 테스트")
    @Test
    void successUpdateMemberUseCase() {

        // given
        CorporationEntity corporationEntity = getOrderedObject(CorporationEntity.class).get(0);
        corporationRepository.save(corporationEntity);

        MemberEntity beforeEntity = getOrderedObject(MemberEntity.class).get(0);
        beforeEntity.setCorporationEntity(corporationEntity);

        memberRepository.save(beforeEntity);

        Member updateMember = getOrderedObject(Member.class).get(0);
        updateMember.setCorporationId(corporationEntity.getCorporationId());

        // when
        updateMemberUseCase.execute(beforeEntity.getMemberId(), updateMember);

        // then
        MemberEntity afterEntity = memberRepository.findById(beforeEntity.getMemberId()).orElseThrow();

        Assertions.assertThat(afterEntity).extracting("name", "id","contactNumber")
                .contains(updateMember.getName(), updateMember.getId(), updateMember.getContactNumber());

        Assertions.assertThat(passwordEncoder.matches(updateMember.getPassword(), afterEntity.getPassword() )).isTrue();}

}