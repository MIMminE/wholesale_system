package nuts.project.wholesale_system.order.adapter.inbound.controller;


import nuts.project.wholesale_system.order.domain.exception.OrderException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<Map<String, Object>> orderUseCaseExceptionHandle(OrderException e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
}
