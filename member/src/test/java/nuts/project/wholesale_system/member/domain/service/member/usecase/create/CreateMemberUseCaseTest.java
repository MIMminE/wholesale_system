package nuts.project.wholesale_system.member.domain.service.member.usecase.create;

import nuts.project.wholesale_system.member.SpringTestSupport;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.domain.model.Member;
import nuts.project.wholesale_system.member.domain.service.member.dto.request.CreateMemberRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//@Commit
class CreateMemberUseCaseTest extends SpringTestSupport {

    @DisplayName("createMemberUseCase 동작 성공 테스트")
    @Test
    void successCreateMemberUseCase() {

        // given
        CorporationEntity corporationEntity = getOrderedObject(CorporationEntity.class).get(0);

        corporationRepository.save(corporationEntity);
        CreateMemberRequest createMemberRequest = getOrderedObject(CreateMemberRequest.class).get(0);
        String corporationId = corporationEntity.getCorporationId();

        // when
        Member member = createMemberUseCase.execute(createMemberRequest.getName(), createMemberRequest.getId(), createMemberRequest.getPassword(),
                createMemberRequest.getContactNumber(), corporationId);

        // then
        Assertions.assertThat(member).extracting("name", "id", "contactNumber", "corporationId")
                .contains(createMemberRequest.getName(), createMemberRequest.getId(),
                        createMemberRequest.getContactNumber(), corporationId);

        Assertions.assertThat(passwordEncoder.matches(createMemberRequest.getPassword(), member.getPassword())).isTrue();
    }
}