package nuts.project.wolesale_system.stock.adapter.inbound.controller;

import nuts.project.wolesale_system.stock.domain.exception.StockException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StockExceptionHandler {

    @ExceptionHandler(StockException.class)
    public ResponseEntity<String> exceptionHandle(StockException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
