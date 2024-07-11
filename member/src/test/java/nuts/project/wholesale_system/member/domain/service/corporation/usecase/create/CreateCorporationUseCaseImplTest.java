package nuts.project.wholesale_system.member.domain.service.corporation.usecase.create;

import nuts.project.wholesale_system.member.support.SpringTestSupport;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.CreateCorporationRequest;
import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException.CorporationExceptionCase.CREATE_REDUNDANCY_BUSINESS_NUMBER;

class CreateCorporationUseCaseImplTest extends SpringTestSupport {

    @DisplayName("createCorporationUseCase 동작 성공 테스트")
    @Test
    void SuccessCreateCorporationUseCase() {
        // given
        CreateCorporationRequest createCorporationRequest = getOrderedObject(CreateCorporationRequest.class).get(0);
        String corporationName = createCorporationRequest.getCorporationName();
        String representative = createCorporationRequest.getRepresentative();
        String contactNumber = createCorporationRequest.getContactNumber();
        String businessNumber = createCorporationRequest.getBusinessNumber();
        Grade grade = Grade.valueOf(createCorporationRequest.getGrade());

        // when
        Corporation result = createCorporationUseCase.execute(corporationName, representative, contactNumber, businessNumber, grade);

        // then
        Assertions.assertThat(result.getCorporationId()).isNotNull();

        Assertions.assertThat(result)
                .extracting("corporationName", "representative", "contactNumber", "businessNumber", "grade")
                .contains(corporationName, representative, contactNumber, businessNumber, grade);

    }

    @DisplayName("createCorporationUseCase 예외 발생 테스트 (동일 기관 번호 입력시)")
    @Test
    void ExceptionCreateCorporationUseCase() {
        // given
        CreateCorporationRequest createCorporationRequest = getOrderedObject(CreateCorporationRequest.class).get(0);
        String corporationName = createCorporationRequest.getCorporationName();
        String representative = createCorporationRequest.getRepresentative();
        String contactNumber = createCorporationRequest.getContactNumber();
        String businessNumber = createCorporationRequest.getBusinessNumber();
        Grade grade = Grade.valueOf(createCorporationRequest.getGrade());

        // when
        Corporation result = createCorporationUseCase.execute(corporationName, representative, contactNumber, businessNumber, grade);

        // then
        Assertions.assertThatThrownBy(() -> createCorporationUseCase.execute(corporationName, representative, contactNumber, businessNumber, grade))
                .isInstanceOf(CorporationUseCaseException.class)
                .hasMessage(CREATE_REDUNDANCY_BUSINESS_NUMBER.getMessage());
    }

}