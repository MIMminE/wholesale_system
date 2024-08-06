package nuts.project.wholesale_system.order.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class HeaderValidationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String gatePass = request.getHeader("Gateway-Pass");
        if (gatePass == null) {
           response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
