package nuts.project.wholesale_system.authentication.service.usecase.get_user;

import nuts.project.wholesale_system.authentication.controller.response.UserInformation;
import nuts.project.wholesale_system.authentication.exception.AuthenticationException;

public interface GetUserUseCase {
    UserInformation execute(String username) throws AuthenticationException;
}
