package nuts.project.wholesale_system.authentication.service;


import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.authentication.controller.response.TokenResponse;
import nuts.project.wholesale_system.authentication.controller.response.UserInformation;
import nuts.project.wholesale_system.authentication.service.usecase.delete_user.DeleteUserUseCase;
import nuts.project.wholesale_system.authentication.service.usecase.get_user.GetUserUseCase;
import nuts.project.wholesale_system.authentication.service.usecase.get_user_table.GetUserTableUseCase;
import nuts.project.wholesale_system.authentication.service.usecase.register_user.RegisterUserUseCase;
import nuts.project.wholesale_system.authentication.service.usecase.request_token.RequestTokenUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {

    private final RequestTokenUseCase requestTokenUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserTableUseCase getUserTableUseCase;
    private final GetUserUseCase getUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public TokenResponse requestToken(String userName, String password) {

        return requestTokenUseCase.execute(userName, password);
    }

    public List<UserInformation> getUserTable() {
        return getUserTableUseCase.execute();
    }

    public UserInformation getUser(String username) {
        return getUserUseCase.execute(username);
    }


    public UserInformation registerUser(String username, String email, String password) {
        return registerUserUseCase.execute(username, email, password);
    }

    public boolean deleteUser(String username) {
        return deleteUserUseCase.execute(username);
    }


//    public Object getJwkSet() {
//        return restTemplate.getForObject(authServerUrl + "/set", Map.class);
//    }

}
