package nuts.project.wholesale_system.authentication.service.cache;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.authentication.controller.response.UserInformation;
import nuts.project.wholesale_system.authentication.service.usecase.get_user_table.GetUserTableUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserAuthenticationTableCache {

    public static ConcurrentMap<String, String> userTable = new ConcurrentHashMap<>();
    private final GetUserTableUseCase getUserTableUseCase;

    @PostConstruct
    public void init() {
        List<UserInformation> userInfoList = getUserTableUseCase.execute();

        userTable = userInfoList.stream()
                .collect(Collectors.toConcurrentMap(UserInformation::getUserName, UserInformation::getUserId));
    }
}
