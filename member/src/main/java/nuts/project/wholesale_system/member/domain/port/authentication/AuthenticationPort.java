package nuts.project.wholesale_system.member.domain.port.authentication;

import java.util.Map;

public interface AuthenticationPort {
    AuthenticationPortResult registerUser(Map<String, Object> userInfo);

    AuthenticationPortResult updateUser();

    AuthenticationPortResult deleteUser();

    AuthenticationPortResult getUserTable();
}
