package nuts.project.wolesale_system.stock.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
//    private final LogPublisherPort logPublisher;
//
//    @Before("execution(* nuts.project.wholesale_system.order.domain.service.*.*(..))")
//    public void beforeAdvice(JoinPoint point) {
//
//        Object[] args = point.getArgs();
//        logPublisher.publishing(Arrays.toString(args));
//
//    }
//
//    @AfterReturning(value = "execution(* nuts.project.wholesale_system.order.service.*.*(..))", returning = "response")
//    public void afterAdvice(Object response) {
//        logPublisher.publishing(response.toString());
//    }
}
