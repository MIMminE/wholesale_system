package nuts.project.wholesale_system.member.domain.service.corporation.usecase.get;

import nuts.project.wholesale_system.member.support.CorporationUseCaseTestSupport;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import nuts.project.wholesale_system.member.domain.model.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException.CorporationExceptionCase.GET_NO_SUCH_ELEMENT;

class GetCorporationUseCaseImplTest extends CorporationUseCaseTestSupport {

    @DisplayName("getCorporationUseCase 동작 성공 테스트")
    @Test
    void successGetCorporationUseCase() {
        // given
        CorporationEntity corporationEntity = getOrderedObject(CorporationEntity.class).get(0);
        CorporationEntity resultEntity = corporationRepository.save(corporationEntity);
        Corporation resultEntityCorporation = resultEntity.toCorporation();

        String corporationId = resultEntityCorporation.getCorporationId();
        String corporationName = resultEntityCorporation.getCorporationName();
        String representative = resultEntityCorporation.getRepresentative();
        String contactNumber = resultEntityCorporation.getContactNumber();
        String businessNumber = resultEntityCorporation.getBusinessNumber();
        Grade grade = resultEntityCorporation.getGrade();
        List<Member> registeredMembers = resultEntityCorporation.getRegisteredMembers();

        // when
        Corporation corporation = getCorporationUseCase.execute(corporationId);

        // then
        Assertions.assertThat(corporation)
                .extracting("corporationId", "corporationName", "representative", "contactNumber", "businessNumber", "grade", "registeredMembers")
                .contains(corporationId, corporationName, representative, contactNumber, businessNumber, grade, registeredMembers);
    }

    @DisplayName("getCorporationUseCase 동작 예외 테스트(조회할 대상 없음)")
    @Test
    void exceptionGetCorporationUseCase() {
        // given
        String corporationId = UUID.randomUUID().toString();

        // when // then
        Assertions.assertThatThrownBy(() -> getCorporationUseCase.execute(corporationId))
                .hasMessage(GET_NO_SUCH_ELEMENT.getMessage())
                .isInstanceOf(CorporationUseCaseException.class);
    }

}