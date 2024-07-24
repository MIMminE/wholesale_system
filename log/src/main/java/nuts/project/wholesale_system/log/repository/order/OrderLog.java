package nuts.project.wholesale_system.log.repository.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.project.wholesale_system.log.LogType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderLog {

    @Id
    String id;

    String requestId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    LogType logType;

    @Column(nullable = false)
    String log;
}
