package nuts.project.wholesale_system.member.domain.model;

import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Corporation {
    private String corporationId;
    private String corporationName;
    private String representative;
    private String contactNumber;
    private String businessNumber;
    private Grade grade;
    private List<Member> registeredMembers;
}
