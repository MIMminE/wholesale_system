package nuts.project.wholesale_system.authentication.service.usecase.get_user_table;

import nuts.project.wholesale_system.authentication.controller.response.UserInformation;

import java.util.List;

public interface GetUserTableUseCase {
    List<UserInformation> execute();
}
