package nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMemberRequest {

    private String name;

    private String id;

    private String password;

    private String contactNumber;

    private String corporationId;
}
