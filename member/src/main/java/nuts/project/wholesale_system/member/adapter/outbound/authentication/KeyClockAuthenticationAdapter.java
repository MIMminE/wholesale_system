package nuts.project.wholesale_system.member.adapter.outbound.authentication;

import nuts.project.wholesale_system.member.domain.port.authentication.AuthenticationPort;
import nuts.project.wholesale_system.member.domain.port.authentication.RegisterUserResult;

import java.util.Map;

public class KeyClockAuthenticationAdapter implements AuthenticationPort {

    @Override
    public RegisterUserResult registerUser(Map<String, Object> userInfo) {
        return null;
    }

}
