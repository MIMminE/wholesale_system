package nuts.project.wholesale_system.member.domain.service.corporation.usecase.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nuts.project.wholesale_system.member.domain.model.Corporation;

@Getter
@AllArgsConstructor
public class UpdateCorporationResultSet {

    private Corporation beforeCorporation;
    private Corporation afterCorporation;
}
