package nuts.project.wholesale_system.member.domain.service.corporation.usecase;

import nuts.project.wholesale_system.member.domain.service.corporation.usecase.update.UpdateCorporationResultSet;
import nuts.project.wholesale_system.member.support.CorporationUseCaseTestSupport;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException.CorporationExceptionCase.UPDATE_NO_SUCH_ELEMENT;


class UpdateCorporationUseCaseImplTest extends CorporationUseCaseTestSupport {

    @DisplayName("요청 기관 ID 정보를 전달받은 요청 정보로 변경, 반영에 성공한다.")
    @Test
    void successUpdateCorporationUseCase() {
        // given
        CorporationEntity corporationEntity = fixtureManager.getOrderObjects((CorporationEntity.class)).get(1);
        CorporationEntity beforeEntity = corporationRepository.save(corporationEntity);
        String corporationId = beforeEntity.getCorporationId();
        Corporation beforeCorporation = beforeEntity.toCorporation();
        Corporation updatedCorporation = fixtureManager.getOrderObject(Corporation.class);

        // when
        UpdateCorporationResultSet resultSet = updateCorporationUseCase.execute(corporationId, updatedCorporation);
        CorporationEntity verifyEntity = corporationRepository.findById(corporationId).orElseThrow();
        Corporation verifyCorporation = verifyEntity.toCorporation();

        // then
        Assertions.assertThat(resultSet)
                .extracting("beforeCorporation", "afterCorporation")
                .contains(beforeCorporation, verifyCorporation);
    }

    @DisplayName("요청 기관 ID 가 조회되지 않을 경우 예외를 던진다.")
    @Test
    void exceptionUpdateCorporationUseCase() {
        // given
        String corporationId = UUID.randomUUID().toString();
        Corporation updatedCorporation = fixtureManager.getOrderObject(Corporation.class);

        // when // then
        Assertions.assertThatThrownBy(() -> updateCorporationUseCase.execute(corporationId, updatedCorporation))
                .hasMessage(UPDATE_NO_SUCH_ELEMENT.getMessage())
                .isInstanceOf(CorporationUseCaseException.class);
    }

}