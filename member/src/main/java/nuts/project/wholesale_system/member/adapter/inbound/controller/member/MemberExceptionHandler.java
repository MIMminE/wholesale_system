package nuts.project.wholesale_system.member.adapter.inbound.controller.member;

import nuts.project.wholesale_system.member.domain.exception.MemberUseCaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(MemberUseCaseException.class)
    public ResponseEntity<String> corporationUseCaseExceptionHandle(MemberUseCaseException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
