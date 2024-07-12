package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation;


import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CorporationExceptionHandler {

    @ExceptionHandler(CorporationUseCaseException.class)
    public ResponseEntity<String> corporationUseCaseExceptionHandle(CorporationUseCaseException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
