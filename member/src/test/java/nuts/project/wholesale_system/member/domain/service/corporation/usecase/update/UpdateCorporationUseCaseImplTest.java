package nuts.project.wholesale_system.member.domain.service.corporation.usecase.update;

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

    @DisplayName("updateCorporationUseCase 동작 성공 테스트")
    @Test
    void successUpdateCorporationUseCase() {
        // given
        CorporationEntity corporationEntity = getOrderedObject(CorporationEntity.class).get(1);
        CorporationEntity beforeEntity = corporationRepository.save(corporationEntity);
        String corporationId = beforeEntity.getCorporationId();
        Corporation beforeCorporation = beforeEntity.toCorporation();
        Corporation updatedCorporation = getOrderedObject(Corporation.class).get(0);

        // when
        UpdateCorporationResultSet resultSet = updateCorporationUseCase.execute(corporationId, updatedCorporation);
        CorporationEntity verifyEntity = corporationRepository.findById(corporationId).orElseThrow();
        Corporation verifyCorporation = verifyEntity.toCorporation();

        // then
        Assertions.assertThat(resultSet)
                .extracting("beforeCorporation", "afterCorporation")
                .contains(beforeCorporation, verifyCorporation);
    }

    @DisplayName("updateCorporationUseCase 동작 예외 테스트")
    @Test
    void exceptionUpdateCorporationUseCase() {
        // given
        String corporationId = UUID.randomUUID().toString();
        Corporation updatedCorporation = getOrderedObject(Corporation.class).get(0);

        // when // then
        Assertions.assertThatThrownBy(() -> updateCorporationUseCase.execute(corporationId, updatedCorporation))
                .hasMessage(UPDATE_NO_SUCH_ELEMENT.getMessage())
                .isInstanceOf(CorporationUseCaseException.class);
    }

}