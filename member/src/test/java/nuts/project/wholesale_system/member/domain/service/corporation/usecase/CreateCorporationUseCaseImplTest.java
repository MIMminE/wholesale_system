package nuts.project.wholesale_system.member.domain.service.corporation.usecase;

import nuts.project.wholesale_system.member.support.CorporationUseCaseTestSupport;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.CreateCorporationRequest;
import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException.CorporationExceptionCase.CREATE_REDUNDANCY_BUSINESS_NUMBER;

class CreateCorporationUseCaseImplTest extends CorporationUseCaseTestSupport {

    @DisplayName("요청 기관 정보를 기반으로 Corporation Repository에 데이터 저장에 성공한다.")
    @Test
    void successCreateCorporationUseCase() {
        // given
        CreateCorporationRequest createCorporationRequest = fixtureManager.getOrderObject(CreateCorporationRequest.class);
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

    @DisplayName("요청 기관 정보 중 [ businessNumber ] 가 일치한 기관이 이미 등록되어 있을 경우 예외를 던진다.")
    @Test
    void exceptionCreateCorporationUseCase() {
        // given
        CreateCorporationRequest createCorporationRequest = fixtureManager.getOrderObject((CreateCorporationRequest.class));
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