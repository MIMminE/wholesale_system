package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation;


import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class CorporationExceptionHandler {

    @ExceptionHandler(CorporationUseCaseException.class)
    public Map<String, Object> corporationUseCaseExceptionHandle(CorporationUseCaseException e) {
        return Map.of("message", e.getMessage());
    }
}
