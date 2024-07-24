package nuts.project.wholesale_system.member.aop;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.domain.port.log.LogServicePort;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.UUID;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private final LogServicePort logService;

    @Before("execution(* nuts.project.wholesale_system.member.adapter.inbound.controller.*.*Controller.*(..))")
    public void beforeAdvice(JoinPoint point) {
        String requestId = UUID.randomUUID().toString();
        RequestContextHolder.currentRequestAttributes().setAttribute("requestId", requestId, RequestAttributes.SCOPE_REQUEST);

        Map<String, Object> logMessage = Map.of("requestId", requestId, "logType", "info", "log", point.getArgs()[0].toString());
        logService.publishing(logMessage);
    }

    @AfterReturning(value = "execution(* nuts.project.wholesale_system.member.adapter.inbound.controller.*.*Controller.*(..))", returning = "response")
    public void afterAdvice(Object response) {
        String requestId = RequestContextHolder.currentRequestAttributes().getAttribute("requestId", RequestAttributes.SCOPE_REQUEST).toString();

        Map<String, Object> logMessage = Map.of("requestId", requestId, "logType", "info", "log", response.toString());
        logService.publishing(logMessage);
    }

    @AfterReturning(value = "execution(* nuts.project.wholesale_system.member.adapter.inbound.controller.*.*ExceptionHandler.*(..))", returning = "response")
    public void exceptionHandlerAdvice(Object response) {
        String requestId = RequestContextHolder.currentRequestAttributes().getAttribute("requestId", RequestAttributes.SCOPE_REQUEST).toString();

        Map<String, Object> logMessage = Map.of("requestId", requestId, "logType", "error", "log", response.toString());
        logService.publishing(logMessage);
    }
}
