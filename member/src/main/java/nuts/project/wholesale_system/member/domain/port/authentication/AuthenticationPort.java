package nuts.project.wholesale_system.member.domain.port.authentication;

import java.util.Map;

public interface AuthenticationPort {
    RegisterUserResult registerUser(Map<String, Object> userInfo);
}
