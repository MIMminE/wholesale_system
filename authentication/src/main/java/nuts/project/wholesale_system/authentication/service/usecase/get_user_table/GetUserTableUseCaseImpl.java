package nuts.project.wholesale_system.authentication.service.usecase.get_user_table;

import nuts.project.wholesale_system.authentication.config.AuthServerProperties;
import nuts.project.wholesale_system.authentication.service.dto.UserInformation;
import nuts.project.wholesale_system.authentication.service.usecase.AuthenticationServerSupport;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class GetUserTableUseCaseImpl implements GetUserTableUseCase {

    private final RestTemplate restTemplate;
    private final AuthServerProperties authServerConfig;
    private final AuthenticationServerSupport authServerSupport;

    public GetUserTableUseCaseImpl(RestTemplate restTemplate, AuthServerProperties authServerConfig) {
        this.restTemplate = restTemplate;
        this.authServerConfig = authServerConfig;
        this.authServerSupport = new AuthenticationServerSupport(authServerConfig, restTemplate);
    }

    @Override
    public List<UserInformation> execute() {

        HttpHeaders requestUserInfoHeader = authServerSupport.getAdminTokenRequestHeader();
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestUserInfoHeader);

        String requestUrl = String.format("%s/admin/realms/%s/users", authServerConfig.getUrl(), authServerConfig.getRealms());

        return restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<UserInformation>>() {
        }).getBody();
    }
}
