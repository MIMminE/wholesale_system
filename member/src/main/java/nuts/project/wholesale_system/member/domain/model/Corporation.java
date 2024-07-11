package nuts.project.wholesale_system.member.domain.model;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Corporation {
    private String corporationId;
    private String corporationName;
    private String representative;

    @Pattern(regexp = "010-\\d{4}-\\d{4}")
    private String contactNumber;
    private String businessNumber;
    private Grade grade;
    private List<Member> registeredMembers;
}
