package nuts.project.wholesale_system.member.domain.model;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {
    private String name;
    private String id;
    private String password;

    @Pattern(regexp = "010-\\d{4}-\\d{4}")
    private String contactNumber;

    @Setter
    private String corporationId;
}
