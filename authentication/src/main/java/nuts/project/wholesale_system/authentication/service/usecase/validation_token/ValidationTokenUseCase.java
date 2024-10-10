package nuts.project.wholesale_system.authentication.service.usecase.validation_token;

import nuts.project.wholesale_system.authentication.service.dto.ValidationResponse;

public interface ValidationTokenUseCase {
    ValidationResponse execute(String token);
}
