package nuts.project.wholesale_system.authentication.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import nuts.project.wholesale_system.authentication.service.dto.UserInformation;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class UserTableResponse {

    int count;
    List<UserInformation> userInformationList;

}
