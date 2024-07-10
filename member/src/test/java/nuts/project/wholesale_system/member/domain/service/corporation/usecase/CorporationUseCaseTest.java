package nuts.project.wholesale_system.member.domain.service.corporation.usecase;

import nuts.project.wholesale_system.member.SpringTestSupport;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.CreateCorporationRequest;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLIntegrityConstraintViolationException;

public class CorporationUseCaseTest extends SpringTestSupport {

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
        createCorporationUseCase.execute(corporationName, representative, contactNumber, businessNumber, grade);

        // then
        Assertions.assertThatThrownBy(() -> corporationRepository.flush()).isInstanceOf(DataIntegrityViolationException.class);
    }


    @DisplayName("deleteCorporationUseCase 동작 성공 테스트")
    @Test
    void deleteCorporationUseCase() {
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
                .extracting("corporationId", "corporationName", "representative", "contactNumber", "businessNumber","grade")
                .contains(corporationId, corporationName, representative, contactNumber, businessNumber, grade);
    }
}
