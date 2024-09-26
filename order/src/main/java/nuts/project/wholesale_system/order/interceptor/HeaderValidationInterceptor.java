package nuts.project.wholesale_system.order.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;
import java.util.Iterator;

public class  HeaderValidationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String role = request.getHeader("User-Role");
        String sign = request.getHeader("Gateway-Sign");

        System.out.println(role);
        System.out.println(sign);
        System.out.println("interceptor !!");


        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
