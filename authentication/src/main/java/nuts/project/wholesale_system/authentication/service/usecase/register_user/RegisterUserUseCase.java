package nuts.project.wholesale_system.authentication.service.usecase.register_user;

import nuts.project.wholesale_system.authentication.service.dto.UserInformation;

public interface RegisterUserUseCase {

    UserInformation execute(String userName, String email, String password, String firstName, String lastName);

}
