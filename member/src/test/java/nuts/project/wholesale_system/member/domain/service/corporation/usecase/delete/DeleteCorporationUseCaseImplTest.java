package nuts.project.wholesale_system.member.domain.service.corporation.usecase.delete;

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

    @DisplayName("deleteCorporationUseCase 동작 성공 테스트")
    @Test
    void SuccessDeleteCorporationUseCase() {
        // given
        CorporationEntity corporationEntity = getOrderedObject(CorporationEntity.class).get(0);
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

    @DisplayName("deleteCorporationUseCase 동작 예외 테스트 (삭제 대상 없음)")
    @Test
    void ExceptionDeleteCorporationUseCase() {
        // given
        DeleteCorporationRequest deleteCorporationRequest = getOrderedObject(DeleteCorporationRequest.class).get(0);
        String corporationId = deleteCorporationRequest.getCorporationId();

        // when // then
        Assertions.assertThatThrownBy(() -> deleteCorporationUseCase.execute(corporationId))
                .hasMessage(DELETE_NO_SUCH_ELEMENT.getMessage())
                .isInstanceOf(CorporationUseCaseException.class);
    }
}