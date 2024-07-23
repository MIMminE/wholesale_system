package nuts.project.wholesale_system.log.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLogRepository extends JpaRepository<MemberLog, String> {
    MemberLog findByLog(String log);
}
