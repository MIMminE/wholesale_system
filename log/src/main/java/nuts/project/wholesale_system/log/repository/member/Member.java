package nuts.project.wholesale_system.log.repository.member;

import jakarta.persistence.*;
import nuts.project.wholesale_system.log.LogType;

@Entity
public class Member {

    @Id
    private String logId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LogType logType;

    @Column(nullable = false)
    private String log;
}
