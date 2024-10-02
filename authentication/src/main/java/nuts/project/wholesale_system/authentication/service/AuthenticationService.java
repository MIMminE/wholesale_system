package nuts.project.wholesale_system.authentication.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.project.wholesale_system.authentication.service.dto.TokenResponse;
import nuts.project.wholesale_system.authentication.service.dto.UserInformation;
import nuts.project.wholesale_system.authentication.service.usecase.delete_user.DeleteUserUseCase;
import nuts.project.wholesale_system.authentication.service.usecase.get_user.GetUserUseCase;
import nuts.project.wholesale_system.authentication.service.usecase.get_user_table.GetUserTableUseCase;
import nuts.project.wholesale_system.authentication.service.usecase.register_user.RegisterUserUseCase;
import nuts.project.wholesale_system.authentication.service.usecase.request_token.RequestTokenUseCase;
import nuts.project.wholesale_system.authentication.service.usecase.validation_token.ValidationTokenUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final RequestTokenUseCase requestTokenUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserTableUseCase getUserTableUseCase;
    private final GetUserUseCase getUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ValidationTokenUseCase validationTokenUseCase;


    public TokenResponse createToken(String userName, String password) {

        return requestTokenUseCase.execute(userName, password);
    }

    public List<UserInformation> getUserTable() {
        return getUserTableUseCase.execute();
    }

    public UserInformation getUser(String username) {
        return getUserUseCase.execute(username);
    }


    public UserInformation registerUser(String username, String firstName, String lastName, String email, String password) {
        return registerUserUseCase.execute(username, email, password, firstName, lastName);
    }

    public boolean deleteUser(String username) {
        return deleteUserUseCase.execute(username);
    }

    public boolean validationToken(String token) {
        return validationTokenUseCase.execute(token);
    }

}
