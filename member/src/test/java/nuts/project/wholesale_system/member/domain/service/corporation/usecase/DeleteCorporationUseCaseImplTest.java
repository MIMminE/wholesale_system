package nuts.project.wholesale_system.member.domain.service.corporation.usecase;

import nuts.project.wholesale_system.member.support.CorporationUseCaseTestSupport;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.DeleteCorporationRequest;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException.CorporationExceptionCase.*;


class DeleteCorporationUseCaseImplTest extends CorporationUseCaseTestSupport {

    @DisplayName("전달 받은 기관 ID 에 해당하는 기관 정보를 반환한다.")
    @Test
    void successDeleteCorporationUseCase() {
        // given
        CorporationEntity corporationEntity = fixtureManager.getOrderObject(CorporationEntity.class);
        CorporationEntity resultEntity = corporationRepository.save(corporationEntity);

        String corporationId = resultEntity.getCorporationId();
        String corporationName = corporationEntity.getCorporationName();
        String representative = corporationEntity.getRepresentative();
        String contactNumber = corporationEntity.getContactNumber();
        String businessNumber = corporationEntity.getBusinessNumber();
        Grade grade = corporationEntity.getGrade();
        // when
        Corporation corporation = deleteCorporationUseCase.execute(corporationId);

        // then
        Assertions.assertThat(corporation)
                .extracting("corporationId", "corporationName", "representative", "contactNumber", "businessNumber", "grade")
                .contains(corporationId, corporationName, representative, contactNumber, businessNumber, grade);
    }

    @DisplayName("전달 받은 기관 ID 에 대한 데이터가 없을 경우 예외를 던진다.")
    @Test
    void exceptionDeleteCorporationUseCase() {
        // given
        DeleteCorporationRequest deleteCorporationRequest = fixtureManager.getOrderObject(DeleteCorporationRequest.class);
        String corporationId = deleteCorporationRequest.getCorporationId();

        // when // then
        Assertions.assertThatThrownBy(() -> deleteCorporationUseCase.execute(corporationId))
                .hasMessage(DELETE_NO_SUCH_ELEMENT.getMessage())
                .isInstanceOf(CorporationUseCaseException.class);
    }
}