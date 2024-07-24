
package nuts.project.wholesale_system.member.domain.port.log;

import java.util.Map;

public interface LogServicePort {
    void publishing(Map<String, Object> message);
}
