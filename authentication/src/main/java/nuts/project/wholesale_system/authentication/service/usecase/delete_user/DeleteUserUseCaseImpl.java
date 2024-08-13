package nuts.project.wholesale_system.authentication.service.usecase.delete_user;

import nuts.project.wholesale_system.authentication.config.AuthServerProperties;
import nuts.project.wholesale_system.authentication.exception.AuthenticationException;
import nuts.project.wholesale_system.authentication.service.cache.UserAuthenticationTableCache;
import nuts.project.wholesale_system.authentication.service.usecase.AuthenticationServerSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final RestTemplate restTemplate;
    private final AuthServerProperties authServerConfig;
    private final AuthenticationServerSupport authServerSupport;

    public DeleteUserUseCaseImpl(RestTemplate restTemplate, AuthServerProperties authServerConfig) {
        this.restTemplate = restTemplate;
        this.authServerConfig = authServerConfig;
        this.authServerSupport = new AuthenticationServerSupport(authServerConfig, restTemplate);
    }


    @Override
    public boolean execute(String username) {
        String userId = UserAuthenticationTableCache.userTable.get(username);
        if (userId == null) {
            throw new AuthenticationException(AuthenticationException.AuthenticationExceptionType.USER_NOT_FOUND);
        }

        HttpHeaders adminTokenRequestHeader = authServerSupport.getAdminTokenRequestHeader();
        HttpEntity<String> request = new HttpEntity<>(adminTokenRequestHeader);

        String requestUrl = String.format("%s/admin/realms/%s/users/%s", authServerConfig.getUrl(), authServerConfig.getRealms(), userId);

        restTemplate.exchange(requestUrl, HttpMethod.DELETE, request, String.class);

        return true;
    }
}
