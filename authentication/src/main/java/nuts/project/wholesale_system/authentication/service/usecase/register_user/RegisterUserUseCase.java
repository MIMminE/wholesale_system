package nuts.project.wholesale_system.authentication.service.usecase.register_user;

import nuts.project.wholesale_system.authentication.controller.response.UserInformation;

public interface RegisterUserUseCase {

    UserInformation execute(String userName, String email, String password);

}
