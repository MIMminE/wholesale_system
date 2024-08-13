package nuts.project.wholesale_system.authentication.service.usecase.request_token;

import nuts.project.wholesale_system.authentication.controller.response.TokenResponse;

public interface RequestTokenUseCase {

    TokenResponse execute(String username, String password);
}
