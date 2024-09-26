package nuts.project.wolesale_system.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GatewayController {

    @GetMapping("/token-error")
    public String tokenError() {
        return "token-error";
    }
}
