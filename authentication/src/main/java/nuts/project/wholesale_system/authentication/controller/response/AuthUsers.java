package nuts.project.wholesale_system.authentication.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AuthUsers {
    List<Object> users;
}
