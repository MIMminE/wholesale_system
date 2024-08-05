package nuts.project.wholesale_system.order.domain.ports.authentication;

import java.util.Map;

public interface AuthenticationPort {

    Map<String, Object> getJWTSet();
}
