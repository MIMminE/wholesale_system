package nuts.project.wholesale_system.authentication.service.usecase.request_token;

import nuts.project.wholesale_system.authentication.service.dto.TokenResponse;

public interface RequestTokenUseCase {

    TokenResponse execute(String username, String password);
}
