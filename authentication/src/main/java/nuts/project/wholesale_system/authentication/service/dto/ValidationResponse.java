package nuts.project.wholesale_system.authentication.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidationResponse {

    private boolean result;
    private List<String> roleList;
}
