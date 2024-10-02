package nuts.project.wholesale_system.authentication.service.usecase.validation_token;

import com.nimbusds.jose.JOSEException;

public interface ValidationTokenUseCase {
    boolean execute(String token);
}
