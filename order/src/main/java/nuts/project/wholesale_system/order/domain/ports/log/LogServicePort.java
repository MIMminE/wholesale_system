package nuts.project.wholesale_system.order.domain.ports.log;

import java.util.Map;

public interface LogServicePort {
    void publishing(Map<String, Object> message);
}