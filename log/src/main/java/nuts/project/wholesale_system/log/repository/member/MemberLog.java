package nuts.project.wholesale_system.log.repository.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nuts.project.wholesale_system.log.LogType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MemberLog {

    @Id
    private String id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LogType logType;

    @Column(nullable = false)
    private String log;
}
