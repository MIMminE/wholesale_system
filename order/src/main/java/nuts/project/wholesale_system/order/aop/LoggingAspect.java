package nuts.project.wholesale_system.order.aop;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.ports.log.LogPublisherPort;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private final LogPublisherPort logPublisher;

    @Before("execution(* nuts.project.wholesale_system.order.service.*.*(..))")
    public void beforeAdvice(JoinPoint point) {

        Object[] args = point.getArgs();
        logPublisher.publishing(Arrays.toString(args));

    }

    @AfterReturning(value = "execution(* nuts.project.wholesale_system.order.service.*.*(..))", returning = "response")
    public void afterAdvice(Object response) {
        logPublisher.publishing(response.toString());
    }
}
