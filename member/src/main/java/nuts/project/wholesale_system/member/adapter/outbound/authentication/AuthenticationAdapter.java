package nuts.project.wholesale_system.member.adapter.outbound.authentication;

import nuts.project.wholesale_system.member.domain.port.authentication.AuthenticationPort;
import nuts.project.wholesale_system.member.domain.port.authentication.AuthenticationPortResult;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthenticationAdapter implements AuthenticationPort {

    @Override
    public AuthenticationPortResult registerUser(Map<String, Object> userInfo) {
        return null;
    }

    @Override
    public AuthenticationPortResult updateUser() {
        return null;
    }

    @Override
    public AuthenticationPortResult deleteUser() {
        return null;
    }

    @Override
    public AuthenticationPortResult getUserTable() {
        return null;
    }
}
