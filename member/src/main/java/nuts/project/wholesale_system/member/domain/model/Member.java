package nuts.project.wholesale_system.member.domain.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class Member {
    private String name;
    private String id;
    private String password;
    private String contactNumber;

    @Setter
    private String corporationId;
}
