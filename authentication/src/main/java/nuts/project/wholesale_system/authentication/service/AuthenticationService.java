package nuts.project.wholesale_system.authentication.service;

import nuts.project.wholesale_system.authentication.controller.response.TokenResponse;
import nuts.project.wholesale_system.authentication.controller.response.UserInformation;
import nuts.project.wholesale_system.authentication.exception.AuthenticationException;

import java.util.List;

public interface AuthenticationService {

    TokenResponse requestToken(String username, String password);

    List<UserInformation> getUserTable();

    UserInformation getUser(String username) throws AuthenticationException;

    UserInformation registerUser(String username, String email, String password);

    Object getJwkSet();
}
