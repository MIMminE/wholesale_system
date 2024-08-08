package nuts.project.wolesale_system.gateway.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class GatewayException extends RuntimeException {
    public GatewayException(GatewayExceptionCode gatewayExceptionCode) {
        super(gatewayExceptionCode.message);
    }

    public GatewayException(GatewayExceptionCode gatewayExceptionCode, Throwable cause) {
        super(gatewayExceptionCode.message, cause);
    }

    @RequiredArgsConstructor
    public enum GatewayExceptionCode {
        AuthenticationFail("Authentication Failed");

        private final String message;
    }
}
