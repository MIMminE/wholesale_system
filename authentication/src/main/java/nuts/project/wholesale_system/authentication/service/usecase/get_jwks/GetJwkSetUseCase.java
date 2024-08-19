package nuts.project.wholesale_system.authentication.service.usecase.get_jwks;

import nuts.project.wholesale_system.authentication.controller.response.JwkSet;

public interface GetJwkSetUseCase {

    JwkSet execute();
}
