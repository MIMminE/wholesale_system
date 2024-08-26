
package nuts.project.wholesale_system.authentication.aop.log;

import java.util.Map;

public interface LogServicePort {
    void publishing(Map<String, Object> message);
}
