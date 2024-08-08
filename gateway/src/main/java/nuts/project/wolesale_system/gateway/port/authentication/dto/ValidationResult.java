package nuts.project.wolesale_system.gateway.port.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationResult {
    private String roleName;
    private String userId;
    private boolean result;
}
